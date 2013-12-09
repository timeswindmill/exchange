package exchange;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ExchangeConfig {
    //TODO config to file

    private final String exchangeName;
    private final String inputFileName;
    private final String priceReportDirectory;
    private final String[] books;
    private boolean useJournal;

    private ExchangeConfig(String exchangeName, String inputFileName, String priceReportDirectory, String[] books, boolean useJournal) {
        this.exchangeName = exchangeName;
        this.inputFileName = inputFileName;
        this.priceReportDirectory = priceReportDirectory;
        this.books = books;
        this.useJournal = useJournal;
    }


    public String getInputFileName() {
        return inputFileName;

        // return "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/datafile";
    }


    public String getPriceReportDirectory() {
        return priceReportDirectory;
        //return "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/";
    }

    public String getExchangeName() {
        return exchangeName;
        // return "x1";
    }


    public String[] getBooks() {
        //    String [] books = {"AAX","BTX","CDX"};
        return books;
    }


    public boolean getUseJournal() {
        return useJournal;
    }

    public static ExchangeConfig[] createExchangeConfigsFromDb(Connection conn) {
        //TODO
        ExchangeConfig[] configs = new ExchangeConfig[1];
        return configs;

    }

    public static List<ExchangeConfig> createExchangeConfigsFromFile(String fileName) throws IOException {
        List<ExchangeConfig> configList = new ArrayList<ExchangeConfig>();
        Path path = FileSystems.getDefault().getPath(fileName);

        List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
        for (String line : lines) {
            ExchangeConfig config = createExchangeConfigsFromString(line);
            if (config != null) {
                configList.add(config);
            }
        }


        return configList;
    }

    public static ExchangeConfig createExchangeConfigsFromString(String configString) {
        // format is exchangename, inputfilename, pricereport dir, books

        String[] tokens = configString.split(",");
        if (tokens.length < 5) {
            return null;
        }
        String exchangeName = tokens[0];
        String inputFileName = tokens[1];
        String priceReportDir = tokens[2];

        String[] books = new String[tokens.length - 3];
        System.arraycopy(tokens, 3, books, 0, tokens.length - 3);

        ExchangeConfig config = new ExchangeConfig(exchangeName, inputFileName, priceReportDir, books, false);
        return config;

    }


    public static ExchangeConfig[] createTestExchangeConfigs() {

        ExchangeConfig[] configs = new ExchangeConfig[1];

        String inputFileName = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/datafile";
        String priceReportFileDirectory = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/datafile";
        String[] books = {"AAX", "BTX", "CDX"};

        configs[0] = new ExchangeConfig("X1", inputFileName, priceReportFileDirectory, books, false);

        return configs;

    }
}