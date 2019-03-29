//package edu.winona.klein;

/**
 *
 * Author : Brian Klein Date : Description :
 */
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class MongoTest {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient();
        
        //code from the MongoDB website to print queries
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        //MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        //MongoClient mongoClient = new MongoClient(connectionString);
        
        //designate the database and collection to access
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("users");

        //print the whole users collection
        System.out.println("\nThe initial collection:");
        collection.find().projection(new Document("firstName", 1)
                     .append("lastName", 1)
                     .append("_id", 0)).forEach(printBlock);
        
        //create the document
        Document doc = new Document("firstName", "James")
                .append("lastName", "Bond");
        
        //print the document
        System.out.println("\nThe document to be added:\n" + doc);

        //add the document to the collection
        collection.insertOne(doc);
        System.out.println("\nDocument added successfully!");
        
        //print the whole users collection again to show the new addition
        System.out.println("\nThe users collection:");
        collection.find().projection(new Document("firstName", 1)
                     .append("lastName", 1)
                     .append("_id", 0)).forEach(printBlock);

        //update the first and last names of James Bond to Sean Connery
        collection.updateOne(
                eq("firstName", "James"),
                combine(set("firstName", "Sean"), set("lastName", "Connery")));
        System.out.println("\nDocument updated successfully!");

        //print the users collection to show James Bond has been updated to be Sean Connery
        System.out.println("\nJames Bond is now Sean Connery:");
        collection.find().projection(new Document("firstName", 1)
                     .append("lastName", 1)
                     .append("_id", 0)).forEach(printBlock);
        
        //delete Sean Connery from the collection
        collection.deleteOne(eq("lastName", "Connery"));
        System.out.println("\nDocument deleted successfully!");
        
        //print the whole users collection again to show Sean Connery has been deleted
        System.out.println("\nSean Connery has been deleted, the final users collection:");
        collection.find().projection(new Document("firstName", 1)
                     .append("lastName", 1)
                     .append("_id", 0)).forEach(printBlock);
    }

}
