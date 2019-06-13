package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Categoria;
import br.ufc.mobile.vendasfacil.repository.CategoriaRepository;
import br.ufc.mobile.vendasfacil.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepo;
	
	@Override
	public Categoria save(Categoria categoria) {
		if(categoria.getId() == null)
			categoria.setId(UUID.randomUUID().toString());
		
		return categoriaRepo.save(categoria);
	}

	@Override
	public Categoria update(Categoria categoria) {
		return categoriaRepo.save(categoria);
	}

	@Override
	public boolean delete(Categoria categoria) {
		categoriaRepo.delete(categoria);
		
		if(categoriaRepo.findById(categoria.getId()) != null)
			return true;
		
		return false;
	}

	@Override
	public List<Categoria> findAll() {
		return categoriaRepo.findAll();
	}

	@Override
	public Optional<Categoria> findById(String id) {
		return categoriaRepo.findById(id);
	}

}
