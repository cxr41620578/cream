/**
 * 
 */
package com.cream.core.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cream.core.base.repository.IBaseRepository;
import com.cream.core.base.service.IBaseService;

/**
 * @author cream
 *
 */
public class BaseServiceImpl<R extends IBaseRepository<T, ID>, T, ID> implements IBaseService<T, ID> {

    @Autowired
    protected R baseRepository;

    @Override
    public R getBaseRepository() {
        return baseRepository;
    }
}
