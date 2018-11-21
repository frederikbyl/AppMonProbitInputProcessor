package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model;

public enum ActionTypes {
    CLICK("click", "click on"),
    TOUCH("ActionEvent", "Touch on"),
    LOGIN("login", "login"),
    KEYPRESS("keypress", "keypress"),
    SCROLL("scroll", "scroll on"),
    LOAD("_load_", "Loading of");

    private String name;
    private String type;

    private ActionTypes(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return this.type;
    }
}
