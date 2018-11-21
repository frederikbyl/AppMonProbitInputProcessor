package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class DynatraceAction {

    private String originalString;
    private String application;
    private String actionName;
    private String actionPrettyName;
    private String type;
    private String visitId;
    private String targetUrl;
    private Long tagId;

    private long startTime;
    private long endTime;


    public DynatraceAction(){
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionPrettyName() {
        return actionPrettyName;
    }

    public void setActionPrettyName(String actionPrettyName) {
        this.actionPrettyName = actionPrettyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isClickAction() {
        return ActionTypes.CLICK.getType().equals(getType());
    }

    public boolean isTouchAction() {
        return ActionTypes.TOUCH.getType().equals(getType());
    }

    public boolean isLoadAction() {
        return ActionTypes.LOAD.getType().equals(getType());
    }



}
