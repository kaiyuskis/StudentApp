package com.example.studentapp.ui.post;

public class Post {
    private int id;
    private String text;
    private String imageUri;
    private String timestamp;
    private int likeCount;

    public Post(String text, String imageUri, String timestamp, int likeCount) {
        this.text = text;
        this.imageUri = imageUri;
        this.timestamp = timestamp;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
