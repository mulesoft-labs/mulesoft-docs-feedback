package org.mule.docs.rating;

import org.junit.Test;

/**
 * Copyright (C) MuleSoft, Inc - All Rights Reserved
 * Created by Sean Osterberg on 8/19/15.
 */
public class RatingApplicationTest {

    @Test
    public void ratingApplicationWorksAsExpected() throws Exception {
        RatingApplication.main(new String[] { "server",  });
    }
}
