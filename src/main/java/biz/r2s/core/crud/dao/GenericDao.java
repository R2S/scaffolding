package biz.r2s.core.crud.dao;

import java.util.List;

public interface GenericDao<T,V> {
	void insert(T t);
	void update(T t);
	void delete(T t);
	List<T> listAll();
	T get(V id);
}
