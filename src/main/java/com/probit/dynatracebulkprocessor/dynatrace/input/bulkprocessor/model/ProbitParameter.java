package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class ProbitParameter {

    private String name;
    private Object value;

    public ProbitParameter(String name, String value){
        this.name = name;
        this.value =value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
