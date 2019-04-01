package lucicd.travelbudget.forms;

import java.math.BigDecimal;
import java.util.List;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.beans.BudgetPlan;

public class BudgetPlanForm {
    private String id;
    private String travelDate;
    private String travelDestination;
    private String availableBudget;
    private String currencyId;
    private String comments;
    private String currencyName;
    private List<Currency> currencies;
    
    public BudgetPlanForm() {}

    public BudgetPlanForm(BudgetPlan rec) {
        this.id = rec.getId().toString();

        BigDecimal rate = rec.getAvailableBudget();
        if (rate != null) {
            this.availableBudget = rate.toString();
        }

        Integer myId = rec.getCurrencyId();
        if (myId != null) {
            this.currencyId = myId.toString();
        }
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailableBudget() {
        return availableBudget;
    }

    public void setAvailableBudget(String availableBudget) {
        this.availableBudget = availableBudget;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getTravelDestination() {
        return travelDestination;
    }

    public void setTravelDestination(String travelDestination) {
        this.travelDestination = travelDestination;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }
}