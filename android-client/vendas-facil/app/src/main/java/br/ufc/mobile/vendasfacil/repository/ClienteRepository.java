package br.ufc.mobile.vendasfacil.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.enums.Unidade;

public class ClienteRepository {

    public static int GEN_ID = 0;

    private Map<String, Cliente> clientes;
    private static ClienteRepository instance;

    private ClienteRepository(){
        clientes = new TreeMap<>();
        mock();
    }

    public static ClienteRepository getInstance(){
        if(instance == null)
            instance = new ClienteRepository();

        return instance;
    }

    public boolean save(Cliente obj) {
        obj.setId(UUID.randomUUID().toString());
        return clientes.put(obj.getId(), obj) != null ? true : false;
    }

    public Cliente remove(Integer id) {
        return clientes.remove(id);
    }

    public List<Cliente> getAll() {
        return new ArrayList<>(clientes.values());
    }

    public Cliente getById(Integer id) {
        return clientes.get(id);
    }

    public boolean update(Cliente obj) {
        return clientes.put(obj.getId(), obj) != null ? true : false;
    }

    private void mock(){
        save(new Cliente("1","Cliente padrão", "Endereço A", "12345678"));
        save(new Cliente("2","Bruno Carvalho", "Endereço B", "95654785"));
        save(new Cliente("3","Maike Bezerra", "Endereço C", "40028922"));
    }
}
