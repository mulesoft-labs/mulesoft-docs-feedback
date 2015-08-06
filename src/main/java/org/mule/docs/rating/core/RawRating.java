package org.mule.docs.rating.core;

/**
 * Copyright (C) MuleSoft, Inc - All Rights Reserved
 * Created by Sean Osterberg on 8/5/15.
 */
public class RawRating {
    private String id;
    private String comment;
    private String url;
    private int rating;

    public RawRating() {
    }

    public RawRating(String id, String comment, String url, int rating) {
        this.id = id;
        this.comment = comment;
        this.url = url;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getUrl() {
        return url;
    }

    public int getRating() {
        return rating;
    }
}
