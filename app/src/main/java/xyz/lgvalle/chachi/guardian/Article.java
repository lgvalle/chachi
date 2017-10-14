package xyz.lgvalle.chachi.guardian;


import java.util.List;

public class Article {

    String creator;
    String date;
    String description;
    double score;
    String title;
    List<Media> media;

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

    public String getThumbnail() {
        if (media != null && media.size() > 0) {
            return media.get(0).getUrl();
        }
        return null;
    }

    public String getImage() {
        if (media != null && media.size() > 0) {
            return media.get(media.size() - 1).getUrl();
        }
        return null;
    }


    public class Media {
        String credit;
        String url;

        public String getCredit() {
            return credit;
        }

        public String getUrl() {
            return url;
        }
    }
}
