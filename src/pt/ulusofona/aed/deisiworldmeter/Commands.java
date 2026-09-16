package pt.ulusofona.aed.deisiworldmeter;

import java.util.*;


public class Commands {
    static String help(){
        return  "-------------------------\n" +
                "Comandos possíveis:\n" +
                "COUNT_CITIES <min_population>\n" +
                "GET_CITIES_BY_COUNTRY <num-results> <country-name>\n" +
                "SUM_POPULATIONS <countries-list>\n" +
                "GET_HISTORY <year-start> <year-end> <country_name>\n" +
                "GET_MISSING_HISTORY <year-start> <year-end>\n" +
                "GET_MOST_POPULOUS <num-results>\n" +
                "GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>\n" +
                "GET_DUPLICATE_CITIES <min_population>\n" +
                "GET_COUNTRIES_GENDER_GAP <min-gender-gap>\n" +
                "GET_TOP_POPULATION_INCREASE <year-start> <year-end>\n" +
                "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min_population>\n" +
                "GET_CITIES_AT_DISTANCE <distance> <country-name>\n" +
                "GET_CITIES_AT_DISTANCE2 <distance> <country-name>\n" +
                "INSERT_CITY <alfa2> <city-name> <region> <population>\n" +
                "REMOVE_COUNTRY <country-name>\n" +
                "GET_TOPCOUNTRY_2024 <num-results>\n" +
                "COUNT_REGIONS <countries_list>\n" +
                "GET_DENSITY_BELOW <max-density> <country_name>\n" +
                "HELP\n" +
                "QUIT\n" +
                "--------------------------\n";
    }

    static int countCities(int valorPopulacao) {
        int count = 0; // Inicializa o contador de cidades
        for (Cidade cidade : Main.cidades) { // Itera sobre a lista de cidades
            if (cidade.populacao >= valorPopulacao) { // Verifica se a população da cidade é maior ou igual ao valor fornecido
                count++; // Incrementa o contador se a condição for satisfeita
            }
        }
        return count; // Retorna o número de cidades que atendem à condição
    }

    static ArrayList<Cidade> getCitiesByCountry(int numResults, String countryName) {
        ArrayList<Cidade> result = new ArrayList<>(); // Inicializa a lista de resultados
        HashMap<String, String> countryCodeMap = new HashMap<>(); // Mapa para armazenar nome do país e código alfa2

        // Construir o mapa de nome do país para código alfa2
        for (Paises pais : Main.paíslista) {
            countryCodeMap.put(pais.nome.toLowerCase(), pais.alfa2); // Adiciona o país ao mapa
        }

        // Obter o código alfa2 para o país fornecido
        String alfa2 = countryCodeMap.get(countryName.toLowerCase());

        // Se o país for encontrado, adicionar as cidades correspondentes até o limite especificado
        if (alfa2 != null) {
            for (Cidade cidade : Main.cidades) {
                if (cidade.alfa2.equalsIgnoreCase(alfa2)) { // Verifica se o código alfa2 da cidade coincide com o do país
                    result.add(cidade); // Adiciona a cidade à lista de resultados
                    if (result.size() == numResults) { // Verifica se o número de resultados atingiu o limite
                        break;
                    }
                }
            }
        }
        // Ordenar as cidades pelo nome
        // result.sort(Comparator.comparing(c -> c.nome.toLowerCase()));


        return result; // Retorna a lista de cidades correspondentes
    }


    static int sumPopulations(List<String> countries) {
        int totalPopulation = 0; // Inicializa a soma da população
        Map<String, Integer> countryIdMap = new HashMap<>(); // Map para armazenar nome do país e seu ID

        // Mapeia o nome do país para o seu ID
        for (Paises pais : Main.paíslista) {
            countryIdMap.put(pais.nome.toLowerCase(), pais.id); // Adiciona o país ao mapa
        }

        // Constrói um conjunto de IDs de países fornecidos
        Set<Integer> countryIds = new HashSet<>();
        for (String country : countries) {
            Integer countryId = countryIdMap.get(country.trim().toLowerCase());
            if (countryId != null) {
                countryIds.add(countryId); // Adiciona o ID do país ao conjunto
            }
        }

        // Itera sobre as populações e acumula a população dos países especificados
        for (Populacao pop : Main.populacaos) {
            if (pop.ano == 2024 && countryIds.contains(pop.id)) { // Verifica se o ano é 2024 e se o ID do país está no conjunto
                totalPopulation += pop.populacaoMasculina + pop.populacaoFeminina; // Soma as populações masculina e feminina
            }
        }

        return totalPopulation; // Retorna a soma total da população
    }

    public static String getHistory(int yearStart, int yearEnd, String countryName) {
        StringBuilder history = new StringBuilder(); // Inicializa o construtor de strings para o histórico
        boolean countryFound = false; // Flag para indicar se o país foi encontrado
        for (int year = yearStart; year <= yearEnd; year++) { // Itera sobre os anos especificados
            for (Populacao pop : Main.populacaos) {
                if (pop.ano == year) { // Verifica se o ano da população coincide com o ano atual da iteração
                    for (Paises pais : Main.paíslista) {
                        if (pais.nome.equalsIgnoreCase(countryName.trim()) && pais.id == pop.id) { // Verifica se o nome e o ID do país coincidem
                            countryFound = true; // Marca que o país foi encontrado
                            // Cortar os três últimos dígitos e adicionar "k"
                            long popMasculina = pop.populacaoMasculina / 1000;
                            long popFeminina = pop.populacaoFeminina / 1000;
                            history.append(year)
                                    .append(":")
                                    .append(popMasculina).append("k:")
                                    .append(popFeminina).append("k\n");
                            break;
                        }
                    }
                }
            }
            ///if (countryFound && yearCount > 0) {
            //7 long averagePopulation = totalPopulationSum / yearCount; // Calcula a média da população total
            ///history.append("Média da População Total: ").append(averagePopulation).append("k\n"); // Adiciona a média ao históric
        }
        return countryFound ? history.toString() : null; // Retorna o histórico ou null se o país não for encontrado
    }

    public static String getMissingHistory(int yearStart, int yearEnd) {
        StringBuilder missingHistory = new StringBuilder(); // Inicializa o construtor de strings para o histórico ausente
        Map<Integer, Set<Integer>> paisesComDados = new HashMap<>(); // Mapa para armazenar os países com dados disponíveis

        // Adicionar países com dados nos anos especificados ao mapa
        for (int year = yearStart; year <= yearEnd; year++) {
            for (Populacao pop : Main.populacaos) {
                if (pop.ano == year) {
                    paisesComDados.putIfAbsent(pop.id, new HashSet<>());
                    paisesComDados.get(pop.id).add(year); // Adiciona o ano aos dados do país
                }
            }
        }

        // Verificar quais países não têm dados para todos os anos especificados
        for (Paises pais : Main.paíslista) {
            boolean missingData = false; // Flag para indicar dados ausentes
            for (int year = yearStart; year <= yearEnd; year++) {
                if (!paisesComDados.containsKey(pais.id) || !paisesComDados.get(pais.id).contains(year)) {
                    missingData = true; // Marca que há dados ausentes para este país
                    break;
                }
            }
            if (missingData) {
                missingHistory.append(pais.alfa2).append(":").append(pais.nome).append("\n"); // Adiciona o país ao histórico ausente
            }
        }

        return missingHistory.length() > 0 ? missingHistory.toString() : "Sem resultados"; // Retorna o histórico ausente ou "Sem resultados"
    }

    public static String getMostPopulous(int numResults) {
        Map<String, Cidade> mostPopulousCities = new HashMap<>(); // Mapa para armazenar a cidade mais populosa de cada país

        // Encontrar a cidade mais populosa de cada país
        for (Cidade cidade : Main.cidades) {
            mostPopulousCities.putIfAbsent(cidade.alfa2, cidade); // Adiciona a cidade se o país ainda não tiver uma cidade armazenada
            if (mostPopulousCities.get(cidade.alfa2).populacao < cidade.populacao) {
                mostPopulousCities.put(cidade.alfa2, cidade); // Atualiza a cidade se a nova cidade for mais populosa
            }
        }

        // Criar uma lista de cidades ordenadas pela população em ordem decrescente
        List<Cidade> sortedCities = new ArrayList<>(mostPopulousCities.values());
        sortedCities.sort((c1, c2) -> Double.compare(c2.populacao, c1.populacao)); // Ordena as cidades pela população

        StringBuilder result = new StringBuilder(); // Inicializa o construtor de strings para o resultado

        // Iterar sobre as cidades ordenadas e incluir o nome do país
        for (int i = 0; i < numResults && i < sortedCities.size(); i++) {
            Cidade cidade = sortedCities.get(i);
            String countryName = null;
            for (Paises pais : Main.paíslista) {
                if (pais.alfa2.equalsIgnoreCase(cidade.alfa2)) {
                    countryName = pais.nome; // Encontra o nome do país
                    break;
                }
            }
            result.append(countryName).append(":").append(cidade.nome).append(":").append((int) cidade.populacao).append("\n"); // Adiciona a cidade ao resultado
        }

        return result.toString(); // Retorna o resultado
    }


    public static String getTopCitiesByCountry(int numResults, String countryName) {
        // Criar um mapa de nome do país para o código alfa2
        Map<String, String> countryMap = new HashMap<>();
        for (Paises pais : Main.paíslista) {
            countryMap.put(pais.nome.toLowerCase(), pais.alfa2); // Adiciona o país ao mapa
        }

        // Encontrar o código alfa2 do país
        String alfa2 = countryMap.get(countryName.toLowerCase());

        if (alfa2 == null) {
            return "País não encontrado."; // Retorna mensagem de erro se o país não for encontrado
        }

        // Usar uma lista para armazenar as cidades do país com população >= 10000
        List<Cidade> citiesByCountry = new ArrayList<>();
        for (Cidade cidade : Main.cidades) {
            if (cidade.alfa2.equalsIgnoreCase(alfa2) && cidade.populacao >= 10000) {
                citiesByCountry.add(cidade); // Adiciona a cidade à lista se a população for >= 10000
            }
        }

        // Ordenar as cidades pela população em ordem decrescente e alfabeticamente se as populações forem iguais a nível dos milhares
        citiesByCountry.sort((c1, c2) -> {
            int populationComparison = Long.compare((long) (c2.populacao / 1000), (long) (c1.populacao / 1000));
            if (populationComparison == 0) {
                return c1.nome.compareToIgnoreCase(c2.nome); // Ordena alfabeticamente se as populações forem iguais
            }
            return populationComparison; // Ordena pela população
        });

        StringBuilder result = new StringBuilder(); // Inicializa o construtor de strings para o resultado

        // Adicionar as cidades ao resultado, até o número especificado ou todas se numResults for -1
        int limit = (numResults == -1) ? citiesByCountry.size() : numResults;
        for (int i = 0; i < limit && i < citiesByCountry.size(); i++) {
            Cidade cidade = citiesByCountry.get(i);
            String formattedPopulation = String.format("%dK", (int) (cidade.populacao / 1000));
            result.append(cidade.nome).append(":").append(formattedPopulation).append("\n"); // Adiciona a cidade ao resultado
        }

        return result.toString(); // Retorna o resultado
    }

    public static String removeCountry(String countryName) {
        // Criar um mapa de nome de país para o objeto país
        Map<String, Paises> countryMap = new HashMap<>();
        for (Paises pais : Main.paíslista) {
            countryMap.put(pais.nome.toLowerCase(), pais); // Adiciona o país ao mapa
        }

        // Encontrar o país correspondente ao nome fornecido
        Paises countryToRemove = countryMap.get(countryName.toLowerCase());

        // Se o país não for encontrado, retornar "Pais invalido"
        if (countryToRemove == null) {
            return "Pais invalido";
        }

        // Remover o país da lista de países
        Main.paíslista.remove(countryToRemove);

        // Remover todas as cidades associadas ao país
        Iterator<Cidade> cidadeIterator = Main.cidades.iterator();
        while (cidadeIterator.hasNext()) {
            Cidade cidade = cidadeIterator.next();
            if (cidade.alfa2.equalsIgnoreCase(countryToRemove.alfa2)) {
                cidadeIterator.remove(); // Remove a cidade se o código alfa2 coincidir
            }
        }

        // Remover todas as populações associadas ao país
        Iterator<Populacao> popIterator = Main.populacaos.iterator();
        while (popIterator.hasNext()) {
            Populacao pop = popIterator.next();
            if (pop.id == countryToRemove.id) {
                popIterator.remove(); // Remove a população se o ID coincidir
            }
        }

        return "Removido com sucesso"; // Retorna mensagem de sucesso
    }

    public static String insertCity(String alfa2, String cityName, String region, int population) {
        // Construir um conjunto de códigos alfa2 para rápida verificação
        Set<String> alfa2Set = new HashSet<>();
        for (Paises pais : Main.paíslista) {
            alfa2Set.add(pais.alfa2.toLowerCase());
        }

        // Verificar se o país existe no conjunto
        if (!alfa2Set.contains(alfa2.toLowerCase())) {
            return "País não encontrado.";
        }

        // Criar e adicionar a nova cidade
        Cidade newCity = new Cidade(alfa2, cityName, region, population, 0.0, 0.0);
        Main.cidades.add(newCity);
        return "Inserido com sucesso";
    }

    public static int countRegions(String countriesList) {
        // Lista de países passados por parâmetro, separados por vírgula
        String[] countries = countriesList.split(",");

        // Mapa para armazenar os países e suas regiões únicas
        Map<String, Set<String>> countryRegionsMap = new HashMap<>();

        // Populando o mapa com os países e suas regiões
        for (String countryName : countries) {
            countryName = countryName.trim().toLowerCase();
            Set<String> regions = new HashSet<>();

            for (Cidade cidade : Main.cidades) {
                // Verifica se a cidade pertence ao país atual
                for (Paises pais : Main.paíslista) {
                    if (pais.nome.toLowerCase().equals(countryName) && cidade.alfa2.equalsIgnoreCase(pais.alfa2)) {
                        regions.add(cidade.regiao);
                    }
                }
            }

            if (!regions.isEmpty()) {
                countryRegionsMap.put(countryName, regions);
            }
        }

        // Verifica se nenhum país foi encontrado
        if (countryRegionsMap.isEmpty()) {
            return -1;
        }

        // Soma o número de regiões únicas para cada país
        int totalRegions = 0;
        for (Set<String> regions : countryRegionsMap.values()) {
            totalRegions += regions.size();
        }

        return totalRegions;
    }
    public static String getDensityBelow(double maxDensity, String countryName) {
        // Mapa para associar o nome do país ao seu ID
        Map<String, Integer> countryNameMap = new HashMap<>();
        for (Paises pais : Main.paíslista) {
            countryNameMap.put(pais.nome.toLowerCase(), pais.id);
        }

        // Procurar o ID do país correspondente ao nome fornecido
        Integer countryId = countryNameMap.get(countryName.toLowerCase().trim());

        if (countryId == null) {
            return "Sem resultados"; // Retorna se o país não for encontrado
        }

        // Lista para armazenar os anos e densidades
        List<String> results = new ArrayList<>();

        // Iterar sobre as populações para encontrar as densidades abaixo do valor fornecido
        for (Populacao pop : Main.populacaos) {
            if (pop.id == countryId && pop.densidade < maxDensity) {
                results.add(pop.ano + " - " + pop.densidade);
            }
        }

        if (results.isEmpty()) {
            return "Sem resultados"; // Retorna se não houver densidades abaixo do valor fornecido
        }

        // Ordenar os resultados por ano em ordem decrescente usando Comparator
        results.sort(Comparator.comparingInt(a -> Integer.parseInt(a.split(" - ")[0])));
        results.sort(Comparator.reverseOrder());

        // Construir a string de resultado
        StringBuilder result = new StringBuilder();
        for (String res : results) {
            result.append(res).append("\n");
        }

        return result.toString();
    }












    public static String getDuplicateCities(int minPopulation) {
        Map<String, List<Cidade>> cityMap = new HashMap<>();
        List<String> resultList = new ArrayList<>();

        // Agrupar as cidades pelo nome
        for (Cidade cidade : Main.cidades) {
            if (cidade.populacao >= minPopulation) {
                cityMap
                        .computeIfAbsent(cidade.nome.toLowerCase(), k -> new ArrayList<>())
                        .add(cidade);
            }
        }

        // Armazenar cidades já mostradas para evitar duplicações
        Set<String> shownCities = new HashSet<>();

        // Encontrar e listar cidades duplicadas
        for (Map.Entry<String, List<Cidade>> entry : cityMap.entrySet()) {
            List<Cidade> cities = entry.getValue();
            if (cities.size() > 1) {
                for (int i = 1; i < cities.size(); i++) { // Começa do índice 1 para ignorar o original
                    Cidade cidade = cities.get(i);
                    String countryName = null;
                    for (Paises pais : Main.paíslista) {
                        if (pais.alfa2.equalsIgnoreCase(cidade.alfa2)) {
                            countryName = pais.nome;
                            break;
                        }
                    }
                    String cityDetails = cidade.nome + " (" + countryName + "," + cidade.regiao + ")";
                    if (!shownCities.contains(cityDetails)) {
                        shownCities.add(cityDetails);
                        resultList.add(cityDetails);
                    }
                }
            }
        }

        // Construir a string de resultado a partir da lista de resultados
        StringBuilder result = new StringBuilder();
        for (String city : resultList) {
            result.append(city).append("\n");
        }

        return result.length() > 0 ? result.toString() : "Sem resultados\n";
    }
    public static String getCountriesGenderGap(double minPercentage) {
        Map<Integer, Paises> paisMap = new HashMap<>();
        for (Paises pais : Main.paíslista) {
            paisMap.put(pais.id, pais);
        }

        StringBuilder result = new StringBuilder();
        for (Populacao pop : Main.populacaos) {
            if (pop.ano == 2024) {
                int totalPopulation = pop.populacaoMasculina + pop.populacaoFeminina;
                if (totalPopulation > 0) {
                    double genderGap = ((double)Math.abs(pop.populacaoMasculina - pop.populacaoFeminina) / totalPopulation) * 100;
                    if (Math.abs(genderGap) >= minPercentage) {
                        Paises pais = paisMap.get(pop.id);
                        if (pais != null) {
                            result.append(pais.nome).append(":").append(String.format("%.2f", genderGap)).append("\n");
                        }
                    }
                }
            }
        }

        return result.length() > 0 ? result.toString() : "Sem resultados";
    }
    public static String getTopPopulationIncrease(int startYear, int endYear) {
        Map<Integer, Map<Integer, Integer>> populationByYearAndCountry = new HashMap<>();
        Map<Integer, Paises> paisMap = new HashMap<>();

        // Preenchendo os mapas de países e populações por ano
        for (Paises pais : Main.paíslista) {
            paisMap.put(pais.id, pais);
        }

        for (Populacao pop : Main.populacaos) {
            if (pop.ano >= startYear && pop.ano <= endYear) {
                populationByYearAndCountry
                        .computeIfAbsent(pop.id, k -> new HashMap<>())
                        .put(pop.ano, pop.populacaoMasculina + pop.populacaoFeminina);
            }
        }

        List<String[]> increases = new ArrayList<>();

        // Calculando o aumento populacional para cada par de anos dentro do intervalo fornecido
        for (Integer countryId : populationByYearAndCountry.keySet()) {
            Map<Integer, Integer> populationsByYear = populationByYearAndCountry.get(countryId);
            for (int year1 = startYear; year1 < endYear; year1++) {
                if (populationsByYear.containsKey(year1)) {
                    for (int year2 = year1 + 1; year2 <= endYear; year2++) {
                        if (populationsByYear.containsKey(year2)) {
                            int populationStartYear = populationsByYear.get(year1);
                            int populationEndYear = populationsByYear.get(year2);
                            double increase = ((double) (populationEndYear - populationStartYear) / populationEndYear) * 100;
                            if (increase > 0) {
                                Paises pais = paisMap.get(countryId);
                                increases.add(new String[] { pais.nome, String.valueOf(year1), String.valueOf(year2), String.format("%.2f", increase) });
                            }
                        }
                    }
                }
            }
        }

        // Ordenando e selecionando os 5 maiores aumentos
        Collections.sort(increases, new Comparator<String[]>() {
            public int compare(String[] o1, String[] o2) {
                // Replace commas with dots for correct parsing
                return Double.compare(Double.parseDouble(o2[3].replace(",", ".")), Double.parseDouble(o1[3].replace(",", ".")));
            }
        });

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.min(5, increases.size()); i++) {
            String[] increase = increases.get(i);
            result.append(increase[0])
                    .append(":")
                    .append(increase[1])
                    .append("-")
                    .append(increase[2])
                    .append(":")
                    .append(increase[3])
                    .append("%\n");
        }

        return result.length() > 0 ? result.toString().trim() + "\n" : "Nenhum país encontrado com aumento populacional positivo.\n";
    }
    public static String getDuplicateCitiesDifferentCountries(int minPopulation) {
        Map<String, List<Cidade>> cityMap = new HashMap<>();
        Map<String, Set<String>> duplicatesByCountry = new HashMap<>();
        List<String> resultList = new ArrayList<>();

        // Agrupar as cidades pelo nome
        for (Cidade cidade : Main.cidades) {
            if (cidade.populacao >= minPopulation) {
                if (!cityMap.containsKey(cidade.nome)) {
                    cityMap.put(cidade.nome, new ArrayList<>());
                }
                cityMap.get(cidade.nome).add(cidade);
            }
        }

        // Encontrar cidades duplicadas em diferentes países
        for (Map.Entry<String, List<Cidade>> entry : cityMap.entrySet()) {
            List<Cidade> cities = entry.getValue();
            if (cities.size() > 1) {
                Set<String> countries = new TreeSet<>(); // Usar TreeSet para ordenar alfabeticamente
                for (Cidade cidade : cities) {
                    String countryName = null;
                    for (Paises pais : Main.paíslista) {
                        if (pais.alfa2.equalsIgnoreCase(cidade.alfa2)) {
                            countryName = pais.nome;
                            break;
                        }
                    }
                    if (countryName != null) {
                        countries.add(countryName);
                    }
                }
                if (countries.size() > 1) {
                    duplicatesByCountry.put(entry.getKey(), countries);
                }
            }
        }

        // Construir a lista de resultados a partir do mapa de duplicatas
        for (Map.Entry<String, Set<String>> entry : duplicatesByCountry.entrySet()) {
            StringBuilder cityDetails = new StringBuilder();
            cityDetails.append(entry.getKey()).append(": ");
            for (String country : entry.getValue()) {
                cityDetails.append(country).append(",");
            }
            cityDetails.setLength(cityDetails.length() - 1); // Remover a última vírgula e espaço
            resultList.add(cityDetails.toString());
        }

        // Construir a string de resultado a partir da lista de resultados
        StringBuilder result = new StringBuilder();
        for (String city : resultList) {
            result.append(city).append("\n");
        }

        return result.length() > 0 ? result.toString().trim() + "\n" : "Sem resultados\n";
    }
    public static String getCitiesAtDistance(double distance, String countryName) {
        // Usar um mapa para encontrar rapidamente o código alfa2 do país
        Map<String, String> countryMap = new HashMap<>();
        for (Paises pais : Main.paíslista) {
            countryMap.put(pais.nome.toLowerCase().trim(), pais.alfa2);
        }

        String alfa2 = countryMap.get(countryName.toLowerCase().trim());
        if (alfa2 == null) {
            return "País não encontrado.\n";
        }

        // Usar uma hash table para armazenar as cidades do país
        Map<String, Cidade> citiesByCountry = new HashMap<>();
        for (Cidade cidade : Main.cidades) {
            if (cidade.alfa2.equalsIgnoreCase(alfa2)) {
                citiesByCountry.put(cidade.nome, cidade);
            }
        }

        List<String> cityPairs = new ArrayList<>();
        List<Cidade> cityList = new ArrayList<>(citiesByCountry.values());

        for (int i = 0; i < cityList.size(); i++) {
            Cidade city1 = cityList.get(i);

            for (int j = i + 1; j < cityList.size(); j++) {
                Cidade city2 = cityList.get(j);
                double dist = calculateHaversineDistance(city1.latitude, city1.longitude, city2.latitude, city2.longitude);

                if (dist >= distance - 1 && dist <= distance + 1) {
                    String cityPair = city1.nome.compareTo(city2.nome) < 0 ?
                            city1.nome + "->" + city2.nome :
                            city2.nome + "->" + city1.nome;
                    cityPairs.add(cityPair);
                }
            }
        }

        // Ordenar os pares de cidades alfabeticamente
        Collections.sort(cityPairs);

        StringBuilder result = new StringBuilder();
        for (String pair : cityPairs) {
            result.append(pair).append("\n");
        }

        return result.length() > 0 ? result.toString() : "Nenhum par de cidades encontrado a esta distância.\n";
    }
    public static String getCitiesAtDistance2(double distance, String countryName) {
        // Utilize um mapa para procurar países pelo nome
        Map<String, String> countryMap = new HashMap<>();
        for (Paises pais : Main.paíslista) {
            countryMap.put(pais.nome.toLowerCase().trim(), pais.alfa2);
        }

        // Obtem o código alfa2 do país
        String alfa2 = countryMap.get(countryName.toLowerCase().trim());
        if (alfa2 == null) {
            return "País não encontrado.\n";
        }

        // Separa as cidades em listas de acordo com o código alfa2
        List<Cidade> citiesInCountry = new ArrayList<>();
        List<Cidade> citiesInWorld = new ArrayList<>();

        for (Cidade cidade : Main.cidades) {
            if (cidade.alfa2.equalsIgnoreCase(alfa2)) {
                citiesInCountry.add(cidade);
            } else {
                citiesInWorld.add(cidade);
            }
        }

        Set<String> cityPairs = new HashSet<>();
        double distanceMin = distance - 1;
        double distanceMax = distance + 1;

        // Pré-calcular as coordenadas dos limites do quadrado bruto
        double distanceInDegrees = distance / 111.0; // Aproximação, 1 grau é aproximadamente 111 km
        double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE, maxLon = -Double.MAX_VALUE;
        for (Cidade cidade : citiesInCountry) {
            double lat = cidade.latitude;
            double lon = cidade.longitude;
            minLat = Math.min(minLat, lat - distanceInDegrees);
            maxLat = Math.max(maxLat, lat + distanceInDegrees);
            minLon = Math.min(minLon, lon - distanceInDegrees);
            maxLon = Math.max(maxLon, lon + distanceInDegrees);
        }

        // Usando um laço para comparar as distâncias
        for (Cidade city1 : citiesInCountry) {
            for (Cidade city2 : Main.cidades) {
                // Verificar se a cidade está dentro do quadrado bruto
                double lat = city2.latitude;
                double lon = city2.longitude;
                if (lat < minLat || lat > maxLat || lon < minLon || lon > maxLon) {
                    continue; // A cidade está fora do quadrado bruto, ignorar
                }

                // Verificar se a cidade é do mesmo país
                if (city2.alfa2.equals(alfa2)) {
                    continue; // Ignorar cidades com o mesmo código de país
                }

                // Verificar a distância entre as cidades usando a fórmula de Haversine
                double dist = calculateHaversineDistance(city1.latitude, city1.longitude, lat, lon);
                if (dist >= distanceMin && dist <= distanceMax) {
                    String cityPair = city1.nome.compareTo(city2.nome) < 0 ?
                            city1.nome + "->" + city2.nome :
                            city2.nome + "->" + city1.nome;
                    cityPairs.add(cityPair);
                }
            }
        }

        if (cityPairs.isEmpty()) {
            return "Nenhum par de cidades encontrado a esta distância.\n";
        }

        List<String> sortedCityPairs = new ArrayList<>(cityPairs);
        Collections.sort(sortedCityPairs);

        StringBuilder result = new StringBuilder();
        for (String pair : sortedCityPairs) {
            result.append(pair).append("\n");
        }

        return result.toString();
    }
    private static double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    public static List<String> getTopCountries2024(int numResults) {
        Map<Integer, String> countryIdToNameMap = new HashMap<>();
        Map<Integer, Integer> countryPopulationMap = new HashMap<>();

        // Mapeia o ID do país para o seu nome
        for (Paises pais : Main.paíslista) {
            countryIdToNameMap.put(pais.id, pais.nome);
        }

        // Itera sobre as populações e acumula a população dos países para o ano de 2024
        for (Populacao pop : Main.populacaos) {
            if (pop.ano == 2024) {
                countryPopulationMap.put(pop.id, countryPopulationMap.getOrDefault(pop.id, 0) + pop.populacaoMasculina + pop.populacaoFeminina);
            }
        }

        // Criar uma lista de países ordenados por população
        List<Map.Entry<Integer, Integer>> sortedCountries = new ArrayList<>(countryPopulationMap.entrySet());
        sortedCountries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Obter os top N resultados
        List<String> topCountries = new ArrayList<>();
        for (int i = 0; i < Math.min(numResults, sortedCountries.size()); i++) {
            Map.Entry<Integer, Integer> entry = sortedCountries.get(i);
            String countryName = countryIdToNameMap.get(entry.getKey());
            topCountries.add(countryName + ": " + entry.getValue());
        }

        return topCountries;
    }
}
