package pt.ulusofona.aed.deisiworldmeter;

public class InputInvalido {
    String nome;
    int linhasCorretas;
    int linhasIncorretas;

    int primeiroErro;

    public InputInvalido() {
    }

    public InputInvalido(String nome, int linhasCorretas, int linhasIncorretas, int primeiroErro) {
        this.nome = nome;
        this.linhasCorretas = linhasCorretas;
        this.linhasIncorretas = linhasIncorretas;
        this.primeiroErro = primeiroErro;
    }

    @Override
    public String toString() {
        return  nome + " | " +
                linhasCorretas + " | " +
                linhasIncorretas + " | " +
                primeiroErro;
    }
}

