package com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.controller;

import com.probit.dynatracebulkprocessor.dynatrace.input.bulkprocessor.service.DynatraceInputProcessorService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynatraceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynatraceController.class);

    @Autowired
    private DynatraceInputProcessorService dynatraceInputProcessorService;

    @RequestMapping(method = RequestMethod.POST, value = "${com.probit.dynatrace.endpoint}")
    public void createSession(@Valid @RequestBody String inputString, HttpServletRequest request)
            throws ServletException, IOException {
        LOGGER.info("==Request received: {}===================", request.getRequestURI());
        LOGGER.debug("COMPLETE REQUEST: {}", inputString);
        dynatraceInputProcessorService.process(inputString);
        LOGGER.info("=====================================");
    }

    @RequestMapping(method = RequestMethod.GET, value = "${com.probit.dynatrace.endpoint}")
    public void getSession(HttpServletRequest request) {
        LOGGER.info("Request received: " + request.getRequestURI());
    }

}
