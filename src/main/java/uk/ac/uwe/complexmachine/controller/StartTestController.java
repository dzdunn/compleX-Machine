package uk.ac.uwe.complexmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.uwe.complexmachine.CompleXException;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.model.TestResult;
import uk.ac.uwe.complexmachine.service.AbstractTestService;
import uk.ac.uwe.complexmachine.service.TestServiceFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static uk.ac.uwe.complexmachine.CompleXMachine.system;

/**
 * @author Danny Dunn
 * @author Alex Mewha
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-3.0
 */
@Controller
public class StartTestController {
    /**
     * The key for message in the controller response.
     */
    private static final String MESSAGE_KEY = "message";
    /**
     * Instance of the AbstractTestService service to
     * create the Test Results to return to the results view
     */
    private transient AbstractTestService testService;
    /**
     * Autowired instance of the TestServiceFactory to
     * create the correct test service for the test beign run
     */
    @Autowired
    private transient TestServiceFactory testServiceFactory;

    /**
     * Handles the request for the Test Results page of the tool.
     * Checks any specification or system file is not null
     * @param specificationString the specification file for expected
     *                            states or transitions as a json string.
     * @return the testResultsPage.jsp view
     * or an error message for a null specification file or null system file
     */
    @PostMapping(value = "/startTest")
    public ResponseEntity startTest(@RequestParam("specification") String specificationString, @RequestParam("testType") String testType) {
        Map<String, Object> model = new ConcurrentHashMap<>();
        if (specificationString == null || system == null || testType == null) {
            model.put(MESSAGE_KEY, createNullErrorMessage(specificationString, testType));
        } else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Specification specification = mapper.readValue(specificationString, Specification.class);
                testService = testServiceFactory.getTestService(testType);
                TestResult result = testService.runTest(specification, system);
                model.put("result", result);
                model.put(MESSAGE_KEY, "Test run successfully");

            } catch (IllegalArgumentException exception) {
                model.put(MESSAGE_KEY, exception.getMessage());
            } catch (IOException exception) {
                model.put(MESSAGE_KEY, "Could not create specification object");
            } catch (CompleXException exception) {
                model.put(MESSAGE_KEY, "Could not run test: " + exception.getMessage());
            }
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
    /**
     * Creates the error message for null parameters.
      * @param specificationString the specification string to check for null
     * @param testType the test type string to check for null
     * @return the error message for null parameter values
     */
    private String createNullErrorMessage(String specificationString, String testType) {
        return "Could not start test. Missing:\n"
                + (null == specificationString ? "specification\n" : "")
                + (null == system ? "system\n" : "")
                + (null == testType ? "test type\n" : "");
    }
}
