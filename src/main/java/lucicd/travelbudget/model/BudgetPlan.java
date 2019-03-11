/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucicd.travelbudget.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Drazen
 */
@Entity
@Table(name = "budget_plans")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BudgetPlan.findAll", query = "SELECT b FROM BudgetPlan b"),
    @NamedQuery(name = "BudgetPlan.findById", query = "SELECT b FROM BudgetPlan b WHERE b.id = :id"),
    @NamedQuery(name = "BudgetPlan.findByTravelDate", query = "SELECT b FROM BudgetPlan b WHERE b.travelDate = :travelDate"),
    @NamedQuery(name = "BudgetPlan.findByTravelDestination", query = "SELECT b FROM BudgetPlan b WHERE b.travelDestination = :travelDestination"),
    @NamedQuery(name = "BudgetPlan.findByAvailableBudget", query = "SELECT b FROM BudgetPlan b WHERE b.availableBudget = :availableBudget"),
    @NamedQuery(name = "BudgetPlan.findByComments", query = "SELECT b FROM BudgetPlan b WHERE b.comments = :comments")})
public class BudgetPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "travel_date")
    @Temporal(TemporalType.DATE)
    private Date travelDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "travel_destination")
    private String travelDestination;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "available_budget")
    private BigDecimal availableBudget;
    @Size(max = 1024)
    @Column(name = "comments")
    private String comments;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Currency currencyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetPlanId")
    private Collection<BudgetPlanItem> budgetPlanItemCollection;

    public BudgetPlan() {
    }

    public BudgetPlan(Integer id) {
        this.id = id;
    }

    public BudgetPlan(Integer id, Date travelDate, String travelDestination, BigDecimal availableBudget) {
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

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    @XmlTransient
    public Collection<BudgetPlanItem> getBudgetPlanItemCollection() {
        return budgetPlanItemCollection;
    }

    public void setBudgetPlanItemCollection(Collection<BudgetPlanItem> budgetPlanItemCollection) {
        this.budgetPlanItemCollection = budgetPlanItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetPlan)) {
            return false;
        }
        BudgetPlan other = (BudgetPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lucicd.travelbudget.model.BudgetPlan[ id=" + id + " ]";
    }
    
}
