package lucicd.travelbudget.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "budget_plans")
public class BudgetPlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "travel_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date travelDate;
    
    @Column(name = "travel_destination")
    private String travelDestination;
    
    @Column(name = "available_budget")
    private BigDecimal availableBudget;
    
    @Column(name = "comments")
    private String comments;
    
    @Column(name = "currency_id")
    private Integer currencyId;

    public BudgetPlan() {
    }

    public BudgetPlan(Integer id) {
        this.id = id;
    }

    public BudgetPlan(Integer id, Date travelDate, 
            String travelDestination, BigDecimal availableBudget) 
    {
        this.id = id;
        this.travelDate = travelDate;
        this.travelDestination = travelDestination;
        this.availableBudget = availableBudget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getTravelDestination() {
        return travelDestination;
    }

    public void setTravelDestination(String travelDestination) {
        this.travelDestination = travelDestination;
    }

    public BigDecimal getAvailableBudget() {
        return availableBudget;
    }

    public void setAvailableBudget(BigDecimal availableBudget) {
        this.availableBudget = availableBudget;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }
}
