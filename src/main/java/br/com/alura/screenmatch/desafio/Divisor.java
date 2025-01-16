package br.com.alura.screenmatch.desafio;

@FunctionalInterface
public interface Divisor {
    int dividir(int numA, int numB) throws ArithmeticException;
}
