package br.ufc.mobile.vendasfacil.dao;

import br.ufc.mobile.vendasfacil.model.Cliente;

public interface ClienteDao extends GenericDao<Cliente> {

    Cliente getClientePadrao();
}
