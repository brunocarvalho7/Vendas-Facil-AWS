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
import br.ufc.mobile.vendasfacil.repository.ProdutoRepository;
import br.ufc.mobile.vendasfacil.dao.ProdutoDao;
import br.ufc.mobile.vendasfacil.model.Produto;

public class ProdutoDaoImpl implements ProdutoDao {

    private static final String REFERENCE = "produtos";

    private List<Produto> produtos;
    private DatabaseReference mDatabase;
    private DataStatus listener;

    public ProdutoDaoImpl(final DataStatus listener) {

        if(listener != null){
            this.listener = listener;
        }

        produtos = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(REFERENCE);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();
                for(DataSnapshot produto: dataSnapshot.getChildren()){
                    Produto p = produto.getValue(Produto.class);
                    produtos.add(p);
                }

                if(listener != null)
                    listener.DataIsLoaded(produtos);

                Log.i( this.getClass().getSimpleName(),
                        "Carregou a lista de produtos: " + produtos.toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(this.getClass().getSimpleName(), "onCancelled: " + databaseError.toString() );
            }
        });
    }

    @Override
    public void save(Produto obj) {
        obj.setId(this.getNewId());

        mDatabase
                .child(obj.getId())
                .setValue(obj);
    }

    @Override
    public boolean remove(Produto obj) {
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
    public List<Produto> getAll() {
        return produtos;
    }

    @Override
    public boolean update(Produto obj) {
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
    public Produto getByBarCode(String barCode) {
        //TODO: QUERY BY BARCODE
        return ProdutoRepository.getInstance().getByBarCode(barCode);
    }
}
