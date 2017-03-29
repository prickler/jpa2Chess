package com.capgemini.chess.service.mapper;

import java.util.List;

public interface Mapper<E, T> {

    T mapE(E entity);

    E mapTO(T to);

    List<T> mapEs(List<E> entities);

    List<E> mapTOs(List<T> tos);
}