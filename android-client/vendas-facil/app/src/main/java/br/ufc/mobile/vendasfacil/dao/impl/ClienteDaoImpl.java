package br.ufc.mobile.vendasfacil.dao.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.repository.ClienteRepository;
import br.ufc.mobile.vendasfacil.repository.ProdutoRepository;
import br.ufc.mobile.vendasfacil.dao.ClienteDao;
import br.ufc.mobile.vendasfacil.model.Produto;

public class ClienteDaoImpl implements ClienteDao {

    private static final String REFERENCE = "clientes";

    private List<Cliente> clientes;
    private DatabaseReference mDatabase;
    private DataStatus listener;

    public ClienteDaoImpl(final DataStatus listener) {

        if(listener != null){
            this.listener = listener;
        }

        clientes = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(REFERENCE);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientes.clear();
                for(DataSnapshot cliente: dataSnapshot.getChildren()){
                    Cliente c = cliente.getValue(Cliente.class);
                    clientes.add(c);
                }

                if(listener != null)
                    listener.DataIsLoaded(clientes);

                Log.i(this.getClass().getSimpleName(), "Carregou a lista de clientes: " + clientes.toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(this.getClass().getSimpleName(), "onCancelled: " + databaseError.toString() );
            }
        });
    }

    @Override
    public void save(Cliente obj) {
        obj.setId(this.getNewId());

        mDatabase
                .child(obj.getId())
                .setValue(obj);
    }

    @Override
    public boolean remove(Cliente obj) {
        try{
            mDatabase
                    .child(obj.getId())
                    .removeValue();
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public List<Cliente> getAll() {
        return clientes;
    }

    @Override
    public boolean update(Cliente obj) {
        try{
            mDatabase
                    .child(obj.getId())
                    .setValue(obj);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public String getNewId() {
        return mDatabase
                .push()
                .getKey();
    }

    @Override
    public Cliente getClientePadrao() {
        if(clientes.size() > 0){
            return clientes.get(0);
        }

        return null;
    }
}
