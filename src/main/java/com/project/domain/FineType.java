package com.project.domain;

public enum FineType {
    /**
     * Fine for overdue books (calculated daily)
     */
    OVERDUE,

    /**
     * Fine for damaged books
     */
    DAMAGE,

    /**
     * Fine for lost books (replacement cost)
     */
    LOSS,

    /**
     * Processing or administrative fees
     */
    PROCESSING
}
