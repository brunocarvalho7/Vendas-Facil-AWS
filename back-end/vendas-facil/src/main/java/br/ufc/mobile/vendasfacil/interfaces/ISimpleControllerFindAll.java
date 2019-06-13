package br.ufc.mobile.vendasfacil.interfaces;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import br.ufc.mobile.vendasfacil.model.Usuario;

public interface ISimpleControllerFindAll<T> extends ISimpleController<T> {
	
	ResponseEntity<T> save(T obj, Usuario usuario);
	ResponseEntity<Collection<T>> findAll();
	
}
