package br.com.alura.screenmatch.desafio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int numero1,numero2;
        System.out.println("Digite o primeiro numero: ");
        numero1 = leitor.nextInt();
        System.out.println("Digite o segundo numero: ");
        numero2 = leitor.nextInt();

        Multiplicador multiplicador = (valor1, valor2) -> valor1 * valor2;
        System.out.println("Resultado: " + multiplicador.multiplicar(numero1,numero2));

        VerificarNumero verificarNumero = n -> {
            if (n <= 1) return false;
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) return false;
            }
            return true;
        };

        System.out.println("O primeiro número "+(verificarNumero.isNumeroPrimo(numero1)?"é Primo":"Não é primo"));
        System.out.println("O segundo número  "+(verificarNumero.isNumeroPrimo(numero2)?"é Primo":"Não é primo"));

        String palavra;
        System.out.println("Digite uma palavra: ");
        palavra = leitor.next();

        TransformarString transformarString = String::toUpperCase;
        System.out.println("A palavra transformada é : "+transformarString.transformar(palavra));

        VerificarPalindromo palindromo = p ->{
            String reversa = new StringBuilder(palavra).reverse().toString();
            if(reversa.equalsIgnoreCase(p)) return true;
            return false;
        };

        System.out.println(new StringBuilder().append("A palavra é um palíndomo ? ").append(palindromo.isPalindromo(palavra)?"Sim":"Não").toString());

        List<Integer> listaNumeros = Arrays.asList(1,2,3,4,5,6);
        listaNumeros.replaceAll(n -> n*3);
        System.out.println(listaNumeros);

        List<String> listaNomes = Arrays.asList("Jorge","Marcos","Ana","Ana Clara",
                "Lucas","Zilma","Vicente");
        listaNomes.sort(String::compareTo);
        System.out.println(listaNomes);

        Divisor divisor = (a,b) ->{
          if(b==0) throw new ArithmeticException();
          return a/b;
        };
        System.out.println("Resultado da divisão: "+divisor.dividir(numero1,numero2));
        System.out.println("Resultado da divisão por zero: "+divisor.dividir(numero1,0));
    }
}
