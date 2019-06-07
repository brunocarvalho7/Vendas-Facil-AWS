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

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.dao.ClienteDao;
import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.dao.impl.ClienteDaoImpl;
import br.ufc.mobile.vendasfacil.model.Cliente;

public class VendasClienteDialog extends AppCompatDialogFragment implements DataStatus<Cliente> {

    private OnClienteSelectListener listener;
    private ClienteDao daoCliente;
    private View view;
    private ListView listView;
    private ArrayAdapter listViewAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        daoCliente = new ClienteDaoImpl(this);

        view = getActivity().getLayoutInflater().inflate(R.layout.activity_venda_cliente, null);
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
        listViewAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1,
                daoCliente.getAll());

        listView = view.findViewById(R.id.activity_venda_cliente_listview);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onClienteSelected((Cliente) listView.getAdapter().getItem(position));
                dismiss();
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
    public void DataIsLoaded(List<Cliente> dados) {
        //TODO: TESTE
        listViewAdapter.notifyDataSetChanged();
    }

    public interface OnClienteSelectListener{
        void onClienteSelected(Cliente cliente);
    }

}
