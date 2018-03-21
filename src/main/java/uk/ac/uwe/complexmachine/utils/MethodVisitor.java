package uk.ac.uwe.complexmachine.utils;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import uk.ac.uwe.complexmachine.model.Parameter;
import uk.ac.uwe.complexmachine.model.Transition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Clare Daly
 * @version alpha-3.0
 * @since alpha-3.0
 */
public class MethodVisitor extends VoidVisitorAdapter<List<Transition>> {

    /**
     * Adds the method from the CompilationUnit to the list of transitions
     * found in the system
     * @param method the method to added to the transitions list
     * @param methods the transition list to add the method to
     */
    @Override
    public void visit (MethodDeclaration method, List<Transition> methods) {
        Transition transition = new Transition();
        transition.setName(method.getNameAsString());
        transition.setParameters(method.getParameters()
                .stream()
                .map(cuParameter -> {
                    Parameter parameter = new Parameter();
                    parameter.setType(cuParameter.getType().toString());
                    parameter.setValue(cuParameter.getNameAsString());
                    return parameter;
                })
                .collect(Collectors.toList()));
        methods.add(transition);
    }

}
