
package com.kengoweb.daggerretrofitrxjavamvp.http.twitch.apimodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Twitch {

    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("top")
    @Expose
    private List<Top> top = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }

}
