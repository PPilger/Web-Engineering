package tuwien.big.formel0.picasa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a race driver from Picasa
 * @author pl
 *
 */
@Entity(name="Avatar")
public class RaceDriver implements Serializable {

    @Id
    private String name;
    private String url;
    private String wikiUrl;

    public RaceDriver() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }   

}
