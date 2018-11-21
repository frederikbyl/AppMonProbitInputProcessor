package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model;

import java.util.List;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class ProbitAction {

    private String name;
    private List<ProbitParameter> parameters;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProbitParameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<ProbitParameter> parameters) {
        this.parameters = parameters;
    }



}
