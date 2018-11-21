package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service.impl;

import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model.DynatraceAction;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service.DynatraceInputFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;

@Service
public class DynatraceInputFilterImpl implements DynatraceInputFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynatraceInputFilter.class);
    private static final String TYPE_VISIT_STRING = "\"_type\":\"visit\"";
    private static final String TYPE_USERACTION_STRING = "\"_type\":\"useraction\"";

    private List<String> APPLICATION_LIST = new ArrayList<>();
    private static final String TEST_ID_KEY = "testRunID";

    private List<String> ACTIONS_TO_FILTER = new ArrayList<>();

    public DynatraceInputFilterImpl(){
        if (System.getProperty("Applications")!=null){
            APPLICATION_LIST.addAll(Arrays.asList(System.getProperty("Applications").split(",")));
        }

        if (System.getProperty("Actions_to_filter")!=null){
            ACTIONS_TO_FILTER.addAll(Arrays.asList(System.getProperty("Actions_to_filter").split(",")));
        }
    }

    public boolean isVisit(@Valid @RequestBody String inputString) {
        return inputString.contains(TYPE_VISIT_STRING);
    }

    public boolean isUserAction(@Valid @RequestBody String inputString) {
        return inputString.contains(TYPE_USERACTION_STRING);
    }


    public boolean hasVisitId(DynatraceAction action) {
        return action != null && action.getVisitId() != null && !"-1".equalsIgnoreCase(action.getVisitId());
    }

    public boolean isCorrectApplication(DynatraceAction action) {
        return action != null && action.getApplication() != null && APPLICATION_LIST.contains(action.getApplication());
    }


    @Override
    public boolean isActionToFilter(DynatraceAction action) {
        return (action != null && action.getActionName() != null && listContainsStringContains(
            action.getActionName()));
    }

    private boolean listContainsStringContains(String text) {
        for (String ActionToFilter : ACTIONS_TO_FILTER) {
            if(text.contains(ActionToFilter)) {
                return true;
            }
        }
        return false;
    }

}
