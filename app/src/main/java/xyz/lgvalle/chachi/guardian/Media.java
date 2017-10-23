package xyz.lgvalle.chachi.guardian;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by lgvalle on 21/10/2017.
 */
@IgnoreExtraProperties
public class Media {
    public String credit;
    public String url;

    public Media() {
    }

    public String getCredit() {
        return credit;
    }

    public String getUrl() {
        return url;
    }
}
