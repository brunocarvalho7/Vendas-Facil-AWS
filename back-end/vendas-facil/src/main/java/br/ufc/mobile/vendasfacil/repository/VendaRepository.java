package br.ufc.mobile.vendasfacil.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, String>{

	@Query("select new br.ufc.mobile.vendasfacil.model.ReportVendaDTO(cast(COUNT(v.id) as int), cast(v.data as date) as dateOnly, SUM(v.total))"
			+ " from Venda v where cast(v.data as date) = :dataAtual and v.vendedor = :usuario and v.filial = :filial"
			+ " group by cast(v.data as date)")
	ReportVendaDTO reportByDia(Date dataAtual, Usuario usuario, Filial filial);

	@Query("select new br.ufc.mobile.vendasfacil.model.ReportVendaDTO(cast(COUNT(v.id) as int), date_trunc('month', v.data), SUM(v.total))"
			+ " from Venda v where cast(v.data as date) >= :inicioMes and v.vendedor = :usuario and v.filial = :filial"
			+ " group by date_trunc('month', v.data)")
	ReportVendaDTO reportByMes(Date inicioMes, Usuario usuario, Filial filial);

	@Query("select new br.ufc.mobile.vendasfacil.model.ReportVendaDTO(cast(COUNT(v.id) as int), cast(v.data as date) as dateOnly, SUM(v.total))"
			+ " from Venda v where cast(v.data as date) in :semanaAnterior and v.vendedor = :usuario and v.filial = :filial "
			+ " group by cast(v.data as date) order by cast(v.data as date)")
	List<ReportVendaDTO> reportBySemana(List<Date> semanaAnterior, Usuario usuario, Filial filial);

	List<Venda> findAllByVendedorAndFilial(Usuario usuario, Filial filial);

	
	
}
