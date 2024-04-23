package com.mydoctor.application.service;

import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;

import java.util.List;

public interface ApplicationService<R,I> {

    R get(I id) throws NotFoundException;
    List<R> getAll();
    // IllegalArgumentException if id is different to Null
    R create(R resource) throws IllegalArgumentException;
    R update(R resource) throws NotFoundException;
    R save(R resource);
    void delete(I id) throws NotFoundException;
    boolean exists(I id);
    void checkExists(I id) throws NotFoundException;
}
