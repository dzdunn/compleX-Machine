package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.ast.CompilationUnit;
import uk.ac.uwe.complexmachine.CompleXException;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.model.TestResult;
import uk.ac.uwe.complexmachine.model.Transition;
import uk.ac.uwe.complexmachine.utils.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-4.0
 */
public abstract class AbstractTestService {
    /**
     * Runs the test for the service being called.
     * @param specification the specification to test against
     * @param system the system to look for transitions in
     * @return the results of the test
     */
    public abstract TestResult runTest(Specification specification, CompilationUnit system) throws CompleXException;

    /**
     * Gets the transitions from the specification object.
     * @param specification the specification object to get the transitions from
     * @return the transitions found in the specification object
     */
    protected List<Transition> getTransitionsFromSpecification(Specification specification) {
        return specification.getTransitions();
    }

    /**
     * Gets the methods from the system object.
     * @param system the system to extract the methods from
     * @return the methods found in the system object, in the form of transitions
     */
    protected List<Transition> getMethodsFromSystem(CompilationUnit system) {
        List<Transition> systemMethods = new ArrayList<>();
        MethodVisitor methodVisitor = new MethodVisitor();
        methodVisitor.visit(system, systemMethods);
        return systemMethods;
    }
}
