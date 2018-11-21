package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service.impl;

import com.google.gson.Gson;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.cache.DynatraceSessionRepository;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.model.*;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service.DynatraceInputFilter;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service.DynatraceInputProcessorService;
import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.util.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Stream;

@Service
public class DynatraceInputProcessorServiceImpl implements DynatraceInputProcessorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynatraceInputProcessorServiceImpl.class);

    @Autowired
    private DynatraceInputFilter inputFilter;

    @Autowired
    private DynatraceSessionRepository sessionRepository;

    private Long startTime = new Date().getTime();
    private long delayInSeconds = 0;
    private static final Gson GSON = new Gson();

    private long minimumActionsForSession = 5;

    private String user = "probit";
    private String password = "probit";

    private String endPoint = "http://localhost:9094/session/enqueue";

    public DynatraceInputProcessorServiceImpl(){
        if (System.getProperty("Delay")!=null){
            try {
                delayInSeconds = Long.parseLong(System.getProperty("Delay"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (System.getProperty("Endpoint")!=null){
            endPoint = System.getProperty("Endpoint");
        }
        if (System.getProperty("User")!=null){
            user = System.getProperty("User");
        }
        if (System.getProperty("Password")!=null){
            password = System.getProperty("Password");
        }
        if (System.getProperty("MinimumActionsForSession")!=null){
            try {
                minimumActionsForSession = Long.parseLong(System.getProperty("MinimumActionsForSession"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Takes the complete input as String, splits into multiple entries and processes each
     */
    @Override
    public void process(String inputString) {
        String[] entries = inputString.split(System.lineSeparator());

        if (inputFilter.isVisit(inputString)) {
            LOGGER.info("visits received");
            parseAndProcessVisits(entries);
        }

        if (inputFilter.isUserAction(inputString)) {
            LOGGER.info("actions received");
            parseAndProcessUserActions(entries);
        }
    }

    private void parseAndProcessVisits(String[] entries) {
        LOGGER.debug("PARSING VISITS");
        Stream.of(entries)
                .map(Parser::parseVisit)
                .filter(Objects::nonNull)
                .forEach(this::processVisit);
    }

    private void processVisit(DynatraceVisit visit) {
        if (visit != null) {
            LOGGER.debug("VISIT : visit data not null");
            if (visit.getId() != null) {
                LOGGER.debug("visitId: {}, visitTag {}", visit.getId());
                List<DynatraceAction> actionList = sessionRepository.getActionList(visit.getId());
                if (actionList != null && actionList.size()>= minimumActionsForSession) {
                    LOGGER.info("actionList found for visit{}, numActions {}", visit.getId(), actionList.size());
                    visit.setActions(actionList);
                    processSession(visit);
                } else {
                    LOGGER.debug("actionList not found for visitId {}", visit.getId());
                }
                sessionRepository.removeVisit(visit.getId());
            } else {
                LOGGER.debug("Visit without visitId");
            }
        }
    }

    private void processSession(DynatraceVisit visit) {
        if (delayExpired()) {
            ProbitSession probitSession = new ProbitSession();
            probitSession.setType(visit.getApplication());

            List<ProbitAction> probitActions = new ArrayList<>();
            for (DynatraceAction action: visit.getActions()) {
                ProbitAction probitAction = new ProbitAction();
                probitAction.setName(action.getActionPrettyName());
                List<ProbitParameter> probitParameters = new ArrayList<>();
                probitParameters.add(new ProbitParameter("prettyName", action.getActionPrettyName()));
                probitParameters.add(new ProbitParameter("target", action.getTargetUrl()));
                probitParameters.add(new ProbitParameter("type", action.getType()));
                probitParameters.add(new ProbitParameter("startTime", action.getStartTime().toString()));
                probitParameters.add(new ProbitParameter("endTime", action.getEndTime().toString()));
                probitAction.setParameters(probitParameters);
                probitActions.add(probitAction);
            }

            probitSession.setActions(probitActions);
            send(probitSession);
        } else {
            LOGGER.debug("Session {} not sent to probit.", visit.getId());
        }
    }

    private void parseAndProcessUserActions(String[] entries) {
        LOGGER.debug("PARSING ACTIONS");
        Stream.of(entries)
                .map(Parser::parseAction)
                .filter(Objects::nonNull)
                .forEach(this::processUserAction);
    }

    private void processUserAction(DynatraceAction action) {
        if (action != null) {
            LOGGER.debug("ACTION: actiondata not null");
            if (isActionToStore(action)) {
                LOGGER.debug("Action with visitId: {} name {}", action.getVisitId(), action.getActionName());
                sessionRepository.addAction(action.getVisitId(), action);
            }
        }
    }

    private boolean isActionToStore(DynatraceAction action) {
        return inputFilter.hasVisitId(action)
                && inputFilter.isCorrectApplication(action);
    }

    private boolean delayExpired() {
        return (new Date().getTime() - startTime) > delayInSeconds * 1000;
    }

    private void send(ProbitSession session) {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getInterceptors().add(
                new BasicAuthorizationInterceptor(user, password));
            Object response = rest.postForObject(endPoint, this.getJsonEntity(session), Object.class);
            LOGGER.debug("response {}", response);
        } catch (Exception e) {
            LOGGER.error("Can't send session", e);
        }
    }

    private HttpEntity<String> getJsonEntity(ProbitSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(this.toJson(session), headers);
    }

    private String toJson(ProbitSession session) {
        String json = GSON.toJson(session);
        LOGGER.debug("session json -> {}", json);
        return json;
    }


}
