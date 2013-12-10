package machine;

import client.Client;
import db.DbConfig;
import db.dataLoader.StaticDataLoader;
import exchange.Exchange;
import instruments.Instrument;
import machine.IO.MachineSocket;
import machine.IO.in.MachineInput;
import message.Address;
import message.GeneralMessage;
import util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Machine {

    INSTANCE;

    private Exchange[] exchanges;
    private MachineSocket machineSocket;
    private String machineAddress;
    private MachineInput machineInput;
    private MachineConfig config;
    private Map<String, Address> machineMap;
    private Map<String, Instrument> instruments;
    private Map<Long, Client> clients;
    private RunConfig runConfig = RunConfig.UNITTEST;
    public boolean initialised = false;

    private Machine() {

    }

    public static void setupMachine(RunConfig runConfig) {
        if (INSTANCE.initialised) {
            return;
        }

        INSTANCE.runConfig = runConfig;

        try {
            INSTANCE.config = MachineConfig.getMachineConfig(runConfig);
        } catch (IOException e) {
            Log.INSTANCE.logError(e.toString());
            return;
        }


        INSTANCE.instruments = new HashMap<>();
        INSTANCE.clients = new HashMap<>();
        try {
            loadStaticData();
        } catch (SQLException e) {
            Log.INSTANCE.logError(e.toString());
            return;
        }
        INSTANCE.exchanges = INSTANCE.config.createExchanges();
        INSTANCE.machineSocket = new MachineSocket();
        INSTANCE.machineAddress = null;
        INSTANCE.machineInput = new MachineInput(Arrays.asList(INSTANCE.exchanges));
        INSTANCE.machineMap = INSTANCE.config.createMachineMap();
        INSTANCE.initialised = true;


    }

    private static void loadStaticData() throws SQLException {

        INSTANCE.instruments.putAll(StaticDataLoader.loadInstruments());
        INSTANCE.clients.putAll(StaticDataLoader.loadClients());

    }

    private static void startMachine() {
        INSTANCE.startAllExchanges();
        INSTANCE.startInputs();
        Log.INSTANCE.logInfo("Machine Started");
    }


    public Map<Long, Client> getClientMap() {
        return clients;
    }

    public Map<String, Instrument> getInstrumentMap() {
        return instruments;
    }


    public boolean sendMessage(GeneralMessage message) {
        return false;
    }

    public void receiveMessage(GeneralMessage message) {
        machineInput.acceptMessage(message);
    }


    public void startAllExchanges() {
        for (Exchange exchange : exchanges) {
            exchange.startRouterAndQueues();
            exchange.startAgents();
        }

    }

    // must be done after exchanges are started
    public void startInputs() {
        machineInput.startConsumers();
    }
//    public void startExchanges(String exchangeName) {
//        for(Exchange exchange : exchanges){
//            if(exchange.getExchangeName().equalsIgnoreCase(exchangeName)){
//                exchange.startRouterAndQueues();
//                exchange.startAgents();
//            }
//        }
//
//    }

    public RunConfig getRunConfig() {
        return runConfig;
    }

    public DbConfig getDbConfig() {
        return config.getDbConfig();
    }


}
