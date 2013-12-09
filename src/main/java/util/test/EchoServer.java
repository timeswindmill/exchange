package util.test;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {

        String hostName = "127.0.0.1";
        int portNumber = 19231;


        Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);

        out.println("test message from echo");
    }


}