package xyz.lgvalle.chachi.guardian;


public class Article {

    String creator;
    String date;
    String description;
    double score;
    String title;

    public String getCreator() {
        return creator;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    // TODO Id is the title ATM
    public String getId() {
        return title;
    }
}
