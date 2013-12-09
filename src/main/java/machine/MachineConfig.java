package machine;

import db.DbConfig;
import exchange.Exchange;
import exchange.ExchangeConfig;
import exchange.StandardExchange;
import message.Address;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MachineConfig {

    private final RunConfig runConfig;
    private final DbConfig dbConfig;
    private Properties properties;

    //TODO log file setup
    private MachineConfig(RunConfig runConfig) {
        this.runConfig = runConfig;
        dbConfig = new DbConfig();

    }


    private void setUpProperties(String propertiesFileName) throws IOException {

        FileInputStream fis = new FileInputStream(propertiesFileName);

        properties = new Properties();
        properties.loadFromXML(fis);
        setUpProperties(properties);

    }

    private void setUpProperties(Properties properties) {

        String exchangesString = properties.getProperty("Exchanges");
        String machineMapString = properties.getProperty("MachineMaps");


        properties.setProperty("Exchanges", exchangesString);
        properties.setProperty("MachineMaps", machineMapString);


    }

    private void setUpUnitTestProperties() {
        properties = new Properties();
        properties.setProperty("Exchanges", "x1");
        properties.setProperty("MachineMaps", "m1,127.0.0.1");

    }

    private ExchangeConfig[] createExchangeConfigsFromString(String configString) {
        return null;
    }

    private Map<String, Address> createMachineMapFromString(String machineMapString) {
        return null;
    }


    public static MachineConfig getMachineConfig(RunConfig runConfig) throws IOException {
        String propertiesFileName = null;
        MachineConfig newConfig = null;

        switch (runConfig) {

            case UNITTEST:
                newConfig = new MachineConfig(runConfig);
                newConfig.setUpUnitTestProperties();
                newConfig.dbConfig.loadTestProperties();
                break;
            case TEST:
                newConfig = new MachineConfig(runConfig);
                propertiesFileName = "./TEST.config";
                newConfig.setUpProperties(propertiesFileName);
                newConfig.dbConfig.setUpProperties(newConfig.properties);
                break;
            case PROD:
                newConfig = new MachineConfig(runConfig);
                propertiesFileName = "./PROD.config";
                newConfig.setUpProperties(propertiesFileName);
                newConfig.dbConfig.setUpProperties(newConfig.properties);
                break;
        }


        return newConfig;
    }

    public Exchange[] createExchanges() {
        //ExchangeConfig[] exchangeConfigs=null;
        Exchange[] exchanges = null;
        switch (runConfig) {

            case UNITTEST:
                ExchangeConfig[] exchangeConfigs = ExchangeConfig.createTestExchangeConfigs();
                exchanges = new Exchange[exchangeConfigs.length];
                for (int ii = 0; ii < exchangeConfigs.length; ii++) {
                    exchanges[ii] = StandardExchange.createMockExchange(exchangeConfigs[ii]);
                }
                break;
            case TEST:
                //TODO

                break;
            case PROD:
                //TODO
//                for(int ii=0;ii<exchangeConfigs.length;ii++){
//                    exchanges[ii] = StandardExchange.createExchange(exchangeConfigs[ii]);
//                }

                break;
        }


        return exchanges;
    }

    public Map<String, Address> createMachineMap() {
        Map<String, Address> machineMap = new HashMap<>();
        //TODO
        switch (runConfig) {

            case UNITTEST:
                break;
            case TEST:
                break;
            case PROD:
                break;
        }


        return machineMap;

    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

}
