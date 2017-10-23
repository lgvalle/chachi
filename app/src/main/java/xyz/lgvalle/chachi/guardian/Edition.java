package xyz.lgvalle.chachi.guardian;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Edition {

    public String date;
    public List<Article> items;

    public List<Article> getItems() {
        return items;
    }
}
