package br.ufc.mobile.vendasfacil.service;

import java.util.Date;
import java.util.List;

import br.ufc.mobile.vendasfacil.interfaces.ISimpleServiceFindAllByFilialAndUsuario;
import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.Venda;

public interface VendaService extends ISimpleServiceFindAllByFilialAndUsuario<Venda>{

	ReportVendaDTO reportByDia(Usuario usuario, Filial filial);
	
	ReportVendaDTO reportByDia(Date data, Usuario usuario, Filial filial);

	List<ReportVendaDTO> reportBySemana(Usuario usuario, Filial filial);
	
	ReportVendaDTO reportByMes(Usuario usuario, Filial filial);
}
