package com.capgemini.chess.dataaccess.dao;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<E, T, K extends Serializable> {

	T save(T to);

	T getOne(K id);

	T findOne(K id);

	List<T> findAll();

	T update(T to);

	void delete(T to);

	void delete(K id);

	void deleteAll();

	long count();

	boolean exists(K id);
}