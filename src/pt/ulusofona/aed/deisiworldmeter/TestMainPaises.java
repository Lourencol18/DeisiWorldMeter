package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestMainPaises {

    @Test
    public void testConversaoParaStringCorreta() {
        Paises pais1 = new Paises(699, "FR", "FRA", "França");
        Paises pais2 = new Paises(600, "US", "USA", "Estados Unidos");

        String stringPais1 = pais1.toString();
        String stringPais2 = pais2.toString();

        assertEquals("França | 699 | FR | FRA", stringPais1, "Falha ao verificar conversão para string do país 1");
        assertEquals("Estados Unidos | 600 | US | USA", stringPais2, "Falha ao verificar conversão para string do país 2");
    }

    @Test
    public void testStringContemIdCorreto() {
        Paises país1 = new Paises(699, "FR", "FRA", "França");
        Paises país2 = new Paises(600, "US", "USA", "Estados Unidos");

        String stringPaís1 = país1.toString();
        String stringPaís2 = país2.toString();

        assertTrue(stringPaís1.contains("699"), "Falha ao verificar se a string contém o ID correto para o país 1");
        assertTrue(stringPaís2.contains("600"), "Falha ao verificar se a string contém o ID correto para o país 2");
    }

    @Test
    public void testStringContemNomeCorreto() {
        Paises país1 = new Paises(699, "FR", "FRA", "França");
        Paises país2 = new Paises(600, "US", "USA", "Estados Unidos");

        String stringPaís1 = país1.toString();
        String stringPaís2 = país2.toString();

        assertTrue(stringPaís1.contains("França"), "Falha ao verificar se a string contém o nome correto para o país 1");
        assertTrue(stringPaís2.contains("Estados Unidos"), "Falha ao verificar se a string contém o nome correto para o país 2");
    }

    @Test
    public void testStringContemCodigoAlfa2Correto() {
        Paises país1 = new Paises(699, "FR", "FRA", "França");
        Paises país2 = new Paises(600, "US", "USA", "Estados Unidos");

        String stringPaís1 = país1.toString();
        String stringPaís2 = país2.toString();

        assertTrue(stringPaís1.contains("FR"), "Falha ao verificar se a string contém o código alfa2 correto para o país 1");
        assertTrue(stringPaís2.contains("US"), "Falha ao verificar se a string contém o código alfa2 correto para o país 2");
    }

    @Test
    public void testStringNaoContemQuantidadeParaIdMenorQue700() {
        Paises pais1 = new Paises(699, "FR", "França", "Europe");
        Paises pais2 = new Paises(600, "US", "Estados Unidos", "North America");

        String stringPais1 = pais1.toString();
        String stringPais2 = pais2.toString();

        assertFalse(stringPais1.contains("Quantidade"), "Falha ao verificar se a string não contém quantidade para país com ID menor que 700");
        assertFalse(stringPais2.contains("Quantidade"), "Falha ao verificar se a string não contém quantidade para país com ID menor que 700");
    }
}
