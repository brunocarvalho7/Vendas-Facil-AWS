package br.ufc.mobile.vendasfacil.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.presenter.VendasPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendasPresenterImpl implements VendasPresenter {

    private static final String TAG = "Vendas";

    private RetrofitConfigAuthorization retrofitConfigAuthorization;
    private VendasFacilView.ViewVendas mView;
    private Activity activity;
    private Venda venda;

    public VendasPresenterImpl(VendasFacilView.ViewVendas mView, Activity activity){
        this.venda = new Venda();
        this.mView = mView;
        this.activity = activity;
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
    }

    @Override
    public void setCliente(Cliente cliente) {
        if(cliente != null){
            this.venda.setCliente(cliente);
            mView.setButtonClienteText(cliente.getNome());
        }
    }

    @Override
    public void setQuantidade(ItemVenda itemVenda, Double newQuantidade) {
        if(itemVenda != null){
            itemVenda.setQtd(newQuantidade);
        }
        this.totalizarItens();
    }

    @Override
    public void totalizarItens(){
        mView.notifyAdapterItensDataSetChanged();
        if(this.venda.getItens().size() == 0)
            mView.setButtonTotalText(R.string.activity_venda_sem_itens);
        else if(this.venda.getItens().size() == 1)
            mView.setButtonTotalText(this.venda.getItens().size() + " item = R$ " + this.venda.getTotalText());
        else
            mView.setButtonTotalText(this.venda.getItens().size() + " itens = R$ " + this.venda.getTotalText());
    }

    public void incluirProduto(Produto produto){
        this.venda.incluir(produto);
        totalizarItens();
    }

    @Override
    public void removerItemVenda(ItemVenda itemVenda) {
        this.venda.remover(itemVenda);
        this.totalizarItens();
    }

    @Override
    public List<ItemVenda> getItensVenda() {
        return venda.getItens();
    }

    @Override
    public void onScanProdutoResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                this.findProdutoByBarCode(result.getContents());
            }
        }
    }

    @Override
    public void initScanBarCode() {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
        integrator.setPrompt("Novo produto");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    @Override
    public void findProdutoByBarCode(String barcode) {
        Call<Produto> callFindByBarCode = this.retrofitConfigAuthorization
                .getProdutoService().findByBarCode(barcode);

        callFindByBarCode.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if(response.isSuccessful()){
                    incluirProduto(response.body());
                }else{
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_BARCODE, t ,mView);
            }
        });
    }

    @Override
    public void checkoutVenda() {
        if(venda.getItens().size() == 0) {
            mView.showText("Insira itens na venda para continuar");
        }else if(venda.getCliente() == null) {
                mView.showText("Informe o cliente da venda");
        }else {
            mView.openVendasPagamentoActivity(venda);
        }
    }
}
