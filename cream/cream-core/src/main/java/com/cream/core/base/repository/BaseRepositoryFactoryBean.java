/**
 * 
 */
package com.cream.core.base.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.cream.core.base.entity.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author cream
 *
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T extends BaseEntity, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {
    
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    
    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory(em);
    }

    // 创建一个内部类，该类不用在外部访问
    private class BaseRepositoryFactory extends JpaRepositoryFactory {

        private final EntityManager em;

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

        // 设置具体的实现类是BaseRepositoryImpl
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return new BaseRepositoryImpl((Class<T>) information.getDomainType(), em, jpaQueryFactory);
        }

        // 设置具体的实现类的class
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }
}
