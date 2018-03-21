package uk.ac.uwe.complexmachine.model;


/**
 * @author Adebayo Kabeer
 * @version alpha-3.0
 * @since alpha-3.0
 */
public class Test {

    /**
     * The test identification
     */
    private String testID;

    /**
     * The expected transition
     */
    private String expectedTransition;

    /**
     * The actual transition
     */
    private String actualTransition ;

    /**
     * The result of the test
     */
    public Result result;

    /**
     * sets the test ID
     * @param testID the parameter required for testID
     */
    public void setTestID(String testID) {
        this.testID = testID;
    }

    /**
     * Sets the expected Transition for the test
     * @param expectedTransition the expected transition for the test
     */
    public void setExpectedTransition(String expectedTransition) {
        this.expectedTransition = expectedTransition;
    }

    /**
     * Sets the actual Transition found in the test
     * @param actualTransition the actual Transition found in the test
     */
    public void setActualTransition(String actualTransition) {
        this.actualTransition = actualTransition;
    }

    /**
     * Sets the result of the test
     * @param result the result of the test
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Returns the test identification
     * @return the test identification
     */
    public String getTestID() {
        return testID;
    }

    /**
     * Returns the expected transition
     * @return the expected transition
     */
    public String getExpectedTransition() {
        return expectedTransition;
    }

    /**
     * Returns the actual transition
     * @return the actual transition
     */
    public String getActualTransition() {
        return actualTransition;
    }

    /**
     * Returns the result of the test
     * @return the result of the test
     */
    public Result getResult() {
        return result;
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD")
    @Override
    public String toString() {
        return "Test{" +
                "testID='" + testID + '\'' +
                ", expectedTransition='" + expectedTransition + '\'' +
                ", actualTransition='" + actualTransition + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        if (testID != null ? !testID.equals(test.testID) : test.testID != null) return false;
        if (expectedTransition != null ? !expectedTransition.equals(test.expectedTransition) : test.expectedTransition != null)
            return false;
        if (actualTransition != null ? !actualTransition.equals(test.actualTransition) : test.actualTransition != null)
            return false;
        return result == test.result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD")
    @Override
    public int hashCode() {
        int result1 = testID != null ? testID.hashCode() : 0;
        result1 = 31 * result1 + (expectedTransition != null ? expectedTransition.hashCode() : 0);
        result1 = 31 * result1 + (actualTransition != null ? actualTransition.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}
