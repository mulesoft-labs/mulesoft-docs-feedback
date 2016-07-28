package org.mule.docs.rating;

import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.Test;
import org.mule.docs.rating.configuration.AwsConfiguration;
import org.mule.docs.rating.core.Rating;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Copyright (C) MuleSoft, Inc - All Rights Reserved
 * Created by Sean Osterberg on 8/6/15.
 */
public class StorageWriterTest {


    @Test
    public void writeRatingToStorage_WithValidItem_SuccessfullyPostsToStorage() {
        AwsConfiguration config = getAwsConfigurationFromYamlFile();
        Rating rating = new Rating(
                "123",
                "this page sucks",
                "0.0.0.0",
                "http://foo.com",
                new Date(),
                2);
        StorageWriter writer = new StorageWriter(config);
        writer.writeRatingToStorage(rating, config.getTableName());
        writer.closeClient();
    }


    @Test
    public void initializeTable_ReturnsTable() {
        AwsConfiguration config = getAwsConfigurationFromYamlFile();
        String tableName = config.getTableName();
        StorageWriter writer = new StorageWriter(config);
        Table table = writer.initializeTable(tableName);
        boolean boo = false;
    }

    @SuppressWarnings("unchecked")
    public AwsConfiguration getAwsConfigurationFromYamlFile() {
        InputStream input = null;
        try {
            input = new FileInputStream("configuration.yml");
        } catch (FileNotFoundException e){
            System.out.println("reading yml file failed");
        }
        if(input != null) {
            Yaml yaml = new Yaml();
            LinkedHashMap<String, Map<String, String>> map = (LinkedHashMap) yaml.load(input);
            if(map != null) {
                Map<String, String> configMap = map.get("configuration");
                String accessKeyId = configMap.get("accessKeyId");
                String secretAccessKey = configMap.get("secretAccessKey");
                AwsConfiguration awsConfig = new AwsConfiguration();
                awsConfig.setSecretAccessKey(secretAccessKey);
                awsConfig.setAccessKeyId(accessKeyId);
                return awsConfig;
            }
        }
        return null;
    }
}
