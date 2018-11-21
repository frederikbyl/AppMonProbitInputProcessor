package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.cache;

import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model.DynatraceAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class DynatraceSessionRepository {

    private static final Logger LOGGER = LoggerFactory
        .getLogger(DynatraceSessionRepository.class);

    private static final Map<String, List<DynatraceAction>> dynatraceActionMap = new ConcurrentHashMap<>();


    public List<DynatraceAction> getActionList(String visitId) {
        LOGGER.debug("getActionList for visit {}", visitId);
        if (dynatraceActionMap.get(visitId) == null) {
            return null;
        }
        return dynatraceActionMap.get(visitId).stream().sorted(
            (o1, o2) -> (o1.getStartTime() - o2.getStartTime() < 0) ? -1
                : (o1.getStartTime().equals(o2.getStartTime())) ? new Long(o1.getTagId() - o2.getTagId()).intValue() : 1)
            .collect(Collectors.toList());
    }


    public void addAction(String visitId, DynatraceAction action) {
        LOGGER.debug("addAction for visit {}", visitId);
        if (dynatraceActionMap.containsKey(visitId)) {
            LOGGER.debug("visit found {}", visitId);
            dynatraceActionMap.get(visitId).add(action);
        } else {
            LOGGER.debug("visit notfound {}", visitId);
            List<DynatraceAction> actions = new ArrayList<>();
            actions.add(action);
            dynatraceActionMap.put(visitId, actions);
        }
    }


    public void removeVisit(String visitId) {
        LOGGER.debug("removeVisit {}", visitId);
        dynatraceActionMap.remove(visitId);
    }


    public void removeAll() {
        dynatraceActionMap.clear();
    }


    public Set<String> getVisits() {
        return dynatraceActionMap.keySet();
    }
}
