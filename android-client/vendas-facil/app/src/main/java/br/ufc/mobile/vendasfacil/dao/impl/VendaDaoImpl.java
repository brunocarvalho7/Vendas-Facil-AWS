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
import br.ufc.mobile.vendasfacil.dao.VendaDao;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.repository.VendaRepository;

public class VendaDaoImpl implements VendaDao {

    private static final String REFERENCE = "vendas";

    private List<Venda> vendas;
    private DatabaseReference mDatabase;
    private DataStatus listener;

    public VendaDaoImpl(final DataStatus listener) {

        if(listener != null){
            this.listener = listener;
        }

        vendas = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(REFERENCE);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendas.clear();
                for(DataSnapshot venda: dataSnapshot.getChildren()){
                    Venda v = venda.getValue(Venda.class);
                    vendas.add(v);
                }

                if(listener != null)
                    listener.DataIsLoaded(vendas);

                Log.i( this.getClass().getSimpleName(),
                        "Carregou a lista de vendas: " + vendas.toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(this.getClass().getSimpleName(), "onCancelled: " + databaseError.toString() );
            }
        });
    }

    @Override
    public void save(Venda obj) {
        obj.setId(this.getNewId());

        mDatabase
                .child(obj.getId())
                .setValue(obj);
    }

    @Override
    public boolean remove(Venda obj) {
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
    public List<Venda> getAll() {
        return vendas;
    }

    @Override
    public boolean update(Venda obj) {
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
