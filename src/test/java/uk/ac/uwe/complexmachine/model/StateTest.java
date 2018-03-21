package uk.ac.uwe.complexmachine.model;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * @author Clare Daly
 * @vesion alpha-6.0
 * @since alpha-6.0
 */
public class StateTest {
    /**
     * Tests that a variable is added to the list when no list currently exists.
     */
    @org.junit.Test
    public void shouldAddVariableToTheList() {
        // Given a State object and a variable to add
        State state = new State();
        Variable variableToAdd = new Variable();
        variableToAdd.setName("VariableName");

        // When the variable is added to the list of variables in the state
        state.addVariable(variableToAdd);

        // Then the variable should be added to the list
        assertThat("Variable should have been added to the list", state.getVariables(), hasItem(variableToAdd));
    }

    /**
     * Tests that a variable is added to an existing list without overriding the
     * existing values.
     */
    @org.junit.Test
    public void shouldAddTestToListAlreadyContainingATest() {
        // Given a State object with a variable in the list and a variable to add
        State state = new State();
        Variable firstVariable = new Variable();
        state.addVariable(firstVariable);
        Variable variableToAdd = new Variable();

        // When the variable is added to the list of variables in the state
        state.addVariable(variableToAdd);

        // Then there should be two variables in the list
        assertThat("There should be two variables in the list", state.getVariables(), both(hasItem(firstVariable)).and(hasItem(variableToAdd)));
    }
}
