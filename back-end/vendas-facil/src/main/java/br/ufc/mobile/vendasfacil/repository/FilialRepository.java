package br.ufc.mobile.vendasfacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Filial;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Integer>{

	Filial findByLongitudeAndLatitude(Double longitude, Double latitude);

}
