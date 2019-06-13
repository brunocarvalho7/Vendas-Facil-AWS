package br.ufc.mobile.vendasfacil.interfaces;

import java.util.Optional;

public interface ISimpleService<T> {

	T save(T obj);
	T update(T obj);
	boolean delete(T obj);
	Optional<T> findById(String id);
	
}
