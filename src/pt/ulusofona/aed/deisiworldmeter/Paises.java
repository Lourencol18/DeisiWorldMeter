package pt.ulusofona.aed.deisiworldmeter;

public class Paises {
    int id;
    String alfa2;
    String alfa3;
    String nome;
    int contagemPopulacao;

    public Paises(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
        this.contagemPopulacao = 0;
    }



    @Override
    public String toString() {
        if (id > 700) {
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase() + " | " + contagemPopulacao;
        } else {
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();
        }
    }
}
