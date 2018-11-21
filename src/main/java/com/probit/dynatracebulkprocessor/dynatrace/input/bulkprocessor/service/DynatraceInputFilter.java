package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service;


import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model.DynatraceAction;

public interface DynatraceInputFilter {

    boolean isVisit(String input);

    boolean isUserAction(String input);

    boolean hasVisitId(DynatraceAction action);

    boolean isCorrectApplication(DynatraceAction action);

    boolean isActionToFilter(DynatraceAction action);
}
