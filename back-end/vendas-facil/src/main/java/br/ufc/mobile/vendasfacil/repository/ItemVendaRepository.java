package br.ufc.mobile.vendasfacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Usuario;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, String>{

	@Query("SELECT i from ItemVenda i inner join Venda v on(i.venda = v) where v.vendedor = :vendedor")
	List<ItemVenda> findAllByUsuario(Usuario vendedor);

}
