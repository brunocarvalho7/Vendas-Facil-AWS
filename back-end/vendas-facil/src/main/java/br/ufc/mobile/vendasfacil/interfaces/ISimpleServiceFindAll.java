package br.ufc.mobile.vendasfacil.interfaces;

import java.util.List;

public interface ISimpleServiceFindAll<T> extends ISimpleService<T> {

	List<T> findAll();
	
}
