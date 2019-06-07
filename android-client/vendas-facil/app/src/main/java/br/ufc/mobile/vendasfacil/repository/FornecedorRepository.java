package br.ufc.mobile.vendasfacil.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import br.ufc.mobile.vendasfacil.model.Fornecedor;

public class FornecedorRepository {

    private Map<String, Fornecedor> fornecedores;
    private static FornecedorRepository instance;

    private FornecedorRepository(){
        fornecedores = new TreeMap<>();
        mock();
    }

    public static FornecedorRepository getInstance(){
        if(instance == null)
            instance = new FornecedorRepository();

        return instance;
    }

    public boolean save(Fornecedor obj) {
        obj.setId(UUID.randomUUID().toString());
        return fornecedores.put(obj.getId(), obj) != null ? true : false;
    }

    public Fornecedor remove(Integer id) {
        return fornecedores.remove(id);
    }

    public List<Fornecedor> getAll() {
        return new ArrayList<>(fornecedores.values());
    }

    public Fornecedor getById(Integer id) {
        return fornecedores.get(id);
    }

    public boolean update(Fornecedor obj) {
        return fornecedores.put(obj.getId(), obj) != null ? true : false;
    }

    private void mock(){
       save(new Fornecedor("1","Pinheiro", "12345678", "Fernando"));
       save(new Fornecedor("2","São Geraldo", "88877554", "Joaquim"));
       save(new Fornecedor("3","M Dias Branco", "44569874", "José"));
    }
}
