package org.mule.docs.rating;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mule.docs.rating.core.Rating;
import org.mule.docs.rating.util.Utilities;

import javax.swing.plaf.synth.Region;

public class StorageWriter {

    /**
     * Writes a rating object to storage.
     * @param rating The rating object to store.
     */
    public static void writeRatingToStorage(Rating rating) {
        Table table = getTable("ratings");

        Item item = new Item()
                .withPrimaryKey("id", rating.getId(), "date", Utilities.convertDateToString(rating.getDate()))
                .withString("date", Utilities.convertDateToString(rating.getDate()))
                .withString("url", rating.getUrl())
                .withString("ipAddress", rating.getIpAddress())
                .withString("comment", rating.getComment())
                .withInt("rating", rating.getRating());
        table.putItem(item);
    }

    /**
     * Initializes the rating table with a primary key hash of the rating's id and date.
     * @param tableName The name of the table to create.
     */
    public static Table initializeTable(String tableName) {
        DynamoDB db = getClient();
        try {
            return db.createTable(tableName,
                    Arrays.asList(
                            new KeySchemaElement("id", KeyType.HASH),
                            new KeySchemaElement("date", KeyType.RANGE)),
                    Arrays.asList(
                            new AttributeDefinition("id", ScalarAttributeType.S),
                            new AttributeDefinition("date", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
        } catch(ResourceInUseException e) {
            return db.getTable(tableName);
        }
    }

    private static Table getTable(String tableName) {
        return initializeTable(tableName);
    }

    private static DynamoDB getClient() {
        AmazonDynamoDBClient client = com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2).createClient(
                AmazonDynamoDBClient.class, new DefaultAWSCredentialsProviderChain(), null);

        return new DynamoDB(client);
    }
}