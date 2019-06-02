package br.ufc.mobile.vendasfacil.interfaces;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

public interface ISimpleController<T> {
	
	ResponseEntity<T> save(T obj);
	ResponseEntity<T> update(T obj, T objAtualizar);
	ResponseEntity<Collection<T>> findAll();
	ResponseEntity<T> findById(T obj);
	ResponseEntity<Boolean> delete(T obj);
	
}
