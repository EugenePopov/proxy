package org.utm.pad.business_layer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

import static org.utm.pad.WebServerApp.*;

public class LoadBalancer extends Thread{

    public static boolean requested = false;

    public static void setUri() {

        Random random = new Random();
        int number;

        //noinspection InfiniteLoopStatement


            //if(!requests.isEmpty()) {

                if (isMyTurn) {
                    if (isAvailable(11219, 1000)) {
                        WAREHOUSE_URI = "http://localhost:11219/api/articles/";
                        NEIGHBOUR_PORT = 11220;
                        NEIGHBOUR_URI = "http://localhost:11220/api/articles/";
                        //requested = false;
                        isMyTurn = false;
                    }


                } else {
                    if (isAvailable(11220, 1000)) {
                        WAREHOUSE_URI = "http://localhost:11220/api/articles/";
                        NEIGHBOUR_PORT = 11219;
                        NEIGHBOUR_URI = "http://localhost:11219/api/articles/";
                        //requested = false;
                        isMyTurn = true;
                    }

                }
                //requests.remove(1);



    }

    private static boolean isAvailable(int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
