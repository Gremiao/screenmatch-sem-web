package br.com.alura.screenmatch.desafio;

import java.util.*;
import java.util.stream.Collectors;

public class Desafio3 {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);
        IntSummaryStatistics estListaNumeros = numeros.stream().mapToInt(num -> num).summaryStatistics();
        System.out.println(estListaNumeros.getMax());

        //////////////////////////////////

        List<String> palavras = Arrays.asList("java", "stream", "lambda", "code");
        Map<Integer, List<String>> agrupamento = palavras.stream().collect(Collectors.groupingBy(String::length));
        System.out.println(agrupamento);

        /////////////////////////////////

        List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");
        System.out.println(String.join(", ", nomes));

        ////////////////////////////////

        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(numeros2.stream().filter(n -> n % 2 == 0).map(n -> n * n).reduce(0, Integer::sum));

        ////////////////////////////////

        List<Integer> numeros3 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> particionado = numeros.stream()
                                                          .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Pares: " + particionado.get(true));  // Esperado: [2, 4, 6]
        System.out.println("Ímpares: " + particionado.get(false)); // Esperado: [1, 3, 5]

        ////////////////////////////////

        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Teclado", 200.0, "Eletrônicos"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Monitor", 900.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis")
        );

        produtos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase("Eletrônicos"))
                .filter(v -> v.getPreco() <= 1000.0)
                .sorted(Comparator.comparing(Produto::getPreco))
                .forEach(i -> System.out.println("Produto :"+i.getNome() + " | Preço :"+i.getPreco() + " | Categoria :" + i.getCategoria()));;

        Map<String, List<Produto>> listaPorCategoria = produtos.stream().collect(Collectors.groupingBy(Produto::getCategoria));
        Map<String, Long> contagemProdutosPorCategoria = produtos.stream().collect(Collectors.groupingBy(Produto::getCategoria, Collectors.counting()));
        Map<String, Optional<Produto>> maisCaroPorCategoria = produtos.stream().collect(Collectors.groupingBy(Produto::getCategoria, Collectors.maxBy(Comparator.comparing(Produto::getPreco))));
        System.out.println(listaPorCategoria);
        System.out.println(contagemProdutosPorCategoria);
        System.out.println(maisCaroPorCategoria);

        ////////////////////////////////

        Map<String, Double> valorTotalPrecosPorCategoria = produtos.stream().collect(Collectors.groupingBy(Produto::getCategoria, Collectors.summingDouble(Produto::getPreco)));
        System.out.println(valorTotalPrecosPorCategoria);
    }
}
