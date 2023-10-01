package com.example.contactos_y_sensores.fragmentos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactos_y_sensores.PerfilAdapter;
import com.example.contactos_y_sensores.databinding.FragmentMagentoBinding;
import com.example.contactos_y_sensores.dto.Result;
import com.example.contactos_y_sensores.viewModel.AppActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class MagentoFragment extends Fragment {

    private SensorManager sensorManager;
    private Sensor magnetometer;
    private RecyclerView recyclerView;
    FragmentMagentoBinding binding;
    List<List<Result>> resultados = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MagentoFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMagentoBinding.inflate(inflater, container, false);

        AppActivityViewModel appActivityViewModel = new ViewModelProvider(getActivity()).get(AppActivityViewModel.class);

        /*
        appActivityViewModel.getListMutableResult().observe(getViewLifecycleOwner(), results -> {

            resultados = results;
                });

        appActivityViewModel.getMagnetometroVisible().observe(getViewLifecycleOwner(), magneto ->{
            if(!magneto){
                resultados.clear();
            }
        });*/


        appActivityViewModel.getListMutableResult().observe(getViewLifecycleOwner(), results -> {

            PerfilAdapter perfilAdapter = new PerfilAdapter(getViewLifecycleOwner());
            perfilAdapter.setListResult(results);
            perfilAdapter.setContext(container.getContext());
            binding.rvPerfilesMag.setAdapter(perfilAdapter);
            binding.rvPerfilesMag.setLayoutManager(new LinearLayoutManager(container.getContext()));
        });


        recyclerView = binding.rvPerfilesMag;
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        return binding.getRoot();
    }



    private final SensorEventListener magnetometerListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent e) {
            float x = e.values[0];
            float y = e.values[1];
            actualizarVisibilidad(x, y);


        }


    };


    //se calcula la visibilidad a partir angulo en el plano horizontal
    private void actualizarVisibilidad(float x, float y) {
        double angle = Math.toDegrees(Math.atan2(y, x));
        double phi = angle - 90;

        phi = (phi < 0) ? phi + 360 : phi;

        float visibilidad;
        if (phi < 0.05 * 180) {
            visibilidad = (float) 1;
        } else if (phi < 0.1 * 180) {
            visibilidad = (float)0.9;
        } else if (phi < 0.2 * 180) {
            visibilidad = (float)0.8;
        } else if (phi < 0.3 * 180) {
            visibilidad = (float)0.7;
        } else if (phi < 0.4 * 180) {
            visibilidad = (float)0.6;
        } else if (phi < 0.5 * 180) {
            visibilidad = (float)0.5;
        } else if (phi < 0.6 * 180) {
            visibilidad = (float)0.4;
        } else if (phi < 0.7 * 180) {
            visibilidad = (float)0.3;
        } else if (phi < 0.8 * 180) {
            visibilidad = (float)0.2;
        } else if (phi < 0.9 * 180) {
            visibilidad = (float)0.1;
        } else {
            visibilidad = (float)0;
        }


        recyclerView.setAlpha(visibilidad);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(magnetometerListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(magnetometerListener);
        }
    }
}