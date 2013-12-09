package util.test;

import jocket.impl.JocketReader;
import jocket.impl.JocketWriter;
import jocket.net.JocketSocket;
import jocket.net.ServerJocket;

import java.io.IOException;

public class Jocket {


    public static void main(String... args) {
        Jocket jocket = new Jocket();
        jocket.runTest();


    }

    public void runTest() {
        try {
            JocketSocket srvSocket = setUpServer();
            JocketWriter jw = srvSocket.getWriter();

            JocketSocket clSocket = setUpClient();
            JocketReader jr = clSocket.getReader();

            String testmsg = new String("testmsg");
            byte[] data = testmsg.getBytes();
            jw.write(data, 0, data.length);

            byte[] inData = new byte[200];
            jr.read(inData);
            System.out.println(inData);


        } catch (IOException e) {
            System.out.println("Error " + e);
        }


    }

    public JocketSocket setUpServer() throws IOException {
        // server
        ServerJocket srv = new ServerJocket(4242);
        JocketSocket ssock = srv.accept();
        return ssock;
    }

    public JocketSocket setUpClient() throws IOException {
        // client
        JocketSocket csock = new JocketSocket(4242);
        // InputStream in = csock.getInputStream();
        // OutputStream out = csock.getOutputStream();
        return csock;


    }


}
