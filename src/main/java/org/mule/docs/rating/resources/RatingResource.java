package org.mule.docs.rating.resources;

import org.mule.docs.rating.StorageWriter;
import org.mule.docs.rating.core.Rating;
import org.mule.docs.rating.core.RawRating;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;


@Path("/rate")
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {

    public RatingResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveRating(RawRating rawRating, @Context HttpServletRequest request) {
        Rating rating = new Rating(
                rawRating.getId(),
                rawRating.getComment(),
                getIpFromAws(request),
                rawRating.getUrl(),
                new Date(),
                rawRating.getRating()
        );

        StorageWriter.writeRatingToStorage(rating);
        return "Successfully created rating.";
    }

    private String getIpFromAws(HttpServletRequest request) {
        String ip = "0.0.0.0";
        try {
            ip = request.getHeader("X-Forwarded-For").split(",")[0];
        } catch (Exception ignored){}
        return ip;
    }
}