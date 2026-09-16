package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMainCities {

    @Test
    public void testConversaoParaStringFormatoCSV() {
        Cidade cidade1 = new Cidade("AD", "Andorra la Vella", "07", 20430, 42.5, 1.5166667);
        Cidade cidade2 = new Cidade("AD", "Canillo", "02", 3292, 42.5666667, 1.6);

        String stringCidade1 = cidade1.toString();
        String stringCidade2 = cidade2.toString();

        assertFalse(stringCidade1.contains("AD") && stringCidade1.contains("Andorra la Vella") && stringCidade1.contains("07") && stringCidade1.contains("20430.0") && stringCidade1.contains("42.5") && stringCidade1.contains("1.5166667"), "Falha ao verificar formato CSV da string para a cidade 1");
        assertFalse(stringCidade2.contains("AD") && stringCidade2.contains("Canillo") && stringCidade2.contains("02") && stringCidade2.contains("3292.0") && stringCidade2.contains("42.5666667") && stringCidade2.contains("1.6"), "Falha ao verificar formato CSV da string para a cidade 2");
    }

    @Test
    public void testStringContemNomeCorreto() {
        Cidade cidade1 = new Cidade("PT", "Lisboa", "Lisboa", 505526, 38.7167, -9.1333);
        Cidade cidade2 = new Cidade("ES", "Madrid", "Madrid", 3207247, 40.4189, -3.6919);

        String stringCidade1 = cidade1.toString();
        String stringCidade2 = cidade2.toString();

        assertTrue(stringCidade1.contains("Lisboa"), "Falha ao verificar se a string contém o nome correto para a cidade 1");
        assertTrue(stringCidade2.contains("Madrid"), "Falha ao verificar se a string contém o nome correto para a cidade 2");
    }

    @Test
    public void testStringContemCodigoAlfa2Correto() {
        Cidade cidade1 = new Cidade("PT", "Lisboa", "Lisboa", 505526, 38.7167, -9.1333);
        Cidade cidade2 = new Cidade("ES", "Madrid", "Madrid", 3207247, 40.4189, -3.6919);

        String stringCidade1 = cidade1.toString();
        String stringCidade2 = cidade2.toString();

        assertTrue(stringCidade1.contains("PT"), "Falha ao verificar se a string contém o código alfa2 correto para a cidade 1");
        assertTrue(stringCidade2.contains("ES"), "Falha ao verificar se a string contém o código alfa2 correto para a cidade 2");
    }

    @Test
    public void testStringContemRegiaoCorreta() {
        Cidade cidade1 = new Cidade("PT", "Lisboa", "Lisboa", 505526, 38.7167, -9.1333);
        Cidade cidade2 = new Cidade("ES", "Madrid", "Madrid", 3207247, 40.4189, -3.6919);

        String stringCidade1 = cidade1.toString();
        String stringCidade2 = cidade2.toString();

        assertTrue(stringCidade1.contains("Lisboa"), "Falha ao verificar se a string contém a região correta para a cidade 1");
        assertTrue(stringCidade2.contains("Madrid"), "Falha ao verificar se a string contém a região correta para a cidade 2");
    }

    @Test
    public void testStringContemPopulacaoCorreta() {
        Cidade cidade1 = new Cidade("PT", "Lisboa", "Lisboa", 505526, 38.7167, -9.1333);
        Cidade cidade2 = new Cidade("ES", "Madrid", "Madrid", 3207247, 40.4189, -3.6919);

        String stringCidade1 = cidade1.toString();
        String stringCidade2 = cidade2.toString();

        assertTrue(stringCidade1.contains("505526"), "Falha ao verificar se a string contém a população correta para a cidade 1");
        assertTrue(stringCidade2.contains("3207247"), "Falha ao verificar se a string contém a população correta para a cidade 2");
    }
}
