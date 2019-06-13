package br.ufc.mobile.vendasfacil.presenter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface LocalizarFiliaisPresenter {

    void loadMarkers();

    void onMapReady(final GoogleMap googleMap, FusedLocationProviderClient fusedLocationClient);

    void confirmFilial(final String markerTitle, final LatLng markerLatLng);

    void confirmFromClosestFilial(FusedLocationProviderClient fusedLocationClient);

    void setFilialSelected(LatLng coordenadas);

}
