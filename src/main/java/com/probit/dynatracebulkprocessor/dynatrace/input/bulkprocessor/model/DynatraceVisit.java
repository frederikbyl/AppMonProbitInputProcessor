package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class DynatraceVisit {

    private String originalString;
    private String id;
    private String application;
    private List<DynatraceAction> actions = new ArrayList<>();

    public DynatraceVisit(){
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public List<DynatraceAction> getActions() {
        return actions;
    }

    public void setActions(
        List<DynatraceAction> actions) {
        this.actions = actions;
    }
}
