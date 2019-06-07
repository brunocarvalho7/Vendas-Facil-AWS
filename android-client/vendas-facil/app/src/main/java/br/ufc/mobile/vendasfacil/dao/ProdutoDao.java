package br.ufc.mobile.vendasfacil.dao;

import br.ufc.mobile.vendasfacil.model.Produto;

public interface ProdutoDao extends GenericDao<Produto> {

    Produto getByBarCode(String barCode);
}
