package kanu_lp.com.gajjarfirestoreexample;

/**
 * Created by Kanu on 10/5/2017.
 */

public class FavModel {

   private String user;
    private String favsinger;

    public FavModel() {
    }

    public FavModel(String user, String favsinger) {
        this.user = user;
        this.favsinger = favsinger;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFavsinger() {
        return favsinger;
    }

    public void setFavsinger(String favsinger) {
        this.favsinger = favsinger;
    }
}
