package uk.ac.uwe.complexmachine.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Alex Mewha
 * @author Clare Daly
 * @version alpha-3.0
 * @since alpha-1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {
    /**
     * The type of the Parameter.
     */
    private String type;
    /**
     * The value of the Parameter
     */
    @XmlElement(type = String.class)
    private Object value;
    /**
     * Returns the type of Parameter.
     * @return thee type of Parameter
     */
    public String getType(){
        return type;
    }
    /**
     * Sets the type of Parameter.
     * @param type the type of the Parameter.
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * Returns the value of the Parameter.
     * @return the value of the Parameter.
     */
    public Object getValue(){
        return value;
    }
    /**
     * Sets the value of the Parameter.
     * @param value the value of the Parameter.
     */
    public void setValue(Object value){
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public String toString() {
        return type + " " + value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (type != null ? !type.equals(parameter.type) : parameter.type != null) return false;
        return value != null ? value.equals(parameter.value) : parameter.value == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}

