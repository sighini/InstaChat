package com.sighini.instachat.dto;

public class Pagination {

    private Integer pageCount;
    private Integer currentPage;
    private Boolean hasNextPage;
    private Boolean hasPrevPage;
    private Integer count;
    private Object limit;

    /**
     * @return The pageCount
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount The page_count
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage The current_page
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return The hasNextPage
     */
    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    /**
     * @param hasNextPage The has_next_page
     */
    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    /**
     * @return The hasPrevPage
     */
    public Boolean getHasPrevPage() {
        return hasPrevPage;
    }

    /**
     * @param hasPrevPage The has_prev_page
     */
    public void setHasPrevPage(Boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The limit
     */
    public Object getLimit() {
        return limit;
    }

    /**
     * @param limit The limit
     */
    public void setLimit(Object limit) {
        this.limit = limit;
    }

}
