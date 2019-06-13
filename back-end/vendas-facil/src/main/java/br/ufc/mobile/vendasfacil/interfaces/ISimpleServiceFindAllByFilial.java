package br.ufc.mobile.vendasfacil.interfaces;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Filial;

public interface ISimpleServiceFindAllByFilial<T> extends ISimpleService<T> {

	List<T> findAll(Filial filial);
	
}
