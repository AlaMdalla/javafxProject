package com.example.jobflow.entities;

public class post_reactions {
    int id;

    public post_reactions(int likes, int dislike) {
        this.likes = likes;
        this.dislike = dislike;
    }

    int likes;
    int dislike;

    public post_reactions(int id, int likes, int dislike) {
        this.id = id;
        this.likes = likes;
        this.dislike = dislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
}
