package model;

public class Article {

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private String content;

}
