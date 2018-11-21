package integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by thomasrotte on 20/11/2018.
 */
public class SendTestSetTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendTestSetTest.class);

    private String filename = "myFile";
    private String separator = "#####";
    private String dynatraceUrl = "http://localhost:9090/dynatrace/test";

    @Test
    public void sendTestSetFromFile() {
        //try {
            //readFile();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        //after this; check if the data is in the database (via clusterhandler)
    }

    public void readFile() throws IOException {
        FileInputStream inputStream = null;
        Scanner sc = null;
        String line;
        String request;
        StringBuilder sb = new StringBuilder();
        try {
            inputStream = new FileInputStream(filename);
            sc = new Scanner(inputStream);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                LOGGER.debug("line: {}", line);

                if (line.equals(separator)) {
                    request = sb.subSequence(0, sb.lastIndexOf(System.lineSeparator())).toString();
                    LOGGER.debug("\tsending request: {}", request);
                    sendRequest(request);
                    sb.setLength(0);
                    pause();
                } else {
                    sb.append(line).append(System.lineSeparator());
                }
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn("file not found {}", filename, fnfe);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

    }

    private void pause() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            LOGGER.error("Thread.sleep throw exception", e);
        }
    }


    public void sendRequest(String request) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                LOGGER.debug("Sending request to DynatraceBulkInputProcessor... {}", request);
                String response = restTemplate.postForObject(dynatraceUrl, request, String.class);
                LOGGER.debug("DynatraceBulkInputProcessor.response \t{} ", response);
            } catch (Exception e) {
                LOGGER.error("Error received from DynatraceBulk when REST call", e);
            }
    }

}
