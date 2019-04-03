package lucicd.travelbudget.validators;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lucicd.travelbudget.forms.BudgetPlanItemForm;

public class BudgetPlanItemValidator {
    public Map<String, String> validate(BudgetPlanItemForm form)
    {
        Map<String, String> errors = new HashMap<>();
        
        String id = form.getId();
        if (id != null && id.trim().length() > 0) {
            try {
                Integer x = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                errors.put("id", "ID is not a number.");
            }
        }
        
        String budgetPlanId = form.getBudgetPlanId();
        if (budgetPlanId == null || budgetPlanId.trim().length() == 0) {
            errors.put("budgetPlanId", "budgetPlanId is required.");
        } else {
            try {
                Integer x = Integer.parseInt(budgetPlanId);
            } catch (NumberFormatException ex) {
                errors.put("budgetPlanId", "budgetPlanId is not a number.");
            }
        }
        
        String itemDescription = form.getItemDescription();
        if (itemDescription == null || itemDescription.trim().isEmpty()) {
            errors.put("itemDescription", "Item description is required.");
        } else if (itemDescription.trim().length() < 2) {
            errors.put("itemDescription", "Item description length must bet at least 2 characters.");
        } else if (itemDescription.trim().length() > 255) {
            errors.put("itemDescription", "Item description length must not exceed 255 characters.");
        }
        
        String url = form.getUrl();
        if (url != null && url.trim().length() > 0) {
            try {
                URL u = new URL(url);
                u.toURI();
            } catch (MalformedURLException | URISyntaxException ex) {
                errors.put("url", "Incorrect URL.");
            }
        }
        
        String startDate = form.getStartDate();
        if (startDate == null || startDate.trim().length() == 0) {
            errors.put("startDate", "Start date is required.");
        } else {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                Date x = df.parse(startDate);
            } catch (ParseException ex) {
                errors.put("startDate", "Start date is in the wrong format. " + startDate);
            }
        }
        
        String completionDate = form.getCompletionDate();
        if (completionDate == null || completionDate.trim().length() == 0) {
            errors.put("completionDate", "Completion date is required.");
        } else {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                Date x = df.parse(completionDate);
            } catch (ParseException ex) {
                errors.put("completionDate", 
                        "Completion date is in the wrong format. " 
                        + completionDate);
            }
        }
        
        String costInCurrency = form.getCostInCurrency();
        if (costInCurrency == null || costInCurrency.trim().length() == 0) {
            errors.put("costInCurrency", "Cost in currency is required.");
        } else {
            try {
                BigDecimal x = new BigDecimal(costInCurrency);
                if (x.compareTo(new BigDecimal(0)) != 1) {
                    errors.put("costInCurrency", "Cost in currency must be positive.");
                }
            } catch(NumberFormatException ex) {
                errors.put("costInCurrency", "Cost in currency must be a number.");
            }
        }
        
        String exchangeRate = form.getExchangeRate();
        if (exchangeRate == null || exchangeRate.trim().length() == 0) {
            errors.put("exchangeRate", "Exchange rate is required.");
        } else {
            try {
                BigDecimal x = new BigDecimal(exchangeRate);
                if (x.compareTo(new BigDecimal(0)) != 1) {
                    errors.put("exchangeRate", "Exchange rate must be positive.");
                }
            } catch(NumberFormatException ex) {
                errors.put("exchangeRate", "Exchange rate must be a number.");
            }
        }
        
        String currencyId = form.getCurrencyId();
        if (currencyId == null || currencyId.trim().length() == 0) {
            errors.put("currencyId", "Currency is required.");
        } else {
            try {
                Integer x = Integer.parseInt(currencyId);
            } catch (NumberFormatException ex) {
                errors.put("currencyId", "currencyId is not a number.");
            }
        }
        
        String categoryId = form.getCategoryId();
        if (categoryId == null || categoryId.trim().length() == 0) {
            errors.put("categoryId", "Category is required.");
        } else {
            try {
                Integer x = Integer.parseInt(categoryId);
            } catch (NumberFormatException ex) {
                errors.put("categoryId", "categoryId is not a number.");
            }
        }
        
        String status = form.getStatus();
        if (status == null || status.trim().length() == 0) {
            errors.put("status", "Status is required.");
        }
        
        String comments = form.getComments();
        if (comments != null && comments.trim().length() > 0) {
            if (comments.trim().length() > 1000) {
                errors.put("comments", "Comments length must not exceed 1000 characters.");
            }
        }
        
        return errors;
    }
}