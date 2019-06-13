package br.ufc.mobile.vendasfacil.interfaces;

import org.springframework.http.ResponseEntity;

public interface ISimpleController<T> {
	
	ResponseEntity<T> update(T obj, T objAtualizar);
	ResponseEntity<T> findById(T obj);
	ResponseEntity<Boolean> delete(T obj);
	
}
