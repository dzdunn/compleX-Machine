package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import uk.ac.uwe.complexmachine.CompleXException;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.model.TestResult;
import uk.ac.uwe.complexmachine.utils.ClassCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.uwe.complexmachine.testutils.TestHelper.createTransitionListWithTargets;
import static uk.ac.uwe.complexmachine.testutils.TestResultMatcher.*;

/**
 * @author Clare Daly
 * @since alpha-6.0
 * @version alpha-6.0
 */
public class MisdirectedTransitionsTestServiceTest {
    /**
     * Tests that a TestResult object is returned.
     */
    @Test
    public void shouldReturnATestResultsObject() throws CompleXException {
        // Given the misdirected transitions test service
        MisdirectedTransitionsTestService service = new MisdirectedTransitionsTestService();

        // When running the test
        Object result = service.runTest(new Specification(), new CompilationUnit());

        // Then a test result object should be returned
        assertThat("Service should return a test result object", result, instanceOf(TestResult.class));
    }

    /**
     * Tests that the expected transitions and states are included in the test result.
     */
    @Test
    public void shouldReturnAllTheExpectedTransitionsAndTargetsInTheTestResult() throws FileNotFoundException, CompleXException {
        // Given the misdirected transitions test service and a specification object
        MisdirectedTransitionsTestService service = new MisdirectedTransitionsTestService();
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionListWithTargets());
        CompilationUnit compilationUnit = JavaParser.parse(getFile("MethodsTargets.java"));

        // When running the test
        TestResult result = service.runTest(testSpecification, compilationUnit);

        // The a test result object containing the expected transitions with targets should be returned
        assertThat("The TestResult object should contain all the transitions with targets", result, containsAllExpectedTransitionsWithTargets(createTransitionListWithTargets()));
    }

    /**
     * Tests that the actual transitions and states are included in the test.
     */
    @Test
    public void shouldIncludeTheActualEndingStateInTestResult() throws FileNotFoundException, CompleXException {
        // Given the misdirected transitions test service and a specification object and a Java file
        // with the expected transition and targets
        MisdirectedTransitionsTestService service = new MisdirectedTransitionsTestService();
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionListWithTargets());
        CompilationUnit compilationUnit = JavaParser.parse(getFile("MethodsTargets.java"));

        // When running the test
        TestResult result = service.runTest(testSpecification, compilationUnit);

        // The a test result object containing the actual transitions with targets should be returned
        assertThat("TestResult object should contain the actual ending state for the transition", result, containsAllActualTransitionsWithTargets(createTransitionListWithTargets()));
    }

    /**
     * Tests that the misdirected transitions test passes if the method results in the correct state.
     */
    @Test
    public void shouldReturnPassForExpectedTransitionsWithTargetsInTheTestResult() throws FileNotFoundException, CompleXException {
        // Given the misdirected transitions test service and a specification object and a Java file
        // with the expected transition and targets
        MisdirectedTransitionsTestService service = new MisdirectedTransitionsTestService();
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionListWithTargets());
        CompilationUnit compilationUnit = JavaParser.parse(getFile("MethodsTargets.java"));

        // When running the test
        TestResult result = service.runTest(testSpecification, compilationUnit);

        // Then the tests should all pass
        assertThat("All tests should pass for transitions and targets that occur", result, allTestArePassed());
    }

    /**
     * Tests that the misdirected transition test will fail if the method results in an incorrect state.
     */
    @Test
    public void shouldReturnFailForExpectedTransitionsWithTargetsNotInTheTestResult() throws FileNotFoundException, CompleXException {
        // Given the misdirected transitions test service and a specification object and a Java file
        // with the expected transition and targets
        MisdirectedTransitionsTestService service = new MisdirectedTransitionsTestService();
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionListWithTargets());
        CompilationUnit compilationUnit = JavaParser.parse(getFile("MethodsIncorrectTargets.java"));

        // When running the test
        TestResult result = service.runTest(testSpecification, compilationUnit);

        // Then the tests should all fail
        assertThat("All tests should fail for transitions and targets that do not occur", result, allTestAreFailed());
    }

    /**
     * Gets the test file from the test resources folder within the project.
     * @param fileName the name of the file to retrieve
     * @return the XML file from the test resources folder
     */
    private File getFile(String fileName) {
        final URL resource = Thread.currentThread().getContextClassLoader().getResource(fileName);
        // Here we need to ensure the path to the file does not have any
        // space encoded, in case of space appearing in the directory or
        // file names e.g. "My Documents", "Program Files",
        // "Test File.xml"
        return new File(resource.getFile().replace("%20", " "));
    }
}
