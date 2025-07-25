
package com.polixis.springai.uk;

import com.mongodb.client.*;
import org.bson.Document;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class OfficerStreamHttpUrlConnection {

    private static final String STREAM_URL = "https://stream.companieshouse.gov.uk/officers";
    private static final String API_KEY = "893a0319-aa8a-44c9-aa1e-ef60908fcdc7";

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoCollection<Document> collection = mongoClient
                .getDatabase("companieshousestream")
                .getCollection("officers");

        try {
            URL url = new URL(STREAM_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Set Basic Auth Header
            String encodedAuth = Base64.getEncoder().encodeToString((API_KEY + ":").getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
            conn.setRequestProperty("Accept", "*/*");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                try {
                    JSONObject json = new JSONObject(line);
                    String resourceId = json.getString("resource_id");

                    if (collection.find(new Document("resource_id", resourceId)).first() == null) {
                        collection.insertOne(Document.parse(json.toString()));
                        System.out.println("Inserted: " + resourceId);
                    } else {
                        System.out.println("Duplicate skipped: " + resourceId);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to parse/insert: " + e.getMessage() + " - Line: " + line);
                }
            }

            in.close();
            conn.disconnect();

        } catch (Exception e) {
            System.err.println("Stream error: " + e.getMessage());
        }
    }
}
