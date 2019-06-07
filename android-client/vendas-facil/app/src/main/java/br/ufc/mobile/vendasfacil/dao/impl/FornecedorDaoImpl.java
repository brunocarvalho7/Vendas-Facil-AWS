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
import br.ufc.mobile.vendasfacil.dao.FornecedorDao;
import br.ufc.mobile.vendasfacil.model.Fornecedor;

public class FornecedorDaoImpl implements FornecedorDao {

    private static final String REFERENCE = "fornecedores";

    private List<Fornecedor> fornecedores;
    private DatabaseReference mDatabase;
    private DataStatus listener;

    public FornecedorDaoImpl(final DataStatus listener) {

        if(listener != null){
            this.listener = listener;
        }

        fornecedores = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(REFERENCE);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fornecedores.clear();
                for(DataSnapshot fornecedor: dataSnapshot.getChildren()){
                    Fornecedor f = fornecedor.getValue(Fornecedor.class);
                    fornecedores.add(f);
                }

                if(listener != null)
                    listener.DataIsLoaded(fornecedores);

                Log.i( this.getClass().getSimpleName(),
                        "Carregou a lista de fornecedores: " + fornecedores.toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(this.getClass().getSimpleName(), "onCancelled: " + databaseError.toString() );
            }
        });
    }

    @Override
    public void save(Fornecedor obj) {
        obj.setId(this.getNewId());

        mDatabase
                .child(obj.getId())
                .setValue(obj);
    }

    @Override
    public boolean remove(Fornecedor obj) {
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
    public List<Fornecedor> getAll() {
        return fornecedores;
    }

    @Override
    public boolean update(Fornecedor obj) {
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
}
