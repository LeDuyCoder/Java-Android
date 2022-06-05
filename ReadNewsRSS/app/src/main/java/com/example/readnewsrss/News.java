package com.example.readnewsrss;

public class News {
    private String title, discription, linkimage, linkNew;

    public News(String title, String discription, String linkimage, String linknew) {
        this.title = title;
        this.discription = discription;
        this.linkimage = linkimage;
        this.linkNew = linknew;
    }

    public String getLinkNew() {
        return linkNew;
    }

    public void setLinkNew(String linkNew) {
        this.linkNew = linkNew;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getLinkimage() {
        return linkimage;
    }

    public void setLinkimage(String linkimage) {
        this.linkimage = linkimage;
    }
}
