package lucicd.travelbudget.validators;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lucicd.travelbudget.forms.SettingForm;

public class SettingValidator {
    public Map<String, String> validate(SettingForm form)
    {
        Map<String, String> errors = new HashMap<>();
        
        String name = form.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Name is required.");
        } else if (name.trim().length() < 2) {
            errors.put("name", "Name length must bet at least 2 characters.");
        } else if (name.trim().length() > 255) {
            errors.put("name", "Name length must not exceed 255 characters.");
        }
        
        String numberValue = form.getNumberValue();
        if (numberValue != null && numberValue.trim().length() > 0)
        {
            try {
                BigDecimal x = new BigDecimal(numberValue);
            } catch(NumberFormatException ex) {
                errors.put("numberValue", "Number value is not numeric.");
            }
        }
        return errors;
    }
}
