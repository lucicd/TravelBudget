/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucicd.travelbudget.exceptions;

/**
 *
 * @author aed20672
 */
public class ModelException extends Exception{
    public ModelException(String errorMessage)
    {
        super(errorMessage);
    }
}
