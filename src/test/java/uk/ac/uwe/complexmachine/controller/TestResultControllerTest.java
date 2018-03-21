package uk.ac.uwe.complexmachine.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.core.convert.ConversionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.uwe.complexmachine.CompleXMachine;
import uk.ac.uwe.complexmachine.model.TestResult;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.junit.Assert.assertThat;

/**
 * @author Danny Dunn
 * @author Alex Mewha
 * @version alpha-5.0
 * @since alpha-5.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CompleXMachine.class})
@WebAppConfiguration
public class TestResultControllerTest {
    /**
     * The message key returned in the response from the controller.
     */
    private static final String MESSAGE_KEY = "message";
    /**
     * The web application context for the test.
     */
    @Autowired
    private transient WebApplicationContext context;

    /**
     * The mock MVC to test the controller requests.
     */
    private static MockMvc mockMvc;
     /**
     * The controller being tested.
     */
    @InjectMocks
    private transient TestResultController mockTestController;
    /**
     * Sets the mock MVC for the controller requests. Runs before
     * every test.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    /**
     * Tests the controller successfully displayed test results on the results page.
     */
    @Test
    public void shouldReturnResultsPage() {
        //Given the controller

        //When valid JSON String is passed to the controller
        ModelAndView result = mockTestController.showTestResults("");

        //The result should be controller proceeds to the results page with the test results displayed
        assertThat("The result should be the page with the results displayed", result.getViewName(), is("testResultsPage"));
    }
    /**
     * Tests the controller displays an error message on the results page if invalid JSON is passed to the controller.
     */
    @Test
    public void shouldReturnErrorMessageifNonValidJsonStringParsed() throws JsonParseException {
        //Given the controller

        //When invalid JSON String is passed to the controller
        ModelAndView result = mockTestController.showTestResults("");

        //The result should be controller returns error message informing user that Test Result object oculd not be created
        Map<String, Object> resultView = result.getModel();
        assertThat("Invalid JSON String could not be parsed", resultView, hasEntry(MESSAGE_KEY, "Could not create Test Result object"));
    }

    /**
     * Tests the controller displays an error message on the results page if JSON cannot be converted into a test object.
     */
    @Test
    public void shouldReturnErrorMessageIfValidJsonNotConvertedToTestObject() {
        //Given the controller


        //When valid JSON String is passed to the controller, but cannot be converted into the test object
        ModelAndView result = mockTestController.showTestResults("{ \"name\":\"John\", \"age\":31, \"city\":\"New York\" }");

        //The result should be controller returns error message informing user that Test Result object oculd not be created
        Map<String, Object> resultView = result.getModel();
        assertThat("Results could not be converted to a TestResult Object", resultView, hasEntry(MESSAGE_KEY, "Could not create Test Result object"));
    }
    /**
     * Tests the controller displays an error message on the results page if a null value is passed to the controller.
     */
    @Test
    public void shouldReturnErrorMEssageIfNullValuePassedToController() {
        //Given the controller


        //When null value is passed to the controller
        ModelAndView result = mockTestController.showTestResults(null);

        //The result should be controller proceeds to the results page with error message informing user Test results were null
        Map<String, Object> resultView = result.getModel();
        assertThat("Null value was passed to the controller", resultView, hasEntry(MESSAGE_KEY, "Test results were null"));
    }

    /**
     * Tests the controller displays an error message on the results page if a single word value is passed to the controller.
     */
    @Test
    public void shouldReturnErrorMessageIfWordPassedToControllerNotJson(){
        //Given the controller

        //When single 'word' value is passed to the controller
        ModelAndView result = mockTestController.showTestResults("word");

        //The result should be controller returns error message informing user that Test Result object could not be created
        Map<String, Object> resultView = result.getModel();
        assertThat("Valid JSON was not passed to create the Test Object", resultView, hasEntry(MESSAGE_KEY, "Could not create Test Result object"));

    }
    /**
     * Tests the controller returns a valid test result object when a valid JSON is passed to the controller.
     */
    //Should return valid Test Result object if valid JSON passed
    @Test
    public void shouldReturnTestResultsifValidJsonPassedAndTestResultObjectCreated() {
        //Given the controller

        //When valid JSON is passed to the controller
        ModelAndView result = mockTestController.showTestResults("{\"testID\":null,\"timeStamp\":1490363593091,\"tests\":[{\"testID\":\"Test1\",\"expectedTransition\":\"prepareToStop()\",\"actualTransition\":\"prepareToStop()\",\"result\":\"PASS\"},{\"testID\":\"Test2\",\"expectedTransition\":\"stop()\",\"actualTransition\":\"stop()\",\"result\":\"PASS\"},{\"testID\":\"Test3\",\"expectedTransition\":\"prepareToGo()\",\"actualTransition\":\"prepareToGo()\",\"result\":\"PASS\"},{\"testID\":\"Test4\",\"expectedTransition\":\"go()\",\"actualTransition\":\"go()\",\"result\":\"PASS\"}]}");

        //The result should be controller returns valid Test Result Object
        Map<String, Object> resultView = result.getModel();
        Object resultObject = resultView.get("testResults");
        assertThat("Valid Test Results should be contained in the model", resultObject, instanceOf(TestResult.class));
    }
}
