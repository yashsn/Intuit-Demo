package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;

import java.util.List;

public interface CacheService<T> {
    public void initialize(int topN, List<T> data) throws CacheInitializationException;
    public void addtoCache(T data) throws CacheUpdateFailureException;
    public List<T> getTopNplayers();
}
