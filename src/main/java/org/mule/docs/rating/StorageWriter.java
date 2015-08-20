package org.mule.docs.rating;

import java.util.Arrays;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import org.mule.docs.rating.configuration.AwsConfiguration;
import org.mule.docs.rating.core.Rating;
import org.mule.docs.rating.util.Utilities;

public class StorageWriter {
    private DynamoDB client;
    private AwsConfiguration config;

    public StorageWriter(AwsConfiguration config) {
        this.config = config;
        this.client = getClient();
    }

    /**
     * Writes a rating object to storage.
     * @param rating The rating object to store.
     */
    public void writeRatingToStorage(Rating rating) {
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
    public Table initializeTable(String tableName) {
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

    private Table getTable(String tableName) {
        return initializeTable(tableName);
    }

    private DynamoDB getClient() {
        if(this.client == null) {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(this.config.getAccessKeyId(), this.config.getSecretAccessKey());
            AmazonDynamoDBClient awsClient = new AmazonDynamoDBClient(awsCreds);
            awsClient.configureRegion(Regions.US_WEST_2);
            return new DynamoDB(awsClient);
        } else {
            return this.client;
        }
    }

    public void closeClient() {
        this.client.shutdown();
    }
}