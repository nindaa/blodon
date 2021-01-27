package com.dicoding.androcoding.blodonapp.Model.event;

import java.util.List;

public class Event {
    private int code;
    private String description;
    private List<EventData> results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EventData> getResults() {
        return results;
    }

    public void setResults(List<EventData> results) {
        this.results = results;
    }
}
