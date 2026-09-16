package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    String alfa2;
    String nome;
    String regiao;
    double populacao;
    double latitude;
    double longitude;

    public Cidade(String alfa2, String nome, String regiao, double populacao, double latitude, double longitude) {
        this.alfa2 = alfa2;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String toString() {
        return nome + " | " + alfa2.toUpperCase() + " | " + regiao + " | " + (int) populacao + " | (" + latitude + "," + longitude + ")";
    }
}
