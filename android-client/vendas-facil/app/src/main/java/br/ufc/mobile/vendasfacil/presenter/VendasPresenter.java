package br.ufc.mobile.vendasfacil.presenter;

import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Produto;

public interface VendasPresenter {


    void setCliente(Cliente cliente);
    void setQuantidade(ItemVenda itemVenda, Double newQuantidade);
    void totalizarItens();
    void incluirProduto(Produto produto);
    void removerItemVenda(ItemVenda itemVenda);
    List<ItemVenda> getItensVenda();
    void onScanProdutoResult(int requestCode, int resultCode, @Nullable Intent data);
    void initScanBarCode();
    void findProdutoByBarCode(String barcode);
    void checkoutVenda();

}
