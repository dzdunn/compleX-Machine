package uk.ac.uwe.complexmachine.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Adebayo Kabeer
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Variable {
    /**
     * The type of Variable
     */
    private String type;
    /**
     * The name of variable
     */
    private String name;
    /**
     * The value of variable
     */
    @XmlElement(type = String.class)
    private Object value;


    /**
     * Sets the value of the type of Variable
     * @param type the type of the Variable.
     */
   public void setType(String type){
        this.type = type;
    }

    /**
     * Returns the type of the Variable
     * @returns the type of the Variable
     */
   public String getType(){
       return type;
   }

    /**
     * Sets the name of the Variable
     * @param name tha name of the Variable
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the set name of the instance variable name
     * @return the name of the Variable
     */
    public String getName(){
        return name;
    }

    /**
     * Sets value of the Variable
     * @param value the value of the Variable
     */
   public void setValue(Object value){
        this.value = value;
    }

    /**
     * Returns the value of the instance variable value
     * @return the value of the Variable
     */
    public Object getValue(){
        return value;
    }

    /**
     * (inheritDoc)
     */
    @Override
    @SuppressWarnings("PMD")
    public String toString() {
        return type + ' ' + name + " = " + value;
    }

    /**
     * (inheritDoc)
     */
    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (type != null ? !type.equals(variable.type) : variable.type != null) return false;
        if (name != null ? !name.equals(variable.name) : variable.name != null) return false;
        return value != null ? value.equals(variable.value) : variable.value == null;
    }

    /**
     * (inheritDoc)
     */
    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
