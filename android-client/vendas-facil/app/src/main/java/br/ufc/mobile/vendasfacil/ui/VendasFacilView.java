package br.ufc.mobile.vendasfacil.ui;

import android.support.annotation.StringRes;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.UsuarioDTO;
import br.ufc.mobile.vendasfacil.model.Venda;

public class VendasFacilView {

    public interface IShowText{
        void showText(String s);
    }

    public interface ViewMaster<T> extends IShowText{
        void updateAdapter(List<T> dados);
    }

    public interface ViewDetails<T> extends IShowText{
        T getData();
        void finishActivity();
    }

    public interface ViewLogin extends IShowText{
        UsuarioDTO getData();
        void abrirActivityPrincipal();
        void abrirActivitySignUp();
    }

    public interface ViewVendas extends IShowText{
        void notifyAdapterItensDataSetChanged();
        void setButtonTotalText(String newText);
        void setButtonTotalText(@StringRes int resid);
        void setButtonClienteText(String newText);
        void openVendasPagamentoActivity(Venda venda);
    }

    public interface ViewLocalizarFiliais extends IShowText{
        boolean verifyPermission(String permission);
        void showConfirmFilialDialog(final String markerTitle, final LatLng markerLatLng);
        void setMyLocationEnabled();
        void openActivityPrincipal();
        void callOnMapReady();
    }

}
