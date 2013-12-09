package db;

import java.util.Properties;

public class DbConfig {


    private String driverClass;


    private String url;
    private String user;
    private String password;

//    public void setUpProperties(String propertiesFileName) throws IOException {
//
//        FileInputStream fis = new FileInputStream(propertiesFileName);
//        Properties newProperties = new Properties();
//        newProperties.loadFromXML(fis);
//        setUpProperties(newProperties);
//
//    }

    public void loadTestProperties() {
        driverClass = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost/fingen1";
        user = "fin97";
        password = "esk1m0";

    }

    public void setUpProperties(Properties properties) {
        driverClass = properties.getProperty("DRIVER_CLASS");
        url = properties.getProperty("DB_URL");
        user = properties.getProperty("DB_USER");
        password = properties.getProperty("DB_PWD");
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }


}
