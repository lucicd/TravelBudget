package lucicd.travelbudget.forms;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import lucicd.travelbudget.beans.BudgetPlan;

public class BudgetPlanForm {
    private String id;
    private String travelDate;
    private String travelDestination;
    private String availableBudget;
    private String allocatedBudget;
    private String currencyId;
    private String comments;
    private String currencyName;
    
    public BudgetPlanForm() {}

    public BudgetPlanForm(BudgetPlan rec) {
        this.id = rec.getId().toString();

        BigDecimal budget = rec.getAvailableBudget();
        if (budget != null) {
            this.availableBudget = budget.toString();
        }
        
        this.travelDate = rec.getTravelDate().toString();
        this.travelDestination = rec.getTravelDestination();
        this.comments = rec.getComments();
        
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
    
    public String getFormattedAvailableBudget() {
        String bdStr;
        if (this.availableBudget == null || this.availableBudget.isEmpty()) {
            bdStr = "0";
        } else {
            bdStr = this.availableBudget;
        }
        BigDecimal bd = new BigDecimal(bdStr);
        return new DecimalFormat("#,##0.00").format(bd);
    }

    public String getAllocatedBudget() {
        return allocatedBudget;
    }

    public void setAllocatedBudget(String allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
    }
    
    public String getFormattedAllocatedBudget() {
        String bdStr;
        if (this.allocatedBudget == null || this.allocatedBudget.isEmpty()) {
            bdStr = "0";
        } else {
            bdStr = this.allocatedBudget;
        }
        BigDecimal bd = new BigDecimal(bdStr);
        return new DecimalFormat("#,##0.00").format(bd);
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