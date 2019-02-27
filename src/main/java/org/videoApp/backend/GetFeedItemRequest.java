package org.videoApp.backend;


public class GetFeedItemRequest {
    private String latitude, longitude, getPostsFrom;

    public GetFeedItemRequest(final String latitude, final String longitude, final String getPostsFrom) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.getPostsFrom = getPostsFrom;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getGetPostsFrom() {
        return getPostsFrom;
    }
}