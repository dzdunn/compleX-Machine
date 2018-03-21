package uk.ac.uwe.complexmachine.controller;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import uk.ac.uwe.complexmachine.CompleXException;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.model.TestResult;
import uk.ac.uwe.complexmachine.service.AbstractTestService;
import uk.ac.uwe.complexmachine.service.TestServiceFactory;

import java.util.Map;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static uk.ac.uwe.complexmachine.CompleXMachine.system;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-3.0
 */
public class StartTestControllerTest {
    /**
     * The error message for a null specification file.
     */
    private static final String NO_SPECIFICATION = "Could not start test. Missing:\nspecification\n";
    /**
     * The error message for a null system file.
     */
    private static final String NO_SYSTEM = "Could not start test. Missing:\nsystem\n";
    /**
     * The error message for a missing test type.
     */
    private static final String NO_TEST_TYPE = "Could not start test. Missing:\ntest type\n";
    /**
     * The error message for null specification and system files.
     */
    private static final String NO_SPECIFICATION_OR_SYSTEM = "Could not start test. Missing:\nspecification\nsystem\n";
    /**
     * The error message for when all inputs are missing.
     */
    private static final String NO_INPUTS = "Could not start test. Missing:\nspecification\nsystem\ntest type\n";
    /**
     * The key for message in the controller response.
     */
    private static final String MESSAGE_KEY = "message";
    /**
     * The test type for missing transitions.
     */
    private static final String MISSING_TRANSITION = "missingTransition";
    /**
     * Error message for an invalid test type.
     */
    private static final String INVALID_TEST_TYPE = "Invalid test type selected";
    /**
     * Error message if JSON String cannot be parsed to Specification object.
     */
    private static final String CANNOT_CREATE_OBJECT = "Could not create specification object";
    /**
     * A mock of the test service to use in the controller
     */
    private transient AbstractTestService testService;

    @Mock
    private transient TestServiceFactory testServiceFactory;

    /**
     * The mocked test result to return from the service.
     */
    private TestResult mockedTestResult;

    /**
     * The controller being tested.
     */
    @InjectMocks
    private transient StartTestController controller;

    @Before
    public void setup() throws CompleXException {
        MockitoAnnotations.initMocks(this);
        mockedTestResult = mock(TestResult.class);
        testService = mock(AbstractTestService.class);
        when(testService.runTest(any(), any())).thenReturn(mockedTestResult);
        when(testServiceFactory.getTestService(anyString())).thenReturn(testService);
    }

    /**
     * Tests that the controller calls the test service.
     */
    @Test
    public void shouldCallTheTestService() throws CompleXException {
        // Given the start test controller
        system = new CompilationUnit();

        // When starting a test
        controller.startTest("{}", MISSING_TRANSITION);

        // Then the AbstractTestService should have been called
        verify(testService, times(1)).runTest(any(Specification.class), any());
    }

    /**
     * Tests the controller returns the TestResult returned from the service.
     */
    @Test
    public void shouldReturnATestResultObjectFromTheServiceInTheModel() {
        // Given the start test controller
        system = new CompilationUnit();

        // When starting a test
        ResponseEntity result = controller.startTest("{}", MISSING_TRANSITION);

        // Then the result should have a test result in the model from the serview
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should have a test result in the model", resultBody, hasEntry("result", mockedTestResult));
    }

    /**
     * Tests the controller returns a failure message if neither the specification or system has been provided.
     */
    @Test
    public void shouldReturnAFailureMessageIfSpecificationAndSystemAreNull() {
        // Given the start test controller and no specification or system objects
        system = null;

        // When starting a test
        ResponseEntity result = controller.startTest(null, MISSING_TRANSITION);

        // Then an error message should be returned to the view
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message if neither the specification or system have been provided", resultBody, hasEntry(MESSAGE_KEY, NO_SPECIFICATION_OR_SYSTEM));
    }

    /**
     * Tests the controller returns the message to the user if the specification is null and system is provided.
     */
    @Test
    public void shouldReturnAFailureMessageIfSpecificationIsNull() {
        // Given the start test controller and a system object
        system = new CompilationUnit();

        // When starting a test
        ResponseEntity result = controller.startTest(null, MISSING_TRANSITION);

        // Then the result should have a message indicating a null specification file with advice
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message if Specification is null", resultBody, hasEntry(MESSAGE_KEY, NO_SPECIFICATION));
    }

    /**
     * Tests the controller returns the message to the user if the system is null and specification is provided.
     */
    @Test
    public void shouldReturnAFailureMessageIfSystemIsNull() {
        // Given the start test controller
        system = null;

        // When starting a test
        ResponseEntity result = controller.startTest("{}", MISSING_TRANSITION);

        // Then the result should have a message indicating a null system file with advice
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message if System is null", resultBody, hasEntry(MESSAGE_KEY, NO_SYSTEM));
    }

    /**
     * Tests the controller returns the message to the user if the test type is null and the system and specification are provided.
     */
    @Test
    public void shouldReturnAFailureMessageIfTestTypeIsNull() {
        // Given the start test controller
        system = new CompilationUnit();

        // When starting a test
        ResponseEntity result = controller.startTest(new Specification().toString(), null);

        // Then the result should have a message saying the test type was not provided
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message if test type is null", resultBody, hasEntry(MESSAGE_KEY, NO_TEST_TYPE));
    }

    /**
     * Tests the controller returns a failure message if all required inputs are missing.
     */
    @Test
    public void shouldReturnAFailureMessageIfAllInputsAreMissing() {
        // Given the start test controller and no specification or system objects
        system = null;

        // When starting a test
        ResponseEntity result = controller.startTest(null, null);

        // Then an error message should be returned to the view
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message if none of the required inputs have been provided", resultBody, hasEntry(MESSAGE_KEY, NO_INPUTS));
    }

    /**
     * Tests the controller returns a failure message if an invalid test type is provided.
     */
    @Test
    public void shouldReturnAFailureMessageIfAnInvalidTestTypeIsProvided() {
        // Given the start test controller with an invalid test type
        system = new CompilationUnit();
        when(testServiceFactory.getTestService("notATestType")).thenThrow(new IllegalArgumentException(INVALID_TEST_TYPE));

        // When starting the test
        ResponseEntity result = controller.startTest("{}", "notATestType");

        // Then an error message should be returned to the view
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message an invalid test type is provided", resultBody, hasEntry(MESSAGE_KEY, INVALID_TEST_TYPE));
    }

    /**
     * Tests the controller returns a failure message if a specification object cannot be made.
     */
    @Test
    public void shouldReturnAFailureMessageIfTheSpecificationObjectCannotBeCreated() {
        // Given the start test controller and a JSON String that cannot be converted into a specification object
        system = new CompilationUnit();

        // When starting the test
        ResponseEntity result = controller.startTest("{\"testString\":\"invalid\"}", "notATestType");

        // Then an error message should be returned to the view
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message if an JSON string is provided that cannot be parsed into a specification object", resultBody, hasEntry(MESSAGE_KEY, CANNOT_CREATE_OBJECT));
    }

    /**
     * Tests the controller returns a failure message if the test cannot be run.
     */
    @Test
    public void shouldReturnAFailureMessageIfTheTestCannotBeRun() throws CompleXException {
        // Given the start test controller and a test that cannot be run
        system = new CompilationUnit();
        AbstractTestService mockTestService = mock(AbstractTestService.class);
        when(mockTestService.runTest(any(), any())).thenThrow(CompleXException.class);
        when(testServiceFactory.getTestService(anyString())).thenReturn(mockTestService);

        // When starting the test
        ResponseEntity result = controller.startTest("{}", "testType");

        // Then an error message should be returned to the view
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("ModelAndView should return a failure message an invalid test type is provided", resultBody, allOf(hasKey(MESSAGE_KEY), not(hasKey("result"))));
    }

}