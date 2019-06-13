package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.repository.FilialRepository;
import br.ufc.mobile.vendasfacil.service.FilialService;

@Service
public class FilialServiceImpl implements FilialService{

    @Autowired
	FilialRepository filialRepo;

	@Override
	public Filial save(Filial filial) {	
		return filialRepo.save(filial);
	}

	@Override
	public Filial update(Filial filial) {
		return filialRepo.save(filial);
	}

	@Override
	public boolean delete(Filial filial) {
		filialRepo.delete(filial);
		
		if(filialRepo.findById(filial.getId()) != null)
			return true;
		
		return false;
	}

	@Override
	public List<Filial> findAll() {
		return filialRepo.findAll();
	}

	@Override
	public Optional<Filial> findById(String id) {
		return filialRepo.findById(Integer.parseInt(id));
	}

	@Override
	public Filial findByLongitudeAndLatitude(Double latitude, Double longitude) {
		return filialRepo.findByLongitudeAndLatitude(longitude, latitude);
	}

}
