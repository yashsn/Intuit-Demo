package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;

import java.util.List;

public interface CacheService<T> {
    void initialize(int topN, List<T> data) throws CacheInitializationException;
    void addtoCache(T data) throws CacheUpdateFailureException;
    List<T> getTopNplayers();
}
