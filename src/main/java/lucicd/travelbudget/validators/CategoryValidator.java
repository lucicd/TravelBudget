package lucicd.travelbudget.validators;

import java.util.HashMap;
import java.util.Map;
import lucicd.travelbudget.forms.CategoryForm;

public class CategoryValidator {
    public Map<String, String> validate(CategoryForm form)
    {
        Map<String, String> errors = new HashMap<>();
        
        String description = form.getDescription();
        if (description == null || description.trim().isEmpty()) {
            errors.put("description", "Description is required.");
        } else if (description.trim().length() < 2) {
            errors.put("description", "Description length must bet at least 2 characters.");
        } else if (description.trim().length() > 255) {
            errors.put("description", "Description length must not exceed 255 characters.");
        }
        
        return errors;
    }
}
