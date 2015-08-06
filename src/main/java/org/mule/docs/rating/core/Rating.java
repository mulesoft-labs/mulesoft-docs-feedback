package org.mule.docs.rating.core;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * Copyright (C) MuleSoft, Inc - All Rights Reserved
 * Created by Sean Osterberg on 8/5/15.
 */
public class Rating {
    private String id;
    private String comment;
    private String ipAddress;
    private String url;
    private Date date;
    private int rating;

    public Rating() {

    }

    public Rating(String id, String comment, String ipAddress, String url, Date date, int rating) {
        this.id = id;
        this.comment = comment;
        this.ipAddress = ipAddress;
        this.url = url;
        this.date = date;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUrl() {
        return url;
    }

    public Date getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }
}
