package org.mule.docs.rating.configuration;

import javax.validation.constraints.NotNull;

/**
 * Copyright (C) MuleSoft, Inc - All Rights Reserved
 * Created by Sean Osterberg on 8/19/15.
 */
public class AwsConfiguration {

    @NotNull
    private String accessKeyId;

    @NotNull
    private String secretAccessKey;

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public String getSecretAccessKey() {
        return this.secretAccessKey;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}
