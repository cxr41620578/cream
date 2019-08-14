/**
 * 
 */
package com.cream.core.base.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.cream.core.base.entity.Entity;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author cream
 *
 */
@NoRepositoryBean
public interface IBaseRepository<T extends Entity, ID extends Serializable> extends JpaRepository<T, ID> {

    JPAQueryFactory getJPAQueryFactory();
}
