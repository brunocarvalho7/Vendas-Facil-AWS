package br.ufc.mobile.vendasfacil.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.Venda;

public class VendaRepository {

    private Map<String, Venda> vendas;
    private static VendaRepository instance;

    private VendaRepository(){
        vendas = new TreeMap<>();
    }

    public static VendaRepository getInstance(){
        if(instance == null)
            instance = new VendaRepository();

        return instance;
    }

    public boolean save(Venda obj) {
        obj.setId(UUID.randomUUID().toString());
        return vendas.put(obj.getId(), obj) != null ? true : false;
    }

    public Venda remove(Integer id) {
        return vendas.remove(id);
    }

    public List<Venda> getAll() {
        return new ArrayList<>(vendas.values());
    }

    public Venda getById(String id) {
        return vendas.get(id);
    }

    public boolean update(Venda obj) {
        return vendas.put(obj.getId(), obj) != null ? true : false;
    }
}
