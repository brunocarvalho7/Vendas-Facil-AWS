package br.ufc.mobile.vendasfacil.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.repository.VendaRepository;
import br.ufc.mobile.vendasfacil.service.ItemVendaService;
import br.ufc.mobile.vendasfacil.service.ProdutoService;
import br.ufc.mobile.vendasfacil.service.VendaService;
import br.ufc.mobile.vendasfacil.utils.ReportsUtils;

@Service
public class VendaServiceImpl implements VendaService{

	@Autowired
	VendaRepository vendaRepo;
	
	@Autowired
	ItemVendaService itemVendaService;
	
	@Autowired
	ProdutoService produtoService;
	
	@Override
	public Venda save(Venda venda) {
		if(venda.getId() == null)
	        venda.setId(UUID.randomUUID().toString());
		
		Venda vendaSaved = vendaRepo.save(venda);
		
		for(ItemVenda i : venda.getItens()) {
			i.getProduto().removerEstoque(i.getQtd());
			produtoService.update(i.getProduto());
			
			i.setVenda(vendaSaved);
			vendaSaved.addItem(itemVendaService.save(i));
		}
	 
	    return vendaRepo.save(venda);
	}

	@Override
	public Venda update(Venda venda) {
	    return vendaRepo.save(venda);
	}

	@Override
	public boolean delete(Venda venda) {
		for(ItemVenda i : venda.getItens()) {
			i.getProduto().adicionarEstoque(i.getQtd());
			produtoService.update(i.getProduto());
		}
		
	    vendaRepo.delete(venda);
	    
	    if(vendaRepo.findById(venda.getId()) != null)
	        return true;
	    
	    return false;
	}

	@Override
	public List<Venda> findAll(Filial filial, Usuario usuario) {
	    return vendaRepo.findAllByVendedorAndFilial(usuario, filial);
	}

	@Override
	public Optional<Venda> findById(String id) {
	    return vendaRepo.findById(id);
	}

	@Override
	public ReportVendaDTO reportByDia(Usuario usuario, Filial filial) {
		return reportByDia(ReportsUtils.getDataAtual(), usuario, filial);
	}
	
	@Override
	public ReportVendaDTO reportByDia(Date data, Usuario usuario, Filial filial) {
		ReportVendaDTO report = vendaRepo.reportByDia(data, usuario, filial);
		
		if(report == null)
			report = new ReportVendaDTO(data);
		
		return report;
	}

	@Override
	public List<ReportVendaDTO> reportBySemana(Usuario usuario, Filial filial) {
		List<ReportVendaDTO> reports = new ArrayList<>();
		
		for(Date d: ReportsUtils.getSemanaAnterior())
			reports.add(reportByDia(d, usuario, filial));
		
		return reports;
	}

	@Override
	public ReportVendaDTO reportByMes(Usuario usuario, Filial filial) {
		return vendaRepo.reportByMes(ReportsUtils.getInicioMes(), usuario, filial);
	}

}
