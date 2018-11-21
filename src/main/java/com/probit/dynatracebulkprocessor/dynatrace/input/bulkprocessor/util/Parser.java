package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model.DynatraceAction;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model.DynatraceVisit;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class Parser {

    public static DynatraceVisit parseVisit(String entry) {
        DynatraceVisit visit = new DynatraceVisit();
        visit.setOriginalString(entry);
        JsonElement element = new JsonParser().parse(entry);
        if (element.getAsJsonObject().get("data") ==null){return null;}

        try {
            visit.setId(element.getAsJsonObject().get("data").getAsJsonObject().get("visitId").getAsString());
        } catch (Exception e) {
        }
        try {
            visit.setApplication(element.getAsJsonObject().get("data").getAsJsonObject().get("application").getAsString());
        } catch (Exception e) {
        }
        return visit;
    }

    public static DynatraceAction parseAction(String entry) {
        DynatraceAction action =  new DynatraceAction();
        action.setOriginalString(entry);
        JsonElement element = new JsonParser().parse(entry);

        if (element.getAsJsonObject().get("data") ==null){return null;}
        if (element.getAsJsonObject().get("data").getAsJsonObject().get("name")==null){return null;}

        try {
            action.setApplication(element.getAsJsonObject().get("data").getAsJsonObject().get("application").getAsString());
        } catch (Exception e) {
        }
        try {
            action.setActionName(element.getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString());
        } catch (Exception e) {
        }
        try {
            action.setActionPrettyName(element.getAsJsonObject().get("data").getAsJsonObject().get("prettyName").getAsString());
        } catch (Exception e) {
        }
        try {
            action.setType(element.getAsJsonObject().get("data").getAsJsonObject().get("type").getAsString());
        } catch (Exception e) {
        }
        try {
            action.setTargetUrl(element.getAsJsonObject().get("data").getAsJsonObject().get("target").getAsJsonObject().get("url").getAsString());
        } catch (Exception e) {
        }
        try {
            action.setVisitId(element.getAsJsonObject().get("data").getAsJsonObject().get("visitId").getAsString());
        } catch (Exception e) {
        }
        try {
            action.setStartTime(element.getAsJsonObject().get("data").getAsJsonObject().get("startTime").getAsLong());
        } catch (Exception e) {
        }
        try {
            action.setEndTime(element.getAsJsonObject().get("data").getAsJsonObject().get("startTime").getAsLong());
        } catch (Exception e) {
        }
        try {
            action.setTagId(element.getAsJsonObject().get("data").getAsJsonObject().get("tagId").getAsLong());
        } catch (Exception e) {
        }
        return action;
    }


}
