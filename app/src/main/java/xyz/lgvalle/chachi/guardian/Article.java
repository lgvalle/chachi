package xyz.lgvalle.chachi.guardian;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Article {

    public String creator;
    public String date;
    public String description;
    public double score;
    public String title;
    public List<Media> media;

    public Article() {
    }

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


}
