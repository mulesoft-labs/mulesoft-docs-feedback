package org.mule.docs.rating;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.Test;
import org.mule.docs.rating.core.Rating;

import java.util.Date;

/**
 * Copyright (C) MuleSoft, Inc - All Rights Reserved
 * Created by Sean Osterberg on 8/6/15.
 */
public class StorageWriterTest {

    @Test
    public void writeRatingToStorage_WithValidItem_SuccessfullyPostsToStorage() {
        Rating rating = new Rating(
                "123",
                "this page sucks",
                "0.0.0.0",
                "http://foo.com",
                new Date(),
                2);
        StorageWriter.writeRatingToStorage(rating);
    }

    @Test
    public void initializeTable_ReturnsTable() {
        Table table = StorageWriter.initializeTable("ratings");
        boolean boo = false;
    }
}
