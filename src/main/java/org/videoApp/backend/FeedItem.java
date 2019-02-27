package org.videoApp.backend;


public class FeedItem {
    private int id, userVote, totalVotes;
    private String media, submitter;

    public FeedItem (final int id, final String media, final String submitter, final int userVote, final int totalVotes) {
        this.id = id;
        this.media = media;
        this.submitter = submitter;
        this.userVote = userVote;
        this.totalVotes = totalVotes;
    }

    public int getId() {
        return id;
    }

    public String getMedia() {
        return media;
    }

    public String getSubmitter() {
        return submitter;
    }

    public int getUserVote() {
        return userVote;
    }

    public int getTotalVotes() {
        return totalVotes;
    }
}