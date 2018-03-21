package uk.ac.uwe.complexmachine.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import javassist.*;
import uk.ac.uwe.complexmachine.CompleXException;

import java.util.List;
import java.util.Random;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-6.0
 */
public final class ClassCreator {

    /**
     * The int representation of the letter selector.
     */
    public static final int LETTER = 1;

    /**
     * Private constructor to prevent instantiation
     */
    private ClassCreator() {
        // Private constructor
    }

    /**
     * Creates an instantiable java class from the compilation unit.
     *
     * @param compilationUnit the compilation unit to convert into a java class
     * @return the java class created from the compilation unit
     * @throws CompleXException if the class cannot be created
     */
    public static Class createClassFromCompilationUnit(CompilationUnit compilationUnit) throws CompleXException {
        ClassPool pool = ClassPool.getDefault();
        boolean classExists = true;
        String className = generateClassName();
        while (classExists) {
            try {
                Class.forName(className);
                classExists = true;
                className = generateClassName();
            } catch (ClassNotFoundException e) {
                classExists = false;
            }
        }
        CtClass createdClass = pool.makeClass(className);
        addFieldsToClass(createdClass, compilationUnit);
        addMethodsToClass(createdClass, compilationUnit);
        try {
            return createdClass.toClass();
        } catch (CannotCompileException exception) {
            throw new CompleXException("Could not compile class", exception);
        }
    }

    /**
     * Adds methods from the compilation unit to the created class.
     * @param createdClass the created class
     * @param compilationUnit the compilation unit to extract methods from
     * @throws CannotCompileException if the created class cannot compile
     */
    private static void addMethodsToClass(CtClass createdClass, CompilationUnit compilationUnit) throws CompleXException {
        for (TypeDeclaration typeDeclaration : compilationUnit.getTypes()) {
            List<BodyDeclaration> members = typeDeclaration.getMembers();
            if (members != null) {
                for (BodyDeclaration member : members) {
                    if (member instanceof MethodDeclaration) {
                        MethodDeclaration method = (MethodDeclaration) member;
                        ClassPool pool = new ClassPool();
                        pool.insertClassPath(new ClassClassPath(ClassCreator.class));
                        CtMethod createdMethod;
                        try {
                            createdMethod = CtMethod.make(method.getDeclarationAsString() + "{" + method.getBody().get().getStatement(0) + "}", createdClass);
                            createdClass.addMethod(createdMethod);
                        } catch (CannotCompileException exception) {
                            throw new CompleXException("Could not create method", exception);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds fields from the compilation unit to the created class.
     * @param createdClass the created class
     * @param compilationUnit the compilation unit to get the fields from
     * @throws CompleXException if the field cannot be created
     */
    private static void addFieldsToClass(CtClass createdClass, CompilationUnit compilationUnit) throws CompleXException {
        try {
            for (TypeDeclaration typeDec : compilationUnit.getTypes()) {
                List<BodyDeclaration> members = typeDec.getMembers();
                if (members != null) {
                    for (BodyDeclaration member : members) {
                        if (member instanceof FieldDeclaration) {
                            FieldDeclaration field = (FieldDeclaration) member;
                            ClassPool pool = new ClassPool();
                            pool.insertClassPath(new ClassClassPath(ClassCreator.class));
                            NodeList<VariableDeclarator> variables = field.getVariables();
                            for (int i = 0; i < variables.size(); i++) {
                                VariableDeclarator variable = variables.get(i);
                                CtClass fieldType = pool.get(variable.getType().toString());
                                CtField createdField = new CtField(fieldType, variable.getNameAsString(), createdClass);
                                createdClass.addField(createdField);
                            }
                        }
                    }
                }
            }
        } catch (CannotCompileException | NotFoundException exception) {
            throw new CompleXException("Could not create field", exception);
        }
    }

    /**
     * Generates a random class name to prevent classes not being created
     * du to existing classes existing.
     * @return the generated class name
     */
    private static String generateClassName() {
        Random random = new Random();
        StringBuilder generatedName = new StringBuilder(8);
        generatedName.append(getRandomLetter());
        while (generatedName.length() <= 8) {
            if ((int) random.nextFloat() * 2 == LETTER) {
                generatedName.append(getRandomLetter());
            } else {
                generatedName.append(getRandomNumber());
            }
        }
        return generatedName.toString();
    }

    /**
     * Returns a random digit.
     * @return a random digit
     */
    private static char getRandomNumber() {
        String numericChars = "1234567890";
        Random random = new Random();
        int character = random.nextInt(numericChars.length());
        return numericChars.charAt(character);
    }

    /**
     * Returns a random letter.
     * @return a random letter
     */
    private static char getRandomLetter() {
        String alphaChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        int character = random.nextInt(alphaChars.length());
        return alphaChars.charAt(character);
    }
}
