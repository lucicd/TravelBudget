package lucicd.travelbudget.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.beans.BudgetPlanItem;
import lucicd.travelbudget.beans.Category;

public class BudgetPlanItemForm {
    private String id;
    private String itemDescription;
    private String url;
    private String startDate;
    private String completionDate;
    private String costInCurrency;
    private String status;
    private String comments;
    private String budgetPlanId;
    private String categoryId;
    private String currencyId;
    private String currency;
    private String exchangeRate;
    private String category;
    private List<Currency> currencies;
    private List<Category> categories;
    private List<String> statuses;
    private BudgetPlanForm budgetPlan;
    
    public BudgetPlanItemForm() {}

    public BudgetPlanItemForm(BudgetPlanItem rec) {
        Integer idTmp = rec.getCurrencyId();
        if (idTmp != null) {
            this.currencyId = idTmp.toString();
        }
        
        this.id = rec.getId().toString();
        this.itemDescription = rec.getItemDescription();
        this.url = rec.getUrl();
        
        Date startDateTmp = rec.getStartDate();
        if (startDateTmp != null) {
            this.startDate = startDateTmp.toString();
        }
        
        Date completionDateTmp = rec.getCompletionDate();
        if (completionDateTmp != null) {
            this.completionDate = completionDateTmp.toString();
        }
        
        BigDecimal costInCurrencyTmp = rec.getCostInCurrency();
        if (costInCurrencyTmp != null) {
            this.costInCurrency = costInCurrencyTmp.toString();
        }
        
        this.status = rec.getStatus();
        this.comments = rec.getComments();
        
        Integer budgetPlanIdTmp = rec.getBudgetPlanId();
        if (budgetPlanIdTmp != null) {
            this.budgetPlanId = budgetPlanIdTmp.toString();
        }
        
        Integer categoryIdTmp = rec.getCategoryId();
        if (categoryIdTmp != null) {
            this.categoryId = categoryIdTmp.toString();
        }
        
        Integer currencyIdTmp = rec.getCategoryId();
        if (currencyIdTmp != null) {
            this.currencyId = currencyIdTmp.toString();
        }
        
        BigDecimal exchangeRateTmp = rec.getExchangeRate();
        if (exchangeRateTmp != null) {
            this.exchangeRate = exchangeRateTmp.toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getCostInCurrency() {
        return costInCurrency;
    }

    public void setCostInCurrency(String costInCurrency) {
        this.costInCurrency = costInCurrency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBudgetPlanId() {
        return budgetPlanId;
    }

    public void setBudgetPlanId(String budgetPlanId) {
        this.budgetPlanId = budgetPlanId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCost() {
        if (this.costInCurrency != null && this.exchangeRate != null) {
            BigDecimal a = new BigDecimal(this.costInCurrency);
            BigDecimal b = new BigDecimal(this.exchangeRate);
            BigDecimal c = a.multiply(b);
            return c.toString();
        } else {
            return null;
        }
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public BudgetPlanForm getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(BudgetPlanForm budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public List<String> getStatuses() {
        this.statuses = new ArrayList<>();
        statuses.add("Draft");
        statuses.add("Confirmed");
        statuses.add("Completed");
        statuses.add("Cancelled");
        return statuses;
    }
}