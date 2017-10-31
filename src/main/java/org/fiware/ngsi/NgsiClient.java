package org.fiware.ngsi;

import org.glassfish.jersey.client.ClientResponse;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;


public class NgsiClient {
    private String endPoint;

    public NgsiClient(String endPoint) {
        this.endPoint = endPoint;
    }

    public QueryResult query(QueryData q) {
        return this.query(q,null);
    }

    public QueryResult query(QueryData q, List<String> options) {
        Client c = ClientBuilder.newClient();

        URI uri = URI.create(endPoint);
        WebTarget target = c.target(uri).path("/entities");

        if (q.entityIds.length() > 0) {
            target = target.queryParam("id",q.entityIds);
        }

        if(q.types.length() > 0) {
            target = target.queryParam("type", q.types);
        }

        if (q.queryExpression.length() > 0) {
            target = target.queryParam("q", q.queryExpression);
        }

        if (options != null && options.size() > 0) {
            String optionsList = String.join(",", options);
            target = target.queryParam("options",optionsList);
        }

        QueryResult out = null;

        try {
            Response r = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
            if (r.getStatus() == 200) {
                out = new QueryResult(200, r.readEntity(JsonArray.class));
            }
            else {
                out = new QueryResult(r.getStatus());
            }
        }
        catch(Throwable thr) {
           thr.printStackTrace();
           out = new QueryResult(500);
        }

        return out;
    }
}
