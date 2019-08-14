/**
 * 
 */
package com.cream.core.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cream.core.base.entity.Entity;
import com.cream.core.base.repository.IBaseRepository;
import com.cream.core.base.service.IBaseService;

/**
 * @author cream
 *
 */
public class BaseServiceImpl<R extends IBaseRepository<T, ID>, T extends Entity, ID extends Serializable> implements IBaseService<T, ID> {

    @Autowired
    protected R baseRepository;

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public <S extends T> S save(S entity) {
        return baseRepository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return baseRepository.existsById(id);
    }

    @Override
    public long count() {
        return baseRepository.count();
    }

    @Override
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        baseRepository.delete(entity);        
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        baseRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        baseRepository.deleteAll();
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return baseRepository.findOne(example);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return baseRepository.findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return baseRepository.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return baseRepository.exists(example);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return baseRepository.findAllById(ids);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return baseRepository.saveAll(entities);
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        baseRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        baseRepository.deleteAllInBatch();
    }

    @Override
    public T getOne(ID id) {
        return baseRepository.getOne(id);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return baseRepository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return baseRepository.findAll(example, sort);
    }

    @Override
    public R getBaseRepository() {
        return baseRepository;
    }
}
