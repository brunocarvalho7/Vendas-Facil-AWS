package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.repository.ProdutoRepository;
import br.ufc.mobile.vendasfacil.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	ProdutoRepository produtoRepo;
	
	@Override
	public Produto save(Produto produto) {
	    if(produto.getId() == null)
	        produto.setId(UUID.randomUUID().toString());
	    
	    return produtoRepo.save(produto);
	}

	@Override
	public Produto update(Produto produto) {
	    return produtoRepo.save(produto);
	}

	@Override
	public boolean delete(Produto produto) {
	    produtoRepo.delete(produto);
	    
	    if(produtoRepo.findById(produto.getId()) != null)
	        return true;
	    
	    return false;
	}

	@Override
	public List<Produto> findAll() {
	    return produtoRepo.findAll();
	}

	@Override
	public Optional<Produto> findById(String id) {
	    return produtoRepo.findById(id);
	}

	@Override
	public Optional<Produto> findByCodBarras(String codBarras) {
		return produtoRepo.findByCodBarras(codBarras);
	}

}
