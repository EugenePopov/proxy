package org.utm.pad;

import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import org.utm.pad.business_layer.LoadBalancer;
import org.utm.pad.model_layer.Article;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.System.*;

public class WebServerApp
{
    private static final String BASE_URI = "http://localhost:11218/api";
    public static CopyOnWriteArrayList<Article> warehouse = new CopyOnWriteArrayList<>();

    public static  String WAREHOUSE_URI = "http://localhost:11219/api/articles/";
    public static String NEIGHBOUR_URI = "http://localhost:11219/api";
    public static boolean isMyTurn = true;

    public static int NEIGHBOUR_PORT = 11219;
    public static ConcurrentLinkedQueue<Integer> requests = new ConcurrentLinkedQueue<>();

    public static void main( String[] args ) throws Exception {
        try {
            HttpServer server = HttpServerFactory.create(BASE_URI);

            new LoadBalancer().start();
            server.start();

            out.println("Press Enter to stop the server. ");
            //noinspection ResultOfMethodCallIgnored
            in.read();
            server.stop(0);
            exit(0);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
