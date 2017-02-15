package org.utm.pad.business_layer;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;

import static org.utm.pad.WebServerApp.WAREHOUSE_URI;

public class Eraser {

    /**
     * If the article with specified id is found in the warehouse, it's deleted.
     * @param id of the article to erase
     * @return true if deleted successful, otherwise return false
     */
    public boolean delete(int id){

        Client client = Client.create();
        WebResource webResource = client
                .resource(WAREHOUSE_URI + id);

        System.out.println("[INFO]---------------------------------------------\n"+
                "Sending DELETE request to " + String.valueOf(webResource.getURI()));

        ClientResponse response = webResource.type(MediaType.TEXT_PLAIN_TYPE)
                .delete(ClientResponse.class);

        return response.getStatus() == 200;
    }
}
