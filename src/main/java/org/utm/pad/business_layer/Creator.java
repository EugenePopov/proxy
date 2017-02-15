package org.utm.pad.business_layer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.utm.pad.model_layer.Article;

import javax.ws.rs.core.MediaType;

import static org.utm.pad.WebServerApp.WAREHOUSE_URI;

public class Creator {

    /**
     * Method receives the message from client and passes it to the warehouse.
     * @param body string contained in request's body. It must be a JSON element.
     * @return true if created successful, otherwise return false
     * @throws Exception caused by troubles at JSON validation
     */
    public boolean create(String body) throws Exception {

        JsonValidator validator = new JsonValidator();

        if(validator.isValid(body)){
            Client client = Client.create();
            WebResource webResource = client
                    .resource(WAREHOUSE_URI);

            System.out.println("[INFO]---------------------------------------------\n"+
                    "Sending POST request to " + String.valueOf(webResource.getURI()));

            ClientResponse response = webResource.type(MediaType.TEXT_PLAIN_TYPE)
                    .post(ClientResponse.class, body);

            if(response.getStatus() == 201){
                return true;
            } else{
                System.out.println("Error " + response.getStatus() + "  at resource creation...");
                return false;
            }
        } else{
            System.out.println("Not a valid JSON!");
            return false;
        }
    }

}
