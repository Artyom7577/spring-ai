package com.polixis.springai.uk;

import com.mongodb.client.*;
import okhttp3.*;
import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;

public class OfficerStreamOkHttp {

    private static final String STREAM_URL = "https://stream.companieshouse.gov.uk/companies";
    private static final String API_KEY = "893a0319-aa8a-44c9-aa1e-ef60908fcdc7";
    private static final Logger log = LoggerFactory.getLogger(OfficerStreamOkHttp.class);

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoCollection<Document> collection = mongoClient
                .getDatabase("companieshousestream")
                .getCollection("companies");

        OkHttpClient client = new OkHttpClient();

        String credential = Base64.getEncoder().encodeToString((API_KEY + ":").getBytes());
        Request request = new Request.Builder()
                .url(STREAM_URL)
                .header("Authorization", "Basic " + credential)
                .header("Accept", "*/*")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed: " + response);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    JSONObject json = new JSONObject(line);
                    String resourceId = json.getString("resource_id");

                    if (collection.find(new Document("resource_id", resourceId)).first() == null) {
                        collection.insertOne(Document.parse(json.toString()));
                        log.info("Inserted: {}", resourceId);
                    } else {
                        log.info("Duplicate skipped: {}", resourceId);
                    }
                } catch (Exception e) {
                    log.warn("Error parsing line: {}, {}", e.getMessage(), line);
                }
            }
        } catch (Exception e) {
            log.error("Stream error: {}", e.getMessage());
        }
    }
}
