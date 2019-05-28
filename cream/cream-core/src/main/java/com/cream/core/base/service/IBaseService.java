/**
 * 
 */
package com.cream.core.base.service;

import com.cream.core.base.repository.IBaseRepository;

/**
 * @author cream
 *
 */
public interface IBaseService<T, ID> {

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    IBaseRepository<T, ID> getBaseRepository();
}
