package com.otb.githubtracker.common.base;

/**
 * Created by Mohit Rajput on 13/08/22.
 * Maps one entity of one layer to another entity of different layer
 */
public interface Mapper<E, T> {
    T mapFrom(E from);
}
