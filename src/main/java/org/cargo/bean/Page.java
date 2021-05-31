package org.cargo.bean;

import java.util.Collections;
import java.util.List;

/**
 * This class is used for pagination.
 *
 * @param <T> Type parameter of items
 */
public class Page<T> {
    private List<T> items;
    private Integer pageNo;
    private Integer pageSize;
    private Integer currentSize;
    private boolean firstPage;
    private boolean lastPage;

    public Page(List<T> items, Integer pageNo, Integer pageSize) {
        this.items = items;
        this.pageNo = pageNo;
        this.pageSize = pageSize;

        currentSize = items.size();
        firstPage = pageNo == 1;
        lastPage = currentSize < pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }

    public static <T> Page<T> empty(){
        return new Page<>(Collections.emptyList(), 1, 1);
    }

}
