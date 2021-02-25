package model;

public class Comment {
    public Comment(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String name;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    private String comment;
}
