/**
 * 
 */
package com.cream.core.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author cream
 *
 */
@NoRepositoryBean
public interface IBaseRepository<T, ID> extends JpaRepository<T, ID> {

    JPAQueryFactory getJPAQueryFactory();
}
