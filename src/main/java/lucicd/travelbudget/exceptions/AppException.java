package lucicd.travelbudget.exceptions;

public class AppException extends Exception{
    public AppException(String errorMessage)
    {
        super(errorMessage);
    }
}
