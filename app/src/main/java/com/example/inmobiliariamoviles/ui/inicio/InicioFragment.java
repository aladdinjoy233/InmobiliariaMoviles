package com.example.inmobiliariamoviles.ui.inicio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.FragmentInicioBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment {

    private static final LatLng UBICACION_IMOBILIARIA = new LatLng(-33.181587173345584, -66.31302381220519);
    protected static final int UBICACION_PERMISSION_REQUEST_CODE = 321;

    FusedLocationProviderClient fusedLocationProviderClient;
    private Location ubicacionActual;
    private MapaActual mapa;
    SupportMapFragment supportMapFragment;
    private FragmentInicioBinding binding;
    private InicioViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        verificarPermisios();

        initMapa();

        return root;
    }

    private void verificarPermisios() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), ubicacion -> {
                if (ubicacion != null) {
                    ubicacionActual = ubicacion;
                    actualizarMapa();
                }
            });
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void actualizarMapa() {
        if (ubicacionHabilitada()) {
            if (mapa == null) mapa = new MapaActual();
            supportMapFragment.getMapAsync(googleMap -> mapa.onMapReady(googleMap, ubicacionActual));
        }
    }

    private boolean ubicacionHabilitada() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(LocationManager.class);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initMapa() {
        mapa = new MapaActual();
        supportMapFragment.getMapAsync(mapa);
    }

    private final ActivityResultLauncher<String> locationPermissionLauncher = registerForActivityResult(
        new ActivityResultContracts.RequestPermission(),
        isGranted -> {
            if (isGranted) {
                verificarPermisios(); // Ya se que tiene permisos, pero es para obtener la ubicacion y actualizar el mapa
            } else {
                Toast.makeText(requireActivity(), "Permisos no dadas", Toast.LENGTH_SHORT).show();
            }
        }
    );

    public class MapaActual implements OnMapReadyCallback {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(UBICACION_IMOBILIARIA).title("Inmobiliaria La Punta"));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(UBICACION_IMOBILIARIA).zoom(15).bearing(45).tilt(70).build();

            CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(update);
        }

        public void onMapReady(@NonNull GoogleMap googleMap, Location location) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(UBICACION_IMOBILIARIA).title("Inmobiliaria La Punta"));

            if (location != null) {
                LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(userLatLng).title("Tu ubicación"));
            }

            CameraPosition cameraPosition = new CameraPosition.Builder().target(UBICACION_IMOBILIARIA).zoom(15).bearing(45).tilt(70).build();

            CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(update);
        }
    }
}