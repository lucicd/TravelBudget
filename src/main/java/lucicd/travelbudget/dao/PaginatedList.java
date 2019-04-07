package lucicd.travelbudget.dao;

import java.math.BigInteger;
import java.util.List;

public class PaginatedList {
    private List<Object> list;
    private Integer numRows;
    private Integer currentPage;
    private Integer numPages;
    private final Integer pageSize;

    public PaginatedList(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Integer getNumRows() {
        return numRows;
    }
    
    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
        if (numRows == 0) {
            this.numPages = 0;
        } else {
            this.numPages = (numRows - 1) / this.pageSize + 1;
        }
    }

    public void setNumRows(BigInteger numRows) {
        setNumRows(numRows.intValue());
    }
    
    public boolean hasPrevPage() {
        return this.currentPage > 1;
    }
    
    public boolean hasNextPage() {
        return this.currentPage < this.numPages;
    }
}
