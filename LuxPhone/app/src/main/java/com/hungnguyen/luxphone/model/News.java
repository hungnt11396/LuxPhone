package com.hungnguyen.luxphone.model;

public class News {
    private String title;
    private String content;
    private String headerimage;
    public News() {
    }

    public News(String title, String content, String headerimage) {
        this.title = title;
        this.content = content;
        this.headerimage = headerimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeaderImage() {
        return headerimage;
    }

    public void setHeaderImage(String headerimage) {
        this.headerimage = headerimage;
    }
}
