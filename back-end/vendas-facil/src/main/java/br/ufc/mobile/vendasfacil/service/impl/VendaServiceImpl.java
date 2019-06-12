package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Venda> findAll(Usuario usuario) {
	    return vendaRepo.findAllByVendedor(usuario);
	}

	@Override
	public Optional<Venda> findById(String id) {
	    return vendaRepo.findById(id);
	}

	@Override
	public ReportVendaDTO reportByDia(Usuario usuario) {
		return vendaRepo.reportByDia(ReportsUtils.getDataAtual(), usuario);
	}

	@Override
	public List<ReportVendaDTO> reportBySemana(Usuario usuario) {
		return vendaRepo.reportBySemana(ReportsUtils.getSemanaAnterior(), usuario);
	}

	@Override
	public List<ReportVendaDTO> reportByMes(Usuario usuario) {
		return vendaRepo.reportByMes(ReportsUtils.getInicioMes(), usuario);
	}

}
