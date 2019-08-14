/**
 * 
 */
package com.cream.core.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cream.core.base.entity.Entity;
import com.cream.core.base.repository.IBaseRepository;

/**
 * @author cream
 *
 */
public interface IBaseService<T extends Entity, ID extends Serializable> {

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    IBaseRepository<T, ID> getBaseRepository();

    Page<T> findAll(Pageable pageable);

    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();

    <S extends T> Optional<S> findOne(Example<S> example);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);

    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAllById(Iterable<ID> ids);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void deleteInBatch(Iterable<T> entities);

    void deleteAllInBatch();

    T getOne(ID id);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);
}
