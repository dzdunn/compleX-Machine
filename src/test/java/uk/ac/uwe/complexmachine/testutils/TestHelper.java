package uk.ac.uwe.complexmachine.testutils;

import uk.ac.uwe.complexmachine.model.Parameter;
import uk.ac.uwe.complexmachine.model.State;
import uk.ac.uwe.complexmachine.model.Transition;
import uk.ac.uwe.complexmachine.model.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clare Daly
 * @version alpha-4.0
 * @since alpha-4.0
 */
public class TestHelper {

    /**
     * Creates an expected transitions list for use in tests.
     * @return an expected transitions list
     */
    public static List<Transition> createTransitionList() {
        List<Transition> transitions = new ArrayList<>();
        Transition firstTransition = new Transition();
        firstTransition.setName("firstMethod");
        Transition secondTransition = new Transition();
        secondTransition.setName("secondMethod");
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setType("String");
        parameter.setValue("stringParameter");
        parameters.add(parameter);
        secondTransition.setParameters(parameters);
        transitions.add(firstTransition);
        transitions.add(secondTransition);
        return transitions;
    }

    /**
     * Creates an expected transitions list with target states for use in tests.
     * @return an expected transitions list with states
     */
    public static List<Transition> createTransitionListWithTargets() {
        List<Transition> transitions = new ArrayList<>();
        Transition transition = new Transition();
        transition.setName("method");
        State starting = new State();
        State ending = new State();
        Variable startingVariable = new Variable();
        startingVariable.setType("java.lang.String");
        startingVariable.setName("state");
        startingVariable.setValue("starting");
        List<Variable> startingVariables = new ArrayList<>();
        startingVariables.add(startingVariable);
        starting.setVariables(startingVariables);
        Variable endingVariable = new Variable();
        endingVariable.setType("java.lang.String");
        endingVariable.setName("state");
        endingVariable.setValue("ending");
        List<Variable> endingVariables = new ArrayList<>();
        endingVariables.add(endingVariable);
        ending.setVariables(endingVariables);
        transition.setStartingState(starting);
        transition.setFinishingState(ending);
        transitions.add(transition);
        return transitions;
    }
}
