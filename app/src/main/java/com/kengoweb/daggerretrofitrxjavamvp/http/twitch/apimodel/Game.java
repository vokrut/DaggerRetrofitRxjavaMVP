
package com.kengoweb.daggerretrofitrxjavamvp.http.twitch.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Integer popularity;
    @SerializedName("_id")
    @Expose
    private Integer id;
    @SerializedName("giantbomb_id")
    @Expose
    private Integer giantbombId;
    @SerializedName("box")
    @Expose
    private Box box;
    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("_links")
    @Expose
    private Links_ links;
    @SerializedName("localized_name")
    @Expose
    private String localizedName;
    @SerializedName("locale")
    @Expose
    private String locale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGiantbombId() {
        return giantbombId;
    }

    public void setGiantbombId(Integer giantbombId) {
        this.giantbombId = giantbombId;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public Links_ getLinks() {
        return links;
    }

    public void setLinks(Links_ links) {
        this.links = links;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
