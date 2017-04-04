package com.example.nrbafna.mynewsapp;

/**
 * Created by N.R. BAFNA on 28/03/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Source {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sources")
    @Expose
    private List<Source_> sources = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Source() {
    }

    /**
     *
     * @param status
     * @param sources
     */
    public Source(String status, List<Source_> sources) {
        super();
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source_> getSources() {
        return sources;
    }

    public void setSources(List<Source_> sources) {
        this.sources = sources;
    }

}