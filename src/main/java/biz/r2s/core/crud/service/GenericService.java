package biz.r2s.core.crud.service;

import java.util.List;

public interface GenericService<T,V> {
	T save(T t);

	T edit(T t);

	void delete(T t);

	List<T> list();
	
	T show(V id);
}
