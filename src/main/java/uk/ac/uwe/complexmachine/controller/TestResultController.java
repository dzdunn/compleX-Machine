package uk.ac.uwe.complexmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.uwe.complexmachine.model.TestResult;

import java.io.IOException;

import static uk.ac.uwe.complexmachine.CompleXMachine.system;

/**
 * @author Clare Daly
 * @author Danny Dunn
 * @author Alex Mewha
 * @version alpha-5.0
 * @since alpha-3.0
 */
@Controller
public class TestResultController {

    /**
     * Message key returned by the controller
     */
    private static final String MESSAGE_KEY = "message";

    /**
     * Handles the request for view the test results.
     *
     * @param resultJson the test results to display as a JSON String
     * @return ModelAndView containing the test results and redirecting to
     * the test results page
     */
    @PostMapping("/testResults")
    public ModelAndView showTestResults(@RequestParam("result") String resultJson) {
        ModelAndView modelAndView = new ModelAndView("testResultsPage");
        if (resultJson == null) {
            modelAndView.addObject(MESSAGE_KEY, "Test results were null");
        } else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                TestResult results = mapper.readValue(resultJson, TestResult.class);
                modelAndView.addObject("testResults", results);
                system = null;//NOPMD
            } catch (IOException e) {
                modelAndView.addObject(MESSAGE_KEY, "Could not create Test Result object");
            }
        }
        return modelAndView;
    }
}