package lucicd.travelbudget.validators;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lucicd.travelbudget.forms.ExchangeRateForm;

public class ExchangeRateValidator {
    public Map<String, String> validate(ExchangeRateForm form)
    {
        Map<String, String> errors = new HashMap<>();
        
        String currentExchangeRate = form.getCurrentExchangeRate();
        if (currentExchangeRate == null || currentExchangeRate.trim().length() == 0) {
            errors.put("currentExchangeRate", "Exchange rate is required.");
        } else {
            try {
                BigDecimal x = new BigDecimal(currentExchangeRate);
                if (x.compareTo(new BigDecimal(0)) != 1) {
                    errors.put("currentExchangeRate", "Exchange rate must be positive.");
                }
            } catch(NumberFormatException ex) {
                errors.put("currentExchangeRate", "Exchange rate must be a number.");
            }
        }
        
        String currencyId = form.getCurrencyId();
        if (currencyId == null || currencyId.trim().length() == 0) {
            errors.put("currencyId", "Currency is required.");
        }
        
        return errors;
    }
}
