package com.capgemini.chess.dataaccess.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import com.capgemini.chess.dataaccess.dao.AbstractDao;
import com.capgemini.chess.service.mapper.Mapper;

@Transactional(Transactional.TxType.SUPPORTS)
public abstract class AbstractDaoImpl<E, T, K extends Serializable> implements AbstractDao<E, T, K> {

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<E> domainClass;

	@Override
	public T save(T to) {
		E entity = getMapper().mapTO(to);
		entityManager.persist(entity);
		return getMapper().mapE(entity);
	}

	@Override
	public T getOne(K id) {
		return getMapper().mapE(entityManager.getReference(getDomainClass(), id));
	}

	@Override
	public T findOne(K id) {
		return getMapper().mapE(entityManager.find(getDomainClass(), id));
	}

	@Override
	public List<T> findAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = builder.createQuery(getDomainClass());
		criteriaQuery.from(getDomainClass());
		TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
		return getMapper().mapEs(query.getResultList());
	}

	@Override
	public T update(T to) {
		E entity = getMapper().mapTO(to);
		return getMapper().mapE(entityManager.merge(entity));

	}

	@Override
	public void delete(T to) {
		E entity = getMapper().mapTO(to);
		entityManager.remove(entity);
	}

	@Override
	public void delete(K id) {
		entityManager.remove(getOne(id));
	}

	@Override
	public void deleteAll() {
		entityManager.createQuery("delete " + getDomainClassName()).executeUpdate();
	}

	@Override
	public long count() {
		return (long) entityManager.createQuery("Select count(*) from " + getDomainClassName()).getSingleResult();
	}

	@Override
	public boolean exists(K id) {
		return findOne(id) != null;
	}

	@SuppressWarnings("unchecked")
	protected Class<E> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			domainClass = (Class<E>) type.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	protected String getDomainClassName() {
		return getDomainClass().getName();
	}

	protected abstract Mapper<E, T> getMapper();
}