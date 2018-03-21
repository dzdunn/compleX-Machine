package uk.ac.uwe.complexmachine.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Clare Daly
 * @version alpha-1.0
 * @since alpha-1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Specification {
    /**
     * The transitions expected by the specification.
     */
    @XmlElementWrapper
    @XmlElement(name = "transition")
    private List<Transition> transitions;

    /**
     * Returns the transitions expected by the specification.
     * @return the transitions expected by the specification
     */
    public List<Transition> getTransitions() {
        return transitions;
    }

    /**
     * Sets the transitions expected by the specification.
     * @param transitions the transitions expected by the specification
     */
    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
}
