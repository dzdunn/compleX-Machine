package uk.ac.uwe.complexmachine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Danny Dunn
 * @author Clare Daly
 * @version alpha-3.0
 * @since alpha-3.0
 */
public class TestResult {
    /**
     * The identifier for the tested transition or state.
     */
    private String testID;
    /**
     * The time of the test result.
     */
    private Date timeStamp;
    /**
     * The List to contain instances of each test.
     */
    private List<Test> tests; //NOPMD Does not need traditional setter
    /**
     * Returns the test ID.
     * @return the test ID.
     */
    public String getTestID() {
        return testID;
    }
    /**
     * Sets the test ID.
     * @param testID the test ID.
     */
    public void setTestID(String testID) {
        this.testID = testID;
    }
    /**
     * Returns the time of the test.
     * @return the time of the test.
     */
    public Date getTimeStamp() {
        return timeStamp;
    }
    /**
     * Sets the time of the test tesult.
     * @param timeStamp the time of the test result.
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    /**
    * Checks if there is no List for test results.
    *  Creates List for test results if there is no List.
    * @param test the test result to be added to the list.
     */
    public void addTest (Test test){
        if(null == tests) {
            tests = new ArrayList<>();
        }
        tests.add(test);
    }
    /**
     * Returns the test results to a list.
     * @return the tests returned to the list.
     */
    public List<Test> getTests() {
        return tests;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public String toString() {
        return "TestResult{" +
                "testID='" + testID + '\'' +
                ", timeStamp=" + timeStamp +
                ", tests=" + tests +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestResult that = (TestResult) o;

        if (testID != null ? !testID.equals(that.testID) : that.testID != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;
        return tests != null ? tests.equals(that.tests) : that.tests == null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        int result = testID != null ? testID.hashCode() : 0;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (tests != null ? tests.hashCode() : 0);
        return result;
    }
}
