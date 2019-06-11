package br.ufc.mobile.vendasfacil.presenter;

public interface ProdutosDetailsPresenter {

    void onButtonConfirmClicked(String path);
    void salvar(String path);
    void uploadFoto(String idProduto, String path);
    void resizeImage(String path);

}
