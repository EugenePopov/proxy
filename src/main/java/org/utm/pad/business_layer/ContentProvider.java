package org.utm.pad.business_layer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import redis.clients.jedis.Jedis;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.utm.pad.WebServerApp.WAREHOUSE_URI;
//import static org.utm.pad.WebServerApp.requests;
import static org.utm.pad.business_layer.LoadBalancer.setUri;


public class ContentProvider {

    private WebResource webResource;
    private Jedis jedis;

    public ContentProvider(){
        jedis = new Jedis("localhost");


    }

    /**
     * Method return all articles in XML format. If there isn't an entry in cache, data is loaded
     * from warehouse.
     * @return all articles in XML format
     */
    public String getAllXml(){

        String fromCache = getFromCache("allxml");

        if(fromCache != null){

            System.out.println("Returned from cache");
            return fromCache;

        } else {
            //requests.add(1);
            setUri();
            Client client = Client.create();
            webResource = client.resource(WAREHOUSE_URI);
            System.out.println("[INFO]---------------------------------------------\n"+
                                "Sending GET request to " + String.valueOf(WAREHOUSE_URI));

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
                    .get(ClientResponse.class);

            String body = response.getEntity(String.class);

            jedis.setex("allxml", getTimeToLive(response), body);

            if (response.getStatus() == 200) {
                System.out.println("Retrieved from warehouse");
                return body;
            } else {
                return "Warehouse error...";
            }
        }
    }

    /**
     * Method return all articles in JSON format. If there isn't an entry in cache, data is loaded
     * from warehouse.
     * @return all articles in JSON format
     */
    public String getAllJson(){

        String fromCache = getFromCache("alljson");

        if(fromCache != null){

            System.out.println("Returned from cache");
            return fromCache;

        } else {
            //requests.add(1);
            setUri();
            Client client = Client.create();
            webResource = client.resource(WAREHOUSE_URI);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            System.out.println("[INFO]---------------------------------------------\n"+
                    "Sending GET request to " + String.valueOf(WAREHOUSE_URI));
            String body = response.getEntity(String.class);

            jedis.setex("alljson", getTimeToLive(response), body);

            if (response.getStatus() == 200) {
                System.out.println("Retrieved from warehouse");
                return body;
            } else {
                return "Warehouse error...";
            }
        }
    }

    public String getOneXml(int id){

        Client client = Client.create();


        String redisKey = "http://localhost:11218/api/articles" + String.valueOf(id)+ "/xml";

        String fromCache = getFromCache(redisKey);

        if(fromCache != null){

            System.out.println("Returned from cache");
            return fromCache;

        } else {
            //requests.add(1);
            setUri();
            webResource = client
                    .resource(WAREHOUSE_URI + id);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
                    .get(ClientResponse.class);

            System.out.println("[INFO]---------------------------------------------\n"+
                    "Sending GET request to " + String.valueOf(webResource.getURI()));
            String body = response.getEntity(String.class);

            jedis.setex(redisKey, getTimeToLive(response), body);

            if (response.getStatus() == 200) {
                System.out.println("Retrieved from warehouse");
                return body;
            } else {
                return "Warehouse error...";
            }
        }

    }

    public String getOneJson(int id){

        Client client = Client.create();


        String redisKey = "http://localhost:11218/api/articles" + String.valueOf(id)+ "/json";


        String fromCache = getFromCache(redisKey);

        if(fromCache != null){

            System.out.println("Returned from cache");
            return fromCache;

        } else {
            //requests.add(1);
            setUri();
            webResource = client
                    .resource(WAREHOUSE_URI + id);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            System.out.println("[INFO]---------------------------------------------\n"+
                    "Sending GET request to " + String.valueOf(webResource.getURI()));
            String body = response.getEntity(String.class);

            jedis.setex(redisKey, getTimeToLive(response), body);

            if (response.getStatus() == 200) {
                System.out.println("Retrieved from warehouse");
                return body;
            } else {
                return "Warehouse error...";
            }
        }

    }

    public String search(String keyword){
        //requests.add(1);
        setUri();
        Client client = Client.create();

        WebResource webResource = client
                .resource(WAREHOUSE_URI).path("search").queryParam
                        ("keyword", keyword);

        System.out.println("[INFO]---------------------------------------------\n"+
                "Sending GET request to " + String.valueOf(webResource.getURI()));
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        return response.getEntity(String.class);
    }

    private String getFromCache(String digest){
        return jedis.get(digest);
    }

    /**
     * Method takes Cache-Control header from response and reads "max-age" property.
     * If Cache-Control header is not found 0 is returned, otherwise time to live is returned.
     * @param response request received from warehouse
     * @return cache time to live
     */
    private int getTimeToLive(ClientResponse response){

        List<String> cacheControl = response.getHeaders().get("Cache-control");
        if(cacheControl == null){
            return 1;
        } else{
            String cache = cacheControl.get(0);
            return Integer.valueOf(cache.substring(cache.indexOf("=")+1, cache.length()));
        }
    }
}
