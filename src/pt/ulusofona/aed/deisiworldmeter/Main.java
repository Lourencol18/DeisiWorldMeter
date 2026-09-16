package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static ArrayList<Paises> paíslista = new ArrayList<>();
    static ArrayList<Cidade> cidades = new ArrayList<>();
    static ArrayList<Populacao> populacaos = new ArrayList<>();
    static ArrayList<InputInvalido> Invalido = new ArrayList<>();
    static boolean isNumber(String nome) {
        try {
            Double.parseDouble(nome);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    static boolean pesquisaalfa(String alfa2) {
        for (Paises pais : paíslista) {
            if (pais.alfa2.equals(alfa2)) {
                return true;
            }
        }
        return false;
    }

    static boolean pesquisaid(int id) {
        for (Paises pais : paíslista) {
            if (pais.id == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean parseFiles(File folder) {
        // Limpar as listas antes de começar a ler os arquivos
        paíslista.clear();
        cidades.clear();
        populacaos.clear();
        Invalido.clear();

        File ficheiroPaises = new File(folder, "paises.csv");
        File ficheiroCidades = new File(folder, "cidades.csv");
        File ficheiroPopulacao = new File(folder, "populacao.csv");

        Scanner scanner;

        // Processar Paises
        try {
            scanner = new Scanner(ficheiroPaises);
        } catch (FileNotFoundException e) {
            return false;  // Não conseguiu ler o arquivo
        }

        HashSet<Integer> idsProcessados = new HashSet<>();
        boolean primeiraLinha = true;
        int ok2 = 0;
        int nok2 = 0;
        int primeiranok2 = -1;

        while (scanner.hasNext()) {
            String linha = scanner.nextLine();

            if (primeiraLinha) {
                primeiraLinha = false;
            } else {
                String[] partes = linha.split(",");

                if (partes.length != 4 || partes[0].isEmpty()  || !isNumber(partes[0]) || partes[1].isEmpty() || partes[2].isEmpty() || partes[3].isEmpty()) {
                    nok2++;
                    if (primeiranok2 == -1) {
                        primeiranok2 = ok2 + 2;
                    }
                } else {
                    int idPais = Integer.parseInt(partes[0]);
                    String alfa2 = partes[1];
                    String alfa3 = partes[2];
                    String nome = partes[3];

                    if (idsProcessados.contains(idPais)) {
                        // ID já foi processado anteriormente, ignorar esta linha
                        nok2++;
                        if (primeiranok2 == -1) {
                            primeiranok2 = ok2 + 2;
                        }
                    } else {
                        Paises pais = new Paises(idPais, alfa2, alfa3, nome);
                        paíslista.add(pais);
                        idsProcessados.add(idPais); // Adicionar ID aos IDs processados
                        ok2++;
                    }
                }
            }
        }

        scanner.close();
        InputInvalido invalidoPaises = new InputInvalido("paises.csv", ok2, nok2, primeiranok2);

        // Processar Cidades
        try {
            scanner = new Scanner(ficheiroCidades);
        } catch (FileNotFoundException e) {
            return false;  // Não conseguiu ler o arquivo
        }

        boolean primeiraLinha2 = true;
        int ok1 = 0;
        int nok1 = 0;
        int primeiranok1 = -1;

        while (scanner.hasNext()) {
            String linha = scanner.nextLine();

            if (primeiraLinha2) {
                primeiraLinha2 = false;
            } else {
                String[] partes = linha.split(",");

                if (primeiranok1 == -1 && (partes.length != 6 || !pesquisaalfa(partes[0]) || partes[0].isEmpty() || partes[2].isEmpty() || partes[3].isEmpty() || !isNumber(partes[3]) || partes[4].isEmpty() || !isNumber(partes[4]) || partes[5].isEmpty() || !isNumber(partes[5]))) {
                    primeiranok1 = ok1 + 2;
                    nok1++;
                } else if (partes.length != 6 || partes[0].isEmpty() || !pesquisaalfa(partes[0]) || partes[2].isEmpty() || partes[3].isEmpty() || !isNumber(partes[3]) || partes[4].isEmpty() || !isNumber(partes[4]) || partes[5].isEmpty() || !isNumber(partes[5])) {
                    nok1++;
                } else {
                    String alfa2Cidade = partes[0];
                    String cidade = partes[1];
                    String regiaoDaCidade = partes[2];
                    long populacaoDaCidade = (long) Double.parseDouble(partes[3]);
                    double latitudeDaCidade = Double.parseDouble(partes[4]);
                    double longitudeDaCidade = Double.parseDouble(partes[5]);
                    Cidade cidadeObj = new Cidade(alfa2Cidade, cidade, regiaoDaCidade, populacaoDaCidade, latitudeDaCidade, longitudeDaCidade);
                    cidades.add(cidadeObj);
                    ok1++;
                }
            }
        }
        scanner.close();
        InputInvalido invalidoCidades = new InputInvalido("cidades.csv", ok1, nok1, primeiranok1);

        int linhapaisesnok = -1;
        int okpaises = 0;
        List<Paises> paisesARemover = new ArrayList<>();

        for (Paises pais : paíslista) {
            boolean found = false;
            for (Cidade cidade : cidades) {
                if (cidade.alfa2.equals(pais.alfa2)) {
                    found = true;
                    okpaises++;
                    break;
                }
            }
            if (!found) {
                nok2++;
                ok2--;
                paisesARemover.add(pais); // Adiciona à lista de remoção
                if (linhapaisesnok == -1) {
                    linhapaisesnok = okpaises + 2;
                    if (linhapaisesnok < primeiranok2) {
                        primeiranok2 = linhapaisesnok;
                    }
                }
            }
        }

        // Remover os países da lista original após a iteração
        paíslista.removeAll(paisesARemover);
        invalidoPaises = new InputInvalido("paises.csv", ok2, nok2, primeiranok2);

        // Processar Populacao
        try {
            scanner = new Scanner(ficheiroPopulacao);
        } catch (FileNotFoundException e) {
            return false;  // Não conseguiu ler o arquivo
        }

        boolean primeiraLinha3 = true;
        int ok3 = 0;
        int nok3 = 0;
        int primeiranok3 = -1;

        while (scanner.hasNext()) {
            String linha = scanner.nextLine();

            if (primeiraLinha3) {
                primeiraLinha3 = false;
            } else {
                String[] partes = linha.split(",");

                if (primeiranok3 == -1 && (partes.length != 5 || !pesquisaid(Integer.parseInt(partes[0])) || partes[0].isEmpty() || !isNumber(partes[0]) || partes[1].isEmpty() || !isNumber(partes[1]) || partes[2].isEmpty() || !isNumber(partes[2]) || partes[3].isEmpty() || !isNumber(partes[3]) || partes[4].isEmpty() || !isNumber(partes[4]))) {
                    primeiranok3 = ok3 + 2;
                    nok3++;
                } else if (partes.length != 5 || !pesquisaid(Integer.parseInt(partes[0])) || partes[0].isEmpty() || !isNumber(partes[0]) || partes[1].isEmpty() || !isNumber(partes[1]) || partes[2].isEmpty() || !isNumber(partes[2]) || partes[3].isEmpty() || !isNumber(partes[3]) || partes[4].isEmpty() || !isNumber(partes[4])) {
                    nok3++;
                } else {
                    int idPopulacao = Integer.parseInt(partes[0]);
                    int anoPopulacao = Integer.parseInt(partes[1]);
                    int masculino = Integer.parseInt(partes[2]);
                    int feminino = Integer.parseInt(partes[3]);
                    double densidade = Double.parseDouble(partes[4]);
                    Populacao popular = new Populacao(idPopulacao, anoPopulacao, masculino, feminino, densidade);
                    populacaos.add(popular);
                    ok3++;
                }
            }
        }
        scanner.close();
        InputInvalido invalidoPopulacao = new InputInvalido("populacao.csv", ok3, nok3, primeiranok3);

        // Atualizar contagem de população para países com id > 700
        for (Paises pais : paíslista) {
            if (pais.id > 700) {
                int contagem = 0;
                for (Populacao populacao : populacaos) {
                    if (populacao.id == pais.id) {
                        contagem++;
                    }
                }
                pais.contagemPopulacao = contagem;
            }
        }

        Invalido.add(invalidoPaises);
        Invalido.add(invalidoCidades);
        Invalido.add(invalidoPopulacao);
        return true;
    }

    public static ArrayList getObjects(TipoEntidade tipo) {
        switch (tipo) {
            case PAIS:
                return new ArrayList<>(paíslista);
            case CIDADE:
                return new ArrayList<>(cidades);
            default:
                return new ArrayList<>(Invalido); // Retorna a lista de InputInvalido para INPUT_INVALIDO
        }
    }


    public static Result execute(String command){
        Result resultado = new Result(false, "Comando não reconhecido", null);
        String[] partes = command.split(" ");

        switch (partes[0].toUpperCase()) {
            case "HELP":
                resultado.error = null;
                resultado.result = Commands.help();
                resultado.success = true;
                return resultado;
            case "QUIT":
                resultado.error = null;
                resultado.result = "Goodbye!";
                resultado.success = true;
                return resultado;
            case "COUNT_CITIES":
                int minPopulation = Integer.parseInt(partes[1]);
                int count = Commands.countCities(minPopulation);
                resultado.error = null;
                resultado.result = String.valueOf((count));
                resultado.success = true;
                return resultado;
            case "GET_CITIES_BY_COUNTRY":
                int numResults = Integer.parseInt(partes[1]);
                String countryName = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));
                ArrayList<Cidade> cities = Commands.getCitiesByCountry(numResults, countryName);
                StringBuilder sb = new StringBuilder();
                for (Cidade city : cities) {
                    sb.append(city.nome).append("\n");
                }
                resultado.error = null;
                resultado.result = sb.toString();
                resultado.success = true;
                return resultado;
            case "SUM_POPULATIONS":
                String countryArgs = command.substring(command.indexOf(" ") + 1);
                String[] countryArray = countryArgs.split(",");
                List<String> countries = new ArrayList<>();
                for (String country : countryArray) {
                    countries.add(country.trim());
                }
                // Verificar se os países existem
                for (String country : countries) {
                    boolean found = false;
                    for (Paises pais : Main.paíslista) {
                        if (pais.nome.equalsIgnoreCase(country.trim())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        resultado.result = "Pais invalido: " + country.trim();
                        resultado.success = true;
                        resultado.error = null;
                        return resultado;
                    }
                }

                int totalPopulation = Commands.sumPopulations(countries);
                resultado.error = null;
                resultado.result = String.valueOf(totalPopulation);
                resultado.success = true;
                return resultado;
            case "GET_HISTORY":

                int yearStart = Integer.parseInt(partes[1]);
                int yearEnd = Integer.parseInt(partes[2]);
                String countryName1 = String.join(" ", Arrays.copyOfRange(partes, 3, partes.length));
                String history = Commands.getHistory(yearStart, yearEnd, countryName1);
                if (history == null) {
                    resultado.error = "País inválido ou sem dados disponíveis.";
                    resultado.result = null;
                } else {
                    resultado.error = null;
                    resultado.result = history;
                }
                resultado.success = true;
                return resultado;
            case "GET_MISSING_HISTORY":
                yearStart = Integer.parseInt(partes[1]);
                yearEnd = Integer.parseInt(partes[2]);
                String missingHistory = Commands.getMissingHistory(yearStart, yearEnd);
                resultado.error = null;
                resultado.result = missingHistory;
                resultado.success = true;
                return resultado;
            case "GET_MOST_POPULOUS":
                int numResults1 = Integer.parseInt(partes[1]);
                String mostPopulousCities = Commands.getMostPopulous(numResults1);
                resultado.error = null;
                resultado.result = mostPopulousCities;
                resultado.success = true;
                return resultado;
            case "GET_TOP_CITIES_BY_COUNTRY":

                int numResults3 = Integer.parseInt(partes[1]);
                String countryName2 = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));
                String topCitiesByCountry = Commands.getTopCitiesByCountry(numResults3, countryName2);
                resultado.error = null;
                resultado.result = topCitiesByCountry;
                resultado.success = true;
                return resultado;
            case "GET_DUPLICATE_CITIES":
                int minPopulation2 = Integer.parseInt(partes[1]);
                String duplicateCities = Commands.getDuplicateCities(minPopulation2);
                resultado.error = null;
                resultado.result = duplicateCities;
                resultado.success = true;
                return resultado;
            case "GET_COUNTRIES_GENDER_GAP":
                double minPercentage = Double.parseDouble(partes[1]);
                String genderGap = Commands.getCountriesGenderGap(minPercentage);
                resultado.error = null;
                resultado.result = genderGap;
                resultado.success = true;
                return resultado;
            case "GET_TOP_POPULATION_INCREASE":
                int startYear = Integer.parseInt(partes[1]);
                int endYear = Integer.parseInt(partes[2]);
                String populationIncrease = Commands.getTopPopulationIncrease(startYear, endYear);
                resultado.error = null;
                resultado.result = populationIncrease;
                resultado.success = true;
                return resultado;
            case "INSERT_CITY":
                String alfa2 = partes[1];
                String cityName = partes[2];
                String region = partes[3];
                int population = Integer.parseInt(partes[4]);
                String insertResult = Commands.insertCity(alfa2, cityName, region, population);
                if  (insertResult.equals("Inserido com sucesso")) {
                    resultado.success = true;
                    resultado.error = null;
                    resultado.result = insertResult;
                } else {
                    resultado.success = true;
                    resultado.error = "Pais invalido";
                    resultado.result = "Pais invalido";
                }
                return resultado;
            case "REMOVE_COUNTRY":
                String countryNameToRemove = partes[1];
                String removeResult = Commands.removeCountry(countryNameToRemove);
                if (removeResult.equals("Removido com sucesso")) {
                    resultado.success = true;
                    resultado.error = null;
                    resultado.result = removeResult;
                } else {
                    resultado.success = true;
                    resultado.error = "Pais invalido";
                    resultado.result = "Pais invalido";
                }
                return resultado;
            case "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES":
                int minPopulation3 = Integer.parseInt(partes[1]);
                String duplicateCities1 = Commands.getDuplicateCitiesDifferentCountries(minPopulation3);
                resultado.error = null;
                resultado.result = duplicateCities1;
                resultado.success = true;
                return resultado;
            case "GET_CITIES_AT_DISTANCE":
                double distance = Double.parseDouble(partes[1]);
                StringBuilder countryNameBuilder = new StringBuilder();
                for (int i = 2; i < partes.length; i++) {
                    if (i > 2) {
                        countryNameBuilder.append(" ");
                    }
                    countryNameBuilder.append(partes[i]);
                }
                String countryName3 = countryNameBuilder.toString().trim();
                String citiesAtDistance = Commands.getCitiesAtDistance(distance, countryName3);
                resultado.success = true;
                resultado.error = null;
                resultado.result = citiesAtDistance;
                return resultado;
            case "GET_CITIES_AT_DISTANCE2":
                double distance2 = Double.parseDouble(partes[1]);
                StringBuilder countryNameBuilder2 = new StringBuilder();
                for (int i = 2; i < partes.length; i++) {
                    if (i > 2) {
                        countryNameBuilder2.append(" ");
                    }
                    countryNameBuilder2.append(partes[i]);
                }
                String countryName4 = countryNameBuilder2.toString().trim();
                String citiesAtDistance5 = Commands.getCitiesAtDistance2(distance2, countryName4);
                resultado.success = true;
                resultado.error = null;
                resultado.result = citiesAtDistance5;
                return resultado;
            case "GET_TOPCOUNTRY_2024":
                int numResults5 = Integer.parseInt(partes[1]);
                List<String> topCountries = Commands.getTopCountries2024(numResults5);
                StringBuilder string = new StringBuilder();
                for (String country : topCountries) {
                    string.append(country).append("\n");
                }
                resultado.error = null;
                resultado.result = string.toString().trim();
                resultado.success = true;
                return resultado;
            case "COUNT_REGIONS":
                String countriesList = command.substring(command.indexOf(" ") + 1);
                int totalRegions = Commands.countRegions(countriesList);
                resultado.error = null;
                resultado.result = String.valueOf(totalRegions);
                resultado.success = true;
                return resultado;
            case "GET_DENSITY_BELOW":
                double maxDensity = Double.parseDouble(partes[1]);
                StringBuilder countryNameBuilder1 = new StringBuilder();
                for (int i = 2; i < partes.length; i++) {
                    if (i > 2) {
                        countryNameBuilder1.append(" ");
                    }
                    countryNameBuilder1.append(partes[i]);
                }
                String countryName6 = countryNameBuilder1.toString().trim();
                String densityBelow = Commands.getDensityBelow(maxDensity, countryName6);
                resultado.error = null;
                resultado.result = densityBelow;
                resultado.success = true;
                return resultado;

            default:
                return resultado;
        }
    }

    public static void main(String[] args) {
        File folder = new File("file");// Altere para o diretório onde estão seus arquivos-
        System.out.println("Welcome do DEISI World Meter");
        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(folder);
        long end = System.currentTimeMillis();

        if (!parseOk) {
            System.out.println("Erro na leitura dos ficheiros");
            return;
        }

        Result result = execute("HELP");
        System.out.println(result.result);

        Scanner in = new Scanner(System.in);




        String line;
        do {
            System.out.print("> ");
            line = in.nextLine();

            if (line != null && !line.equals("QUIT")) {
                start = System.currentTimeMillis();
                result = execute(line);
                end = System.currentTimeMillis();

                if (!result.success) {
                    System.out.println("Error: " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println("(took " + (end - start) + " ms)");
                }
            }
        } while (line != null && !line.equals("QUIT"));
    }
}
