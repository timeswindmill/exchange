package db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionPool {

    INSTANCE;

    //TODO get from config
    private ComboPooledDataSource cpds;
    //private DbConfig config; //= Machine.INSTANCE.getDbConfig();


    public void setup(DbConfig config) {
        //INSTANCE.config = config;
        // don't setup again if already setup
        if (cpds == null) {
            cpds = new ComboPooledDataSource();

            try {
                cpds.setDriverClass(config.getDriverClass());
                //              cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver
//               cpds.setDriverClass( "org.sqlite.JDBC" ); //loads the jdbc driver

            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
            cpds.setJdbcUrl(config.getUrl());
            cpds.setUser(config.getUser());
            cpds.setPassword(config.getPassword());
//            cpds.setJdbcUrl( "jdbc:sqlite:/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/exchange.db" );


            // the settings below are optional -- c3p0 can work with defaults
            cpds.setMinPoolSize(20);
            cpds.setAcquireIncrement(10);
            cpds.setMaxPoolSize(200);

        }
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

}
