package org.utm.pad.business_layer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;

import static org.utm.pad.WebServerApp.WAREHOUSE_URI;

public class Updater {

    /**
     * If the article with specified id is found in the warehouse, its content is updated.
     * @param id of the row in the database
     * @param content of the request which is new value for "content" column
     * @return true if updated successful, otherwise return false
     */
    public boolean update(int id, String content){

        Client client = Client.create();
        WebResource webResource = client
                .resource(WAREHOUSE_URI + id);

        System.out.println("[INFO]---------------------------------------------\n"+
                "Sending PUT request to " + String.valueOf(webResource.getURI()));
        ClientResponse response = webResource.type(MediaType.TEXT_PLAIN_TYPE)
                .put(ClientResponse.class, content);

        return response.getStatus() == 200;

    }
}
