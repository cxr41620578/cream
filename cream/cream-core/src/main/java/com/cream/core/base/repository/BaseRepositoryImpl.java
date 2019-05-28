/**
 * 
 */
package com.cream.core.base.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.cream.core.base.entity.Entity;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author cream
 *
 */
public class BaseRepositoryImpl<T extends Entity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements IBaseRepository<T, ID> {

    private JPAQueryFactory jpaQueryFactory;
    
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(domainClass, em);
        this.jpaQueryFactory = jpaQueryFactory;
    }
    
    @Override
    public JPAQueryFactory getJPAQueryFactory() {
        return jpaQueryFactory;
    }
}
