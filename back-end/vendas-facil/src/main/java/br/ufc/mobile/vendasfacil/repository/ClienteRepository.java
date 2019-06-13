package br.ufc.mobile.vendasfacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.Filial;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{

	List<Cliente> findAllByFilial(Filial filial);
	
}
