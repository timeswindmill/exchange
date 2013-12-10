package db.dataLoader;

import client.Client;
import client.StandardClient;
import db.ConnectionPool;
import instruments.Instrument;
import instruments.StandardInstrument;
import machine.Machine;
import machine.RunConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class StaticDataLoader {


    public static Map<String, Instrument> loadInstruments() throws SQLException {
        if (Machine.INSTANCE.getRunConfig() == RunConfig.UNITTEST) {
            return loadTestInstruments();
        }

        ConnectionPool.INSTANCE.setup(Machine.INSTANCE.getDbConfig());
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        Statement stmt = connection.createStatement();

        String sql = "SELECT *  FROM InstrumentStatic";
        ResultSet rs = stmt.executeQuery(sql);
        Map<String, Instrument> instrumentMap = new HashMap<>();

        while (rs.next()) {
            //Retrieve by column name
            String name = rs.getString("Name");
            String symbol = rs.getString("Symbol");
            double refPrice = rs.getDouble("RefPrice");
            int multiplier = rs.getInt("Multiplier");
            double tickSize = rs.getDouble("TickSize");
            //TODO add underlying
            Instrument instrument = new StandardInstrument(symbol, name, null, refPrice, multiplier, tickSize);
            instrumentMap.put(symbol, instrument);
        }
        rs.close();

        return instrumentMap;

    }

    public static Map<String, Client> loadClients() throws SQLException {
        if (Machine.INSTANCE.getRunConfig() == RunConfig.UNITTEST) {
            return loadTestClients();
        }
        ConnectionPool.INSTANCE.setup(Machine.INSTANCE.getDbConfig());
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        Statement stmt = connection.createStatement();

        String sql = "SELECT *  FROM Clients";
        ResultSet rs = stmt.executeQuery(sql);
        Map<String, Client> clientMap = new HashMap<>();

        while (rs.next()) {
            //Retrieve by column name
            int ID = rs.getInt("ID");
            String name = rs.getString("ClientName");
            Client client = new StandardClient(ID, name);
            clientMap.put(name, client);
        }
        rs.close();

        return clientMap;

    }

    private static Map<String, Instrument> loadTestInstruments() {
        Map<String, Instrument> instrumentMap = new HashMap<>();

        instrumentMap.put("AAX", new StandardInstrument("AAX", "AAX Equity", null, 53.7, 1, 0.1));
        instrumentMap.put("BTX", new StandardInstrument("BTX", "BTX Equity", null, 98.7, 1, 0.1));
        instrumentMap.put("CDX", new StandardInstrument("CDX", "CDX Equity", null, 127.3, 1, 0.1));
        instrumentMap.put("BTCUSD", new StandardInstrument("BTCUSD", "BTC USD", null, 700, 1, 0.0001));

        return instrumentMap;
    }

    private static Map<String, Client> loadTestClients() {
        Map<String, Client> clientMap = new HashMap<>();
        clientMap.put("CLIENT1", new StandardClient(1, "Client 1"));
        clientMap.put("CLIENT2", new StandardClient(2, "Client 2"));
        clientMap.put("CLIENT3", new StandardClient(2, "Client 3"));
        clientMap.put("EX1", new StandardClient(2, "EX1"));

        return clientMap;

    }


}
