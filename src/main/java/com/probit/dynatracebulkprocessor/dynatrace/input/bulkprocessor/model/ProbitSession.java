package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class ProbitSession implements Serializable {

    private String type;
    private List<ProbitAction> actions;

    public ProbitSession() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProbitAction> getActions() {
        return this.actions;
    }

    public void setActions(List<ProbitAction> actions) {
        this.actions = actions;
    }
}