package util;

public enum Log {
    //TODO implement
    INSTANCE;

    public void logError(String error) {
        System.out.println("Error " + error);

    }

    public void logInfo(String message) {
        System.out.println(message);

    }


}
