package br.ufc.mobile.vendasfacil.presenter.impl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.presenter.LocalizarFiliaisPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.Utils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthenticationFirebase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalizarFiliaisPresenterImpl
        implements LocalizarFiliaisPresenter {

    private static final String TAG = "LocalizarFiliais";

    private VendasFacilView.ViewLocalizarFiliais mView;
    private List<MarkerOptions> markers;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public LocalizarFiliaisPresenterImpl(VendasFacilView.ViewLocalizarFiliais mView){
        this.mView = mView;
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
    }

    public void loadMarkers(){
        markers = new ArrayList<>();

        Call<List<Filial>> calLGetFiliais = this.retrofitConfigAuthorization.getFilialService().findAll();

        calLGetFiliais.enqueue(new Callback<List<Filial>>() {
            @Override
            public void onResponse(Call<List<Filial>> call, Response<List<Filial>> response) {
                if(response.isSuccessful()){

                    for(Filial f : response.body()){
                        Log.i(TAG, "onResponse: " + f);
                        markers.add(new MarkerOptions()
                                .position(new LatLng(f.getLatitude(), f.getLongitude()))
                                .title(f.getDescricao()));
                    }

                    mView.callOnMapReady();
                }else{
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<List<Filial>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS, t, mView);
            }
        });

/*
        LatLng ufcQXD  = new LatLng(-4.9790841, -39.0585789);
        LatLng casaQXD = new LatLng(-4.9684813, -39.0266193);
        LatLng casaPHB = new LatLng(-2.9287365, -41.763117);

        markers.add(new MarkerOptions().position(ufcQXD).title("Filial Cedro"));
        markers.add(new MarkerOptions().position(casaQXD).title("Filial Planalto Universitário"));
        markers.add(new MarkerOptions().position(casaPHB).title("Filial Parnaíba-PI"));
        */
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap, FusedLocationProviderClient fusedLocationClient) {
        for(MarkerOptions m : markers)
            googleMap.addMarker(m);

        if((mView.verifyPermission(Manifest.permission.ACCESS_FINE_LOCATION)) &&
                (mView.verifyPermission(Manifest.permission.ACCESS_COARSE_LOCATION))){

            mView.setMyLocationEnabled();

            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.i("LocalizarFiliais", "onSuccess: MyLocation:: " + location);
                    CameraPosition camera = new CameraPosition.Builder()
                            .target(getFilialMaisProxima(location).getPosition())
                            .zoom(15.5f)
                            .bearing(300)
                            .tilt(50)
                            .build();

                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera));
                }
            });
        }
    }

    @Override
    public void confirmFilial(final String markerTitle, final LatLng markerLatLng) {
        mView.showConfirmFilialDialog(markerTitle, markerLatLng);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void confirmFromClosestFilial(FusedLocationProviderClient fusedLocationClient) {
        if((mView.verifyPermission(Manifest.permission.ACCESS_FINE_LOCATION)) &&
                (mView.verifyPermission(Manifest.permission.ACCESS_COARSE_LOCATION))) {

            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    MarkerOptions closestFilial = getFilialMaisProxima(location);
                    confirmFilial(closestFilial.getTitle(), closestFilial.getPosition());
                }
            });
        }
    }

    @Override
    public void setFilialSelected(LatLng coordenadas){
        Call<Filial> callGetByCoordenadas = this.retrofitConfigAuthorization.getFilialService()
                .findByCoordenadas(coordenadas.latitude, coordenadas.longitude);

        callGetByCoordenadas.enqueue(new Callback<Filial>() {
            @Override
            public void onResponse(Call<Filial> call, Response<Filial> response) {
                if(response.isSuccessful()){
                    VendasFacilAuthenticationFirebase.getInstance().setFilial(response.body());

                    mView.openActivityPrincipal();
                }else{
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<Filial> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, "Erro ao obter a filial selecionada",
                        t, mView);
            }
        });

    }

    private MarkerOptions getFilialMaisProxima(Location myLocation){
        markers = Utils.sortListbyDistance(markers, myLocation);

        return markers.get(0);
    }

}
