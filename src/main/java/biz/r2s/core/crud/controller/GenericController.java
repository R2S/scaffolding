package biz.r2s.core.crud.controller;

public interface GenericController<T,V,R> {
	public R listObject();
	public R inserirObject(T t);
	public R editObject(T t, V id);
	public R excluirObject(V id);
	public R showObject(V id);
}
