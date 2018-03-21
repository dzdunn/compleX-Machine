package uk.ac.uwe.complexmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Danny Dunn
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class State {
    /**
     * The name of the state.
     */
    private String name;
    /**
     * Flag setting this as the starting state for the X-Machine.
     */
    @XmlElement(defaultValue = "false")
    private Boolean startingState;//NOPMD
    /**
     * The variables list required for define states.
     */
    @XmlElementWrapper
    @XmlElement(name = "variable")
    private List<Variable> variables;
    /**
     * Returns the name of the state.
     * @return the name of the state.
     */
    public String getName(){ return name;}
    /**
     * Sets the name of state.
     * @param name the name of the state.
     */
    public void setName (String name) {this.name = name;}
    /**
     * Returns the whether this is the starting state for the x-machine.
     * @return true if this is the starting state for the x-machine..
     */
    public boolean isStartingState() {return startingState;}
    /**
     * Sets whether this is the starting state.
     * @param startingState whether this is a starting state.
     */
    public void setIsStartingState (boolean startingState) {this.startingState = startingState;}
    /**
     * Returns the variables required for the state.
     * @return the variables required for the state
     */
    public List<Variable> getVariables() {
        return variables;
    }
    /**
     * The variables required for the state.
     * @param variables the variables required for the state
     */
    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    /**
     * Adds a variables to the variables list.
     * @param variable the variable to add to the list
     */
    public void addVariable(Variable variable) {
        if(null == variables) {
            variables = new ArrayList<>();
        }
        variables.add(variable);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", startingState=" + startingState +
                '}';
    }

    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (name != null ? !name.equals(state.name) : state.name != null) return false;
        return startingState != null ? startingState.equals(state.startingState) : state.startingState == null;
    }

    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (startingState != null ? startingState.hashCode() : 0);
        return result;
    }

    /**
     * Returns a String representation of the variables present in the state.
     * @return a String representation of the variables present in the state
     */
    @JsonIgnore
    public String getState() {
        return variables.stream()
                .map(Variable::toString)
                .collect(Collectors.joining(", "));
    }
}


