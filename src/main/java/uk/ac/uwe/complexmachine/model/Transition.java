package uk.ac.uwe.complexmachine.model;

import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Transition {
    /**
     * The name of the transition.
     */
    private String name;
    /**
     * The state at the start of the transition.
     */
    private State startingState;
    /**
     * The state at the end of the transition.
     */
    private State finishingState;
    /**
     * The parameters required for the transition.
     */
    @XmlElementWrapper
    @XmlElement(name = "parameter")
    private List<Parameter> parameters;

    /**
     * Returns the name of the transition.
     * @return the name of the transition
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the transition.
     * @param name the name of the transition
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the state at the start of the transition.
     * @return the state at the start of the transition
     */
    public State getStartingState() {
        return startingState;
    }

    /**
     * Sets the state at the start of the transition.
     * @param startingState the state at the start of the transition
     */
    public void setStartingState(State startingState) {
        this.startingState = startingState;
    }

    /**
     * Returns the state at the end of the transition.
     * @return the state at the end of the transition
     */
    public State getFinishingState() {
        return finishingState;
    }

    /**
     * Sets the state at the end of the transition.
     * @param finishingState the state at the end of the transition
     */
    public void setFinishingState(State finishingState) {
        this.finishingState = finishingState;
    }

    /**
     * Returns the parameters required for the transition.
     * @return the parameters required for the transition
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets the parameters required for the transition.
     * @param parameters the parameters required for the transition
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public String toString() {
        StringBuilder transitionString = new StringBuilder(name);
        transitionString.append('(');
        if(null != parameters) {
            transitionString.append(StringUtils.collectionToCommaDelimitedString(parameters));
        }
        transitionString.append(')');
        return transitionString.toString();
    }

    /**
     * Creates a String representation of the transition, with states.
     * @return a String representation of the transition, with states
     */
    public String toStringWithStates() {
        return startingState.getState() + " -> " +
                toString() +
                " -> " +
                finishingState.getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Transition that = (Transition) object;

        return name != null ? name.equals(that.name) : that.name == null
                && (startingState != null ? startingState.equals(that.startingState) : that.startingState == null
                && (finishingState != null ? finishingState.equals(that.finishingState) : that.finishingState == null
                && (parameters != null ? parameters.equals(that.parameters) : that.parameters == null)));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (startingState != null ? startingState.hashCode() : 0);
        result = 31 * result + (finishingState != null ? finishingState.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
