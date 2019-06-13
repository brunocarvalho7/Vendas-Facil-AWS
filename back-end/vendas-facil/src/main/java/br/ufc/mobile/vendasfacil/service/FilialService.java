package br.ufc.mobile.vendasfacil.service;

import br.ufc.mobile.vendasfacil.interfaces.ISimpleServiceFindAll;
import br.ufc.mobile.vendasfacil.model.Filial;

public interface FilialService extends ISimpleServiceFindAll<Filial>{

	Filial findByLongitudeAndLatitude(Double latitude, Double longitude);

}
