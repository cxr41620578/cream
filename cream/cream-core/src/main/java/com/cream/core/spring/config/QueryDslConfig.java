/**
 * 
 */
package com.cream.core.spring.config;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author cream
 *
 */
@Configuration
public class QueryDslConfig {
	
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}
