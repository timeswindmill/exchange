package util.test;

import jocket.impl.JocketReader;
import jocket.net.JocketSocket;
import jocket.net.ServerJocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JocketTester {

    public static void main(String... args) {
        JocketTester jt = new JocketTester();
        jt.run();
    }

    private void run() {


        JServer js = new JServer();
        Thread t = new Thread(js);
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // client
        JocketSocket sock = null;
        try {
            sock = new JocketSocket(14242);
            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();
            String msg = "jocket test";
            byte[] data = msg.getBytes();
            out.write(data);
            out.flush();
            System.out.println("Data Written");

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    private class JServer implements Runnable {


        @Override
        public void run() {

            ServerJocket srv = null;
            try {
                srv = new ServerJocket(14242);
                JocketSocket servSock = srv.accept();
                JocketReader jr = servSock.getReader();
                byte[] data = new byte[100];
                System.out.println("waiting");
                int result = jr.read(data);
                System.out.println("result is " + result);
                String inputStr = new String(data);
                System.out.println(inputStr);


            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


        }
    }


}
