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
import android.widget.Toast;

import com.example.contactos_y_sensores.PerfilAdapter;
import com.example.contactos_y_sensores.R;
import com.example.contactos_y_sensores.databinding.FragmentAceleBinding;
import com.example.contactos_y_sensores.dto.Result;
import com.example.contactos_y_sensores.viewModel.AppActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class AceleFragment extends Fragment {

    private SensorManager sensorManager;
    private Sensor acel;
    private RecyclerView rv;
    private int lastPos;

    FragmentAceleBinding binding;

    List<List<Result>> resultados = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AceleFragment() {
        // Required empty public constructor
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

        binding = FragmentAceleBinding.inflate(inflater,container,false);

        AppActivityViewModel appActivityViewModel = new ViewModelProvider(getActivity()).get(AppActivityViewModel.class);


        /*
        appActivityViewModel.getListMutableResult().observe(getViewLifecycleOwner(), results -> {

            resultados = results;
        });
        appActivityViewModel.getMagnetometroVisible().observe(getViewLifecycleOwner(), magneto ->{
            if(magneto){
                resultados.clear();
            }
        });*/

        appActivityViewModel.getListMutableResult2().observe(getViewLifecycleOwner(), results -> {
            PerfilAdapter perfilAdapter = new PerfilAdapter();
            perfilAdapter.setListResult(results);
            lastPos = results.size() -1;
            perfilAdapter.setContext(container.getContext());
            binding.rvPerfilesAcel.setAdapter(perfilAdapter);
            binding.rvPerfilesAcel.setLayoutManager(new LinearLayoutManager(container.getContext()));


        });
        rv = binding.rvPerfilesAcel;

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        acel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return binding.getRoot();
    }


    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];
            double atotal = Math.sqrt(ax*ax + ay*ay + az*az);

            double treshold = 22.0;
            if (atotal > treshold) {
                Toast.makeText(getContext(), String.format("Aceleracion: %.2f m/s^2", atotal), Toast.LENGTH_SHORT).show();
                scrollProfiles();
            }
        }



    };

    private void scrollProfiles() {
        rv.smoothScrollBy(0, 500);
        //rv.smoothScrollToPosition(lastPos);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, acel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(accelerometerListener);
        }
    }

}