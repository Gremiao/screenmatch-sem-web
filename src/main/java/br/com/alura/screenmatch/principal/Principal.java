package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        System.out.println("Digite o nome da série para ");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO+ nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i<=dadosSerie.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO+ nomeSerie.replace(" ", "+")+"&season="+ i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

//        for(int i = 0; i< dadosSerie.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for(int j=0; j<episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        System.out.println("Top 10 episodios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro "+e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao)
                .reversed())
//                .peek(e -> System.out.println("Ordenação "+e))
                .limit(10)
//                .peek(e -> System.out.println("Limite "+e))
                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento "+e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                                             .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(),d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite um trecho do titulo do episódio: ");
        var trechoTitulo = leitura.nextLine();


        Optional<Episodio> episodioBuscado = episodios.stream()
                                                      .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                                                      .findFirst();

        if(episodioBuscado.isPresent()){
            System.out.println("Episódio localizado com sucesso!");
            System.out.println("Temporada: "+episodioBuscado.get().getTemporada());
        }else{
            System.out.println("Episódio não localizado...");
        }

        System.out.println("A partir de qual ano você deseja ver os episodios: ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                 .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                 .forEach(d -> System.out.println(
                         "Temporada: "+d.getTemporada() +
                                 " | Episódio: "+d.getTitulo()+
                                 " | Data lançamento: "+d.getDataLancamento().format(formatter)
                 ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                                                               .filter(e -> e.getAvaliacao() > 0.0)
                                                               .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println("Avaliacoes por temporada: "+avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                                               .filter(e -> e.getAvaliacao() > 0.0)
                                               .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média : "+est.getAverage());
        System.out.println("Melhor : "+est.getMax());
        System.out.println("Pior : "+est.getMin());
        System.out.println("Quantidade : "+est.getCount());
    }
}
