/*
*File Name: ClassException.java
*Author: Thomas Helfrich
* Date: March 09, 2019
* Purpose: Create and define the checked exception class for handling
* invalid class name.
 */
package dependencyproject;

public class ClassException extends RuntimeException {
    private String invalidClass = "";

    public ClassException(){
    }

    public ClassException(String className){
        invalidClass = className;
    }

    public String getClassName(){
        return invalidClass;
    }

}
