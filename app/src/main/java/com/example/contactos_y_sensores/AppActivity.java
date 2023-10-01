package com.example.contactos_y_sensores;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.example.contactos_y_sensores.databinding.ActivityAppBinding;
import com.example.contactos_y_sensores.databinding.ActivityMainBinding;
import com.example.contactos_y_sensores.dto.Perfil;
import com.example.contactos_y_sensores.dto.Result;
import com.example.contactos_y_sensores.fragmentos.AceleFragment;
import com.example.contactos_y_sensores.fragmentos.MagentoFragment;
import com.example.contactos_y_sensores.services.ProfileService;
import com.example.contactos_y_sensores.viewModel.AppActivityViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {

    ActivityAppBinding binding;
    ProfileService profileService;

    AppActivityViewModel appActivityViewModel;
    private boolean isMagnetoFragmentVisible = true;

    List<List<Result>> listaResults = new ArrayList<>();
    List<List<Result>> listaResults2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        appActivityViewModel = new ViewModelProvider(AppActivity.this).get(AppActivityViewModel.class);

        binding.button2.setOnClickListener(v -> {

            Fragment fragmentToReplace;

            if (isMagnetoFragmentVisible) {
                fragmentToReplace = new AceleFragment();
                binding.button2.setText("Ir a Magnetometro");
            } else {
                fragmentToReplace = new MagentoFragment();
                binding.button2.setText("Ir a Acelerometro");
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, fragmentToReplace)
                    .commit();

            // Alterna el valor de la variable de control.
            isMagnetoFragmentVisible = !isMagnetoFragmentVisible;

        });

        binding.imageView.setOnClickListener(view -> {

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

            if(isMagnetoFragmentVisible){
                builder.setTitle("Detalles-Magnetómetro");


                builder.setMessage("Haga CLICK 'Añadir' para agregar contactos a su lista. Esta aplicación está utilizando el "
                        + "MAGNETÓMETRO de su dispositivo \n\n"+
                        "De esta forma, la lista se mostrará al 100% cuando se apunte al NORTE. Caso contrario se desvanecerá...");
            }else{
                builder.setTitle("Detalles-Acelerómetro");


                builder.setMessage("Haga CLICK 'Añadir' para agregar contactos a su lista. Esta aplicación está utilizando el "
                        + "ACELERÓMETRO de su dispositivo \n\n"+
                        "De esta forma, la lista hará scroll hacia abajo, cuando agite su dispositivo");
            }




            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                dialog.dismiss();
            });


            builder.show();


        });


        profileService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProfileService.class);





        //mando por livedata al fragment lo obtenido de la api
        binding.button3.setOnClickListener(view -> {

            profileService.getPerfil().enqueue(new Callback<Perfil>() {

                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {

                    if (response.isSuccessful()) {
                        Perfil perfil = response.body();
                        appActivityViewModel.getMagnetometroVisible().setValue(isMagnetoFragmentVisible);

                        if(isMagnetoFragmentVisible){
                            listaResults.add(perfil.getResults());
                            appActivityViewModel.getListMutableResult().setValue(listaResults);
                        }else{
                            listaResults2.add(perfil.getResults());
                            appActivityViewModel.getListMutableResult2().setValue(listaResults2);
                        }

                    }
                }

                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {
                    Log.d("msg-test", "Ocurrio algun error");
                }




            });


        });
    }
}