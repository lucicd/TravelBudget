package lucicd.travelbudget.validators;

import java.util.HashMap;
import java.util.Map;
import lucicd.travelbudget.forms.CurrencyForm;

public class CurrencyValidator {
    public Map<String, String> validate(CurrencyForm form)
    {
        Map<String, String> errors = new HashMap<>();
        
        String name = form.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Name is required.");
        } else if (name.trim().length() > 3) {
            errors.put("name", "Name length must not exceed 3 characters.");
        }
        
        return errors;
    }
}
