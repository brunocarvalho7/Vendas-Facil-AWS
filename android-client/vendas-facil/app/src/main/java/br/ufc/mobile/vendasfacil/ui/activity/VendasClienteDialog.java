package br.ufc.mobile.vendasfacil.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendasClienteDialog extends AppCompatDialogFragment implements VendasFacilView.IShowText {

    private static final String TAG = "Vendas-cilente";

    private OnClienteSelectListener listener;
    private View view;
    private ListView listView;
    private ArrayAdapter listViewAdapter;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.activity_venda_cliente, null);
        retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();

        setUpListViewClientes();
        setUpTextInputSearch();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (OnClienteSelectListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must be implement OnClienteSelectListener");
        }
    }

    private void setUpListViewClientes() {
        Call<List<Cliente>> callFindAll = this.retrofitConfigAuthorization
                .getClienteService().findAll();

        callFindAll.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if(response.isSuccessful()){
                    listViewAdapter = new ArrayAdapter<>(view.getContext(),
                            android.R.layout.simple_list_item_1,
                            response.body());

                    listView = view.findViewById(R.id.activity_venda_cliente_listview);
                    listView.setAdapter(listViewAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listener.onClienteSelected((Cliente) listView.getAdapter().getItem(position));
                            dismiss();
                        }
                    });
                }else{
                    APIUtils.getInstance().onRequestError(response, VendasClienteDialog.this);
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
               APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS,
                       t, VendasClienteDialog.this);
            }
        });
    }

    private void setUpTextInputSearch() {
        TextInputEditText txtSearch = view.findViewById(R.id.activity_venda_cliente_nome);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                listViewAdapter.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    public void showText(String s) {
        Toast.makeText(getContext(), "Erro ao tentar localizar os clientes", Toast.LENGTH_SHORT).show();
    }

    public interface OnClienteSelectListener{
        void onClienteSelected(Cliente cliente);
    }

}
