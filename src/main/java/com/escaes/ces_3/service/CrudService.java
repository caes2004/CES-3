package com.escaes.ces_3.service;


public interface CrudService<T, ID> {

    T save(T entity);

    T getById(ID id);

    void deleteById(ID id);


}
