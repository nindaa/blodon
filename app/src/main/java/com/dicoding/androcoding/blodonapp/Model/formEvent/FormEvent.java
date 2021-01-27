package com.dicoding.androcoding.blodonapp.Model.formEvent;

import com.dicoding.androcoding.blodonapp.Model.event.EventData;

import java.util.List;

public class FormEvent {
    private int code;
    private String description;
    private List<FormEventData> results;

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

    public List<FormEventData> getResults() {
        return results;
    }

    public void setResults(List<FormEventData> results) {
        this.results = results;
    }
}
