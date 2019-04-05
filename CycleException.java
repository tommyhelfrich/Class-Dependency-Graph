/*
 *File Name: CycleException.java
 *Author: Thomas Helfrich
 * Date: March 09, 2019
 * Purpose: Create and define the checked exception class for handling
 * cases where a cycle occurs.
 */
package dependencyproject;

public class CycleException extends Exception {
    private String cycle;
    public CycleException(String cycleName){
        cycle = cycleName;
    }

    public String getCycle(){
        return cycle;
    }
}
