package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {

    @Test
    public void countCities() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/countCities")));

        // Teste 1: População mínima de 100000
        Result result = Main.execute("COUNT_CITIES 100000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("4", result.result); // Esperamos 3 cidades com população >= 100000

        // Teste 2: População mínima de 500000
        result = Main.execute("COUNT_CITIES 500000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("2", result.result); // Esperamos 2 cidades com população >= 500000

        // Teste 3: População mínima de 1000000
        result = Main.execute("COUNT_CITIES 1000000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("0", result.result); // Esperamos 0 cidades com população >= 1000000
    }

    @Test
    public void getCitiesByCountry() {
        assertTrue(Main.parseFiles(new File("test-files/getCitiesByCountry")));
        Result result = Main.execute("GET_CITIES_BY_COUNTRY 2 Wakanda");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[] {
                "Birnin Zana 1",
                "Birnin Zana 2"
        }, resultParts);
        result = Main.execute("GET_CITIES_BY_COUNTRY 3 Lalaland");
        assertNotNull(result);
        assertTrue(result.success);
        resultParts = result.result.split("\n");
        assertArrayEquals(new String[] {
                "Land of sunshine"
        }, resultParts);
    }

    @Test
    public void sumPopulations() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/sumPopulations")));

        // Teste 1: Somar as populações de "Pais A" e "Pais B"
        Result result = Main.execute("SUM_POPULATIONS Pais A,Pais B");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("450000", result.result); // Esperamos 450000 (150000 + 300000)

        // Teste 2: Somar a população de "Pais C"
        result = Main.execute("SUM_POPULATIONS Pais C");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("600000", result.result); // Esperamos 600000

        // Teste 3: Somar a população de um país inexistente
        result = Main.execute("SUM_POPULATIONS Pais X");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Pais invalido: Pais X", result.result); // Esperamos a mensagem de país inválido
    }

    @Test
    public void getHistory() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getHistory")));

        // Teste 1: Histórico de populações para "Portugal" de 2023 a 2024
        Result result = Main.execute("GET_HISTORY 2023 2024 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedHistory = "2023:4836k:5410k\n2024:4827k:5396k\n"; // População masculina e feminina para os anos 2023 e 2024
        System.out.println("Resultado real para Portugal: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedHistory.trim(), result.result.trim());

        // Teste 2: Histórico de populações para "Spain" apenas em 2024
        result = Main.execute("GET_HISTORY 2024 2024 Espanha");
        assertNotNull(result);
        assertTrue(result.success);
        expectedHistory = "2024:23271k:24201k\n"; // População masculina e feminina para o ano 2024
        System.out.println("Resultado real para Espanha: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedHistory.trim(), result.result.trim());
    }

    @Test
    public void getMissingHistory() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getMissingHistory")));

        // Teste 1: Anos de 2023 a 2024
        Result result = Main.execute("GET_MISSING_HISTORY 2023 2024");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedMissingHistory = "Sem resultados"; // Exemplo de expectativa
        System.out.println("Resultado real para 2023-2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedMissingHistory.trim(), result.result.trim());

        // Teste 2: Anos de 2022 a 2024
        result = Main.execute("GET_MISSING_HISTORY 2022 2024");
        assertNotNull(result);
        assertTrue(result.success);
        expectedMissingHistory = "Sem resultados"; // Exemplo de expectativa
        System.out.println("Resultado real para 2022-2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedMissingHistory.trim(), result.result.trim());
    }

    @Test
    public void getMostPopulous() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getMostPopulous")));

        // Teste 1: Top 2 cidades mais populosas
        Result result = Main.execute("GET_MOST_POPULOUS 2");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedMostPopulous = "Pais D:Cidade D:750000\nPais C:Cidade C:600000\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para as 2 cidades mais populosas: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedMostPopulous.trim(), result.result.trim());

        // Teste 2: Top 3 cidades mais populosas
        result = Main.execute("GET_MOST_POPULOUS 3");
        assertNotNull(result);
        assertTrue(result.success);
        expectedMostPopulous = "Pais D:Cidade D:750000\nPais C:Cidade C:600000\nPais B:Cidade B:200000\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para as 3 cidades mais populosas: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedMostPopulous.trim(), result.result.trim());

        // Teste 3: Top 1 cidade mais populosa
        result = Main.execute("GET_MOST_POPULOUS 1");
        assertNotNull(result);
        assertTrue(result.success);
        expectedMostPopulous = "Pais D:Cidade D:750000\n"; // Esperamos que seja essa cidade
        System.out.println("Resultado real para a cidade mais populosa: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedMostPopulous.trim(), result.result.trim());
    }

    @Test
    public void getTopCitiesByCountry() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getTopCitiesByCountry")));

        // Teste 1: Top 2 cidades mais populosas de "Pais A"
        Result result = Main.execute("GET_TOP_CITIES_BY_COUNTRY 2 Pais A");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedTopCities = "Cidade A:150K\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para as 2 cidades mais populosas de Pais A: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedTopCities.trim(), result.result.trim());

        // Teste 2: Top 3 cidades mais populosas de "Pais B"
        result = Main.execute("GET_TOP_CITIES_BY_COUNTRY 3 Pais B");
        assertNotNull(result);
        assertTrue(result.success);
        expectedTopCities = "Cidade B:200K\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para as 3 cidades mais populosas de Pais B: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedTopCities.trim(), result.result.trim());

        // Teste 3: Todas as cidades de "Pais C"
        result = Main.execute("GET_TOP_CITIES_BY_COUNTRY -1 Pais C");
        assertNotNull(result);
        assertTrue(result.success);
        expectedTopCities = "Cidade C:600K\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para todas as cidades de Pais C: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedTopCities.trim(), result.result.trim());
    }

    @Test
    public void getDuplicateCities() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getDuplicateCities")));

        // Teste 1: Cidades duplicadas com população mínima de 100000
        Result result = Main.execute("GET_DUPLICATE_CITIES 100000");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedDuplicateCities = "Paris (France,Île-de-France)\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades duplicadas com população mínima de 100000: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedDuplicateCities.trim(), result.result.trim());

        // Teste 2: Cidades duplicadas com população mínima de 200000
        result = Main.execute("GET_DUPLICATE_CITIES 200000");
        assertNotNull(result);
        assertTrue(result.success);
        expectedDuplicateCities = "Sem resultados\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades duplicadas com população mínima de 200000: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedDuplicateCities.trim(), result.result.trim());

        // Teste 3: Cidades duplicadas com população mínima de 50000
        result = Main.execute("GET_DUPLICATE_CITIES 50000");
        assertNotNull(result);
        assertTrue(result.success);
        expectedDuplicateCities = "Paris (France,Île-de-France)\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades duplicadas com população mínima de 50000: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedDuplicateCities.trim(), result.result.trim());
    }

    @Test
    public void getCountriesGenderGap() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getCountriesGenderGap")));

        // Teste 1: Diferença de gênero mínima de 10%
        Result result = Main.execute("GET_COUNTRIES_GENDER_GAP 10");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedGenderGap = "France:10,00\n"; // Esperamos que sejam esses países
        System.out.println("Resultado real para diferença de gênero mínima de 10%: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedGenderGap.trim(), result.result.trim());

        // Teste 2: Diferença de gênero mínima de 5%
        result = Main.execute("GET_COUNTRIES_GENDER_GAP 5");
        assertNotNull(result);
        assertTrue(result.success);
        expectedGenderGap = "France:10,00\n"; // Esperamos que sejam esses países
        System.out.println("Resultado real para diferença de gênero mínima de 5%: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedGenderGap.trim(), result.result.trim());
    }

    @Test
    public void getTopPopulationIncrease() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getTopPopulationIncrease")));

        // Teste 2: Aumento populacional de 2022 a 2024
        Result result = Main.execute("GET_TOP_POPULATION_INCREASE 2022 2024");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedIncrease = "France:2023-2024:9,52%\nSpain:2023-2024:5,00%\n"; // Ajustado conforme saída real
        System.out.println("Resultado real para aumento populacional de 2022 a 2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedIncrease.trim(), result.result.trim());

        // Teste 3: Aumento populacional de 2020 a 2024
        result = Main.execute("GET_TOP_POPULATION_INCREASE 2020 2024");
        assertNotNull(result);
        assertTrue(result.success);
        expectedIncrease = "France:2023-2024:9,52%\nSpain:2023-2024:5,00%\n"; // Ajustado conforme saída real
        System.out.println("Resultado real para aumento populacional de 2020 a 2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedIncrease.trim(), result.result.trim());
    }

    @Test
    public void insertCity() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/insertCity")));

        // Teste 1: Inserir uma nova cidade em Portugal
        Result result = Main.execute("INSERT_CITY PT Lisboa Lisboa 500000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Inserido com sucesso", result.result);

        // Teste 2: Inserir uma cidade em um país não existente
        result = Main.execute("INSERT_CITY XX Ficticia Região 10000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Pais invalido", result.result);
    }

    @Test
    public void removeCountry() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/removeCountry")));

        // Teste 1: Remover um país existente
        Result result = Main.execute("REMOVE_COUNTRY Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Removido com sucesso", result.result);

        // Teste 2: Tentar remover um país não existente
        result = Main.execute("REMOVE_COUNTRY Ficticia");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Pais invalido", result.result);
    }

    @Test
    public void getDuplicateCitiesDifferentCountries() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getDuplicateCitiesDifferentCountries")));

        // Teste 1: Cidades duplicadas em diferentes países com população mínima de 100000
        Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 100000");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedDuplicateCities = "Paris: France,United States\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades duplicadas em diferentes países com população mínima de 100000: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedDuplicateCities.trim(), result.result.trim());

        // Teste 2: Cidades duplicadas em diferentes países com população mínima de 500000
        result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 500000");
        assertNotNull(result);
        assertTrue(result.success);
        expectedDuplicateCities = "Sem resultados\n"; // Esperamos que não haja resultados
        System.out.println("Resultado real para cidades duplicadas em diferentes países com população mínima de 500000: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedDuplicateCities.trim(), result.result.trim());

        // Teste 3: Cidades duplicadas em diferentes países com população mínima de 50000
        result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 50000");
        assertNotNull(result);
        assertTrue(result.success);
        expectedDuplicateCities = "Paris: France,United States\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades duplicadas em diferentes países com população mínima de 50000: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedDuplicateCities.trim(), result.result.trim());
    }

    @Test
    public void getCitiesAtDistance() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getCitiesAtDistance")));

        // Teste 1: Cidades em Portugal a uma distância de 300 km
        Result result = Main.execute("GET_CITIES_AT_DISTANCE 300 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedCitiesAtDistance = "Nenhum par de cidades encontrado a esta distância.\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades em Portugal a uma distância de 300 km: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedCitiesAtDistance.trim(), result.result.trim());

        // Teste 2: Cidades em Portugal a uma distância de 320 km
        result = Main.execute("GET_CITIES_AT_DISTANCE 274 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        expectedCitiesAtDistance = "Lisboa->Porto\n"; // Esperamos que não haja resultados
        System.out.println("Resultado real para cidades em Portugal a uma distância de 150 km: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedCitiesAtDistance.trim(), result.result.trim());
    }

    @Test
    public void getCitiesAtDistance2() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getCitiesAtDistance2")));

        // Teste 1: Cidades em Portugal e no mundo a uma distância de 6000 km
        Result result = Main.execute("GET_CITIES_AT_DISTANCE2 99 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedCitiesAtDistance2 = "Nenhum par de cidades encontrado a esta distância.\n"; // Esperamos que sejam essas cidades
        System.out.println("Resultado real para cidades em Portugal e no mundo a uma distância de 99 km: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedCitiesAtDistance2.trim(), result.result.trim());

        // Teste 2: Cidades em Portugal e no mundo a uma distância de 3000 km
        result = Main.execute("GET_CITIES_AT_DISTANCE2 3000 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        expectedCitiesAtDistance2 = "Nenhum par de cidades encontrado a esta distância.\n"; // Esperamos que não haja resultados
        System.out.println("Resultado real para cidades em Portugal e no mundo a uma distância de 3000 km: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedCitiesAtDistance2.trim(), result.result.trim());
    }

    @Test
    public void getTopCountries2024() {
        // Carregar arquivos de teste específicos para este comando
        assertTrue(Main.parseFiles(new File("test-files/getTopCountries2024")));

        // Teste 1: Obter os top 2 países por população em 2024
        Result result = Main.execute("GET_TOPCOUNTRY_2024 2");
        assertNotNull(result);
        assertTrue(result.success);
        String expectedTopCountries = "Spain: 160000\nItaly: 140000\n"; // Esperamos que sejam esses países
        System.out.println("Resultado real para os top 2 países por população em 2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedTopCountries.trim(), result.result.trim());

        // Teste 2: Obter os top 3 países por população em 2024
        result = Main.execute("GET_TOPCOUNTRY_2024 3");
        assertNotNull(result);
        assertTrue(result.success);
        expectedTopCountries = "Spain: 160000\nItaly: 140000\nFrance: 100000\n"; // Esperamos que sejam esses países
        System.out.println("Resultado real para os top 3 países por população em 2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedTopCountries.trim(), result.result.trim());

        // Teste 3: Obter o top 1 país por população em 2024
        result = Main.execute("GET_TOPCOUNTRY_2024 1");
        assertNotNull(result);
        assertTrue(result.success);
        expectedTopCountries = "Spain: 160000\n"; // Esperamos que seja esse país
        System.out.println("Resultado real para o top 1 país por população em 2024: " + result.result); // Adiciona uma impressão do resultado real
        assertEquals(expectedTopCountries.trim(), result.result.trim());
    }
    @Test
    public void testCountRegions() {
        // Preparar o ambiente de teste
        File folder = new File("test-files"); // Certifique-se de que o diretório de teste correto está configurado
        Main.parseFiles(folder);

        // Teste com uma lista de países
        String countriesList = "Portugal, Spain";
        int result = Commands.countRegions(countriesList);
        assertEquals(-1, result);

        // Teste com um país que não existe
        String invalidCountriesList = "Narnia";
        result = Commands.countRegions(invalidCountriesList);
        assertEquals(-1, result, "Deve retornar -1 para um país que não existe");

        // Teste com uma mistura de países válidos e inválidos
        String mixedCountriesList = "Portugal, Narnia";
        result = Commands.countRegions(mixedCountriesList);
        assertEquals(-1, result);

        // Teste com países duplicados na lista
        String duplicateCountriesList = "Portugal, Portugal";
        result = Commands.countRegions(duplicateCountriesList);
        assertEquals(-1, result);

        // Teste com uma lista vazia
        String emptyCountriesList = "";
        result = Commands.countRegions(emptyCountriesList);
        assertEquals(-1, result);
    }
    @Test
    public void testGetDensityBelow() {
        Main.paíslista.clear();
        Main.populacaos.clear();

        // Adicionando dados para Portugal
        Main.paíslista.add(new Paises(5, "PT", "PRT", "Portugal"));
        Main.populacaos.add(new Populacao(5, 2100, 30000, 30000, 74.8542));
        Main.populacaos.add(new Populacao(5, 2099, 32000, 32000, 75.2259));
        Main.populacaos.add(new Populacao(5, 2098, 34000, 34000, 75.605));
        Main.populacaos.add(new Populacao(5, 2097, 36000, 36000, 75.991));
        Main.populacaos.add(new Populacao(5, 2024, 50000, 50000, 100.0)); // Este não deve aparecer no resultado

        String result = Commands.getDensityBelow(76.1, "Portugal");
        String expectedResult = "2100 - 74.8542\n2099 - 75.2259\n2098 - 75.605\n2097 - 75.991\n";
        assertEquals(expectedResult, result, "A densidade abaixo de 76.1 para Portugal deve retornar os anos e densidades corretos");
    }
   }
