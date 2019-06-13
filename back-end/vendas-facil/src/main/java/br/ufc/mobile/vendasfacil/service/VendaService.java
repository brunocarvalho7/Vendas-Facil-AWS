package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.interfaces.ISimpleServiceFindAllByFilialAndUsuario;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.Venda;

public interface VendaService extends ISimpleServiceFindAllByFilialAndUsuario<Venda>{

	ReportVendaDTO reportByDia(Usuario usuario);

	List<ReportVendaDTO> reportBySemana(Usuario usuario);
	
	List<ReportVendaDTO> reportByMes(Usuario usuario);
}
