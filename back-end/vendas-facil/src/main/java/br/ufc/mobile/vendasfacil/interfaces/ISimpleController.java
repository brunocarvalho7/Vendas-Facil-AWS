package br.ufc.mobile.vendasfacil.interfaces;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import br.ufc.mobile.vendasfacil.model.Usuario;

public interface ISimpleController<T> {
	
	ResponseEntity<T> save(T obj, Usuario usuario);
	ResponseEntity<T> update(T obj, T objAtualizar);
	ResponseEntity<Collection<T>> findAll(Usuario usuario);
	ResponseEntity<T> findById(T obj);
	ResponseEntity<Boolean> delete(T obj);
	
}
