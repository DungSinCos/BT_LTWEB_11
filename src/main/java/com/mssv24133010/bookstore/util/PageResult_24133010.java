package com.mssv24133010.bookstore.util;

import java.util.List;

/** Generic holder for one page of results, used by all paginated listings. */
public class PageResult_24133010<T> {
    private List<T> items;
    private int currentPage;   // 1-based
    private int pageSize;
    private long totalItems;
    private int totalPages;

    public PageResult_24133010(List<T> items, int currentPage, int pageSize, long totalItems) {
        this.items = items;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.max(1, Math.ceil((double) totalItems / pageSize));
    }

    public List<T> getItems() { return items; }
    public int getCurrentPage() { return currentPage; }
    public int getPageSize() { return pageSize; }
    public long getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public boolean isHasPrev() { return currentPage > 1; }
    public boolean isHasNext() { return currentPage < totalPages; }
}
