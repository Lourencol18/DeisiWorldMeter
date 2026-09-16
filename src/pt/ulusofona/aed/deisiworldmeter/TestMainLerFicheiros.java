package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMainLerFicheiros {

    @Test
    public void testeLeituraArquivosSemErros() {
        File folder = new File("test-files/SemErros");
        boolean result = Main.parseFiles(folder);

        assertTrue(result);
    }

    @Test
    public void testeQuantidadeCidadesCarregadas() {
        File folder = new File("test-files/SemErros");
        boolean result = Main.parseFiles(folder);
        assertTrue(result);

        assertEquals(6, Main.cidades.size());
    }

}
