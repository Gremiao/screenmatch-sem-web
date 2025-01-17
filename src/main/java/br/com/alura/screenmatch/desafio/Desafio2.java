package br.com.alura.screenmatch.desafio;

import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Desafio2 {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        numeros.stream().filter(x -> x % 2 == 0).forEach(System.out::println);

        List<String> palavras = Arrays.asList("java", "stream", "lambda");
        palavras.stream().map(p -> p.toUpperCase()).forEach(System.out::println);

        numeros.stream()
               .filter(x -> x % 2 != 0)
               .map(n -> n *2).collect(Collectors.toList())
               .forEach(System.out::println);

        List<String> listaDuplicada = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        listaDuplicada.stream()
                      .collect(Collectors.toSet())
                      .forEach(System.out::println);

        List<List<Integer>> listaDeNumeros = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(5, 6, 7, 8),
                Arrays.asList(9, 10, 11, 12)
        );
        listaDeNumeros.stream()
                      .flatMap(n -> n.stream().filter(y -> y %2 == 0)).forEach(System.out::println);

        //Mesma solução acima
        listaDeNumeros.stream()
                      .flatMap(List::stream)
                      .filter(Desafio2::ehPrimo)
                      .toList().forEach(System.out::println);


        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("Alice", 22),
                new Pessoa("Bob", 17),
                new Pessoa("Charlie", 19)
        );

        pessoas.stream()
               .filter(p -> p.getIdade() >= 18)
               .sorted(Comparator.comparing(Pessoa::getNome))
               .forEach(i -> System.out.println(i.getNome()));

        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Teclado", 200.0, "Eletrônicos"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Monitor", 900.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis")
        );

        produtos.stream()
                .filter(c -> c.getCategoria().equalsIgnoreCase("Eletrônicos"))
                .filter(p -> p.getPreco()<=1000)
                .sorted(Comparator.comparing(Produto::getPreco))
                .forEach(i -> System.out.println("Produto :"+i.getNome() + " | Preço :"+i.getPreco() + " | Categoria :" + i.getCategoria()));

        produtos.stream()
                .filter(c -> c.getCategoria().equalsIgnoreCase("Eletrônicos"))
                .sorted(Comparator.comparing(Produto::getPreco))
                .limit(3)
                .forEach(i -> System.out.println("Produto :"+i.getNome() + " | Preço :"+i.getPreco() + " | Categoria :" + i.getCategoria()));
    }

    private static boolean ehPrimo(int numero) {
        if (numero < 2) return false; // Números menores que 2 não são primos
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false; // Divisível por outro número que não 1 e ele mesmo
            }
        }
        return true;
    }
}
