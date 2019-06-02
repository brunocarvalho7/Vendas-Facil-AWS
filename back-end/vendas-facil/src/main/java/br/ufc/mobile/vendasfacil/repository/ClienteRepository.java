package br.ufc.mobile.vendasfacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{

}
