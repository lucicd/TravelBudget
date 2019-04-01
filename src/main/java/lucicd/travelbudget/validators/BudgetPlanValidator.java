package lucicd.travelbudget.validators;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lucicd.travelbudget.forms.BudgetPlanForm;

public class BudgetPlanValidator {
    public Map<String, String> validate(BudgetPlanForm form)
    {
        Map<String, String> errors = new HashMap<>();
        
        String availableBudget = form.getAvailableBudget();
        if (availableBudget == null || availableBudget.trim().length() == 0) {
            errors.put("availableBudget", "Available budget is required.");
        } else {
            try {
                BigDecimal x = new BigDecimal(availableBudget);
                if (x.compareTo(new BigDecimal(0)) != 1) {
                    errors.put("availableBudget", "Available budget must be positive.");
                }
            } catch(NumberFormatException ex) {
                errors.put("availableBudget", "Available budget must be a number.");
            }
        }
        
        String travelDate = form.getTravelDate();
        if (travelDate == null || travelDate.trim().length() == 0) {
            errors.put("travelDate", "Travel date is required.");
        } else {
            try {
                DateFormat df = new SimpleDateFormat("dd-MMM-yy", Locale.US);
                Date x = df.parse(travelDate);
            } catch (ParseException ex) {
                errors.put("travelDate", "Travel date is in the wrong format.");
            }
        }
        
        String travelDestination = form.getTravelDestination();
        if (travelDestination == null || travelDestination.trim().isEmpty()) {
            errors.put("travelDestination", "Travel destination is required.");
        } else if (travelDestination.trim().length() < 2) {
            errors.put("travelDestination", "Travel destination length must bet at least 2 characters.");
        } else if (travelDestination.trim().length() > 255) {
            errors.put("travelDestination", "Travel destination length must not exceed 255 characters.");
        }
        
        String currencyId = form.getCurrencyId();
        if (currencyId == null || currencyId.trim().length() == 0) {
            errors.put("currencyId", "Currency is required.");
        }
        
        return errors;
    }
}