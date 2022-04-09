package com.cms.config;

import java.util.Collection;
import java.util.Collections;

public class PaginationT<E> {
    long total = 0L;
    Collection<E> elements;

    public PaginationT() {
    }

    public PaginationT<E> setItems(Collection<E> elements) {
        this.elements = (Collection)(elements == null ? Collections.emptyList() : elements);
        return this;
    }

    public Collection<E> getItems() {
        return this.elements;
    }

    public PaginationT<E> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getTotal() {
        return this.total == 0L ? (long)this.elements.size() : this.total;
    }
}