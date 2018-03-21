package uk.ac.uwe.complexmachine.model;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * @author Clare Daly
 * @version alpha-3.0
 * @since alpha-3.0
 */
public class TestResultTest {

    @org.junit.Test
    public void shouldAddTestToTheList() {
        // Given a Test Result object and a test to add
        TestResult testResult = new TestResult();
        Test testToAdd = new Test();

        // When the test is added to the list of tests in test result
        testResult.addTest(testToAdd);

        // Then the test should be added to the list
        assertThat("Test should have been added to the list", testResult.getTests(), hasItem(testToAdd));
    }

    @org.junit.Test
    public void shouldAddTestToListAlreadyContainingATest() {
        // Given a TestResult object with a test in the list and a test to add
        TestResult testResult = new TestResult();
        Test firstTest = new Test();
        testResult.addTest(firstTest);
        Test testToAdd = new Test();

        // When the test is added to the list of tests in test result
        testResult.addTest(testToAdd);

        // Then there should be two tests in the list
        assertThat("There should be two tests in the list", testResult.getTests(), both(hasItem(firstTest)).and(hasItem(testToAdd)));
    }
}
