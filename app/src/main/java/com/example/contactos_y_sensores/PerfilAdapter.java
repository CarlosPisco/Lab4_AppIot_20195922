package com.example.contactos_y_sensores;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactos_y_sensores.dto.Perfil;
import com.example.contactos_y_sensores.dto.Result;
import com.example.contactos_y_sensores.viewModel.AppActivityViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder>{

    private List<Perfil> listPerfil;
    private List<List<Result>> listResult;
    AppActivityViewModel appActivityViewModel;

    public List<List<Result>> getListResult() {
        return listResult;
    }

    public void setListResult(List<List<Result>> listResult) {
        this.listResult = listResult;
    }

    private Context context;

    public List<Perfil> getListPerfil() {
        return listPerfil;
    }

    public void setListPerfil(List<Perfil> listPerfil) {
        this.listPerfil = listPerfil;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private ViewModel viewModel;
    private LifecycleOwner lifecycleOwner;

    public PerfilAdapter(){

    }
    public PerfilAdapter( LifecycleOwner lifecycleOwner) {

        this.lifecycleOwner = lifecycleOwner;
    }
    private int pos;

    @NonNull
    @Override
    public PerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.perfil_rv, parent, false);
        return new PerfilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilViewHolder holder, int position) {

        //pos = position;
        List<Result> results = listResult.get(position);
        holder.result = results.get(0);

        TextView mistermiss = holder.itemView.findViewById(R.id.textView6);
        String a = results.get(0).getName().getTitle() +" "+results.get(0).getName().getFirst() + " "+results.get(0).getName().getLast();
        mistermiss.setText(a);

        TextView gender = holder.itemView.findViewById(R.id.textView14);
        gender.setText(results.get(0).getGender());

        TextView country = holder.itemView.findViewById(R.id.textView16);
        country.setText(results.get(0).getLocation().getCountry());

        TextView city = holder.itemView.findViewById(R.id.textView15);
        city.setText(results.get(0).getLocation().getCity());

        TextView email = holder.itemView.findViewById(R.id.textView17);
        email.setText(results.get(0).getEmail());

        TextView phone = holder.itemView.findViewById(R.id.textView18);
        phone.setText(results.get(0).getPhone());

        ImageView pic = holder.itemView.findViewById(R.id.imageView2);
        Picasso.get().load(results.get(0).getPicture().getLarge()).into(pic);




    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }




    public class PerfilViewHolder extends RecyclerView.ViewHolder{

        Result result;
        int pos;
        AppActivityViewModel appActivityViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(AppActivityViewModel.class);
        public PerfilViewHolder(@NonNull View itemView) {
            super(itemView);

            Button button = itemView.findViewById(R.id.buttonClose);
            button.setOnClickListener(view -> {
                List<List<Result>> resultList;
                Boolean magnetoVisible = appActivityViewModel.getMagnetometroVisible().getValue();

                if(magnetoVisible){
                    resultList = appActivityViewModel.getListMutableResult().getValue();
                }else{
                    resultList = appActivityViewModel.getListMutableResult2().getValue();
                }



                for(List<Result> rList : resultList){
                    if(rList.get(0).getId().equals(result.getId())){
                        pos = resultList.indexOf(rList);
                        break;
                    }
                }

                resultList.remove(pos);





                if(magnetoVisible){
                    appActivityViewModel.getListMutableResult().setValue(resultList);
                }else{
                    appActivityViewModel.getListMutableResult2().setValue(resultList);
                }


                Log.d("msg-test","la posisicon es "+pos);




            });





            Button button1 = itemView.findViewById(R.id.buttonEdit);
            button1.setOnClickListener(view -> {


                LinearLayout layout = new LinearLayout(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.edit_profile,null);

                List<List<Result>> resultList;
                Boolean magnetoVisible = appActivityViewModel.getMagnetometroVisible().getValue();

                if(magnetoVisible){
                    resultList = appActivityViewModel.getListMutableResult().getValue();
                }else{
                    resultList = appActivityViewModel.getListMutableResult2().getValue();
                }

                for(List<Result> rList : resultList){
                    if(rList.get(0).getId().equals(result.getId())){
                        pos = resultList.indexOf(rList);
                        break;
                    }
                }

                AlertDialog.Builder mydialog = new AlertDialog.Builder(getContext());
                mydialog.setTitle("Editar Perfil");

                EditText editTextName = dialogView.findViewById(R.id.editTextName);
                editTextName.setInputType(InputType.TYPE_CLASS_TEXT);
                String nom = resultList.get(pos).get(0).getName().getTitle() +" "+resultList.get(pos).get(0).getName().getFirst() + " "+resultList.get(pos).get(0).getName().getLast();
                editTextName.setText(nom);

                EditText editTextGender = dialogView.findViewById(R.id.editTextGender);
                editTextGender.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextGender.setText(resultList.get(pos).get(0).getGender());


                EditText editTextCity = dialogView.findViewById(R.id.editTextCity);
                editTextCity.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextCity.setText(resultList.get(pos).get(0).getLocation().getCity());

                EditText editTextCountry = dialogView.findViewById(R.id.editTextCountry);
                editTextCountry.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextCountry.setText(resultList.get(pos).get(0).getLocation().getCountry());

                EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
                editTextEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextEmail.setText(resultList.get(pos).get(0).getEmail());

                EditText editTextNumber = dialogView.findViewById(R.id.editTextNumber);
                editTextNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextNumber.setText(resultList.get(pos).get(0).getPhone());


                mydialog.setView(dialogView);

                //mydialog.setView(layout);

                mydialog.setPositiveButton("Aceptar", (dialogInterface, i) -> {

                    String updatedName = editTextName.getText().toString();
                    String updatedGender = editTextGender.getText().toString();
                    String updatedCity = editTextCity.getText().toString();
                    String updatedCountry = editTextCountry.getText().toString();
                    String updatedEmail = editTextEmail.getText().toString();
                    String updatedNumber = editTextNumber.getText().toString();

                    resultList.get(pos).get(0).getName().setTitle(updatedName);
                    resultList.get(pos).get(0).getName().setFirst("");
                    resultList.get(pos).get(0).getName().setLast("");

                    resultList.get(pos).get(0).setGender(updatedGender);
                    resultList.get(pos).get(0).getLocation().setCity(updatedCity);
                    resultList.get(pos).get(0).getLocation().setCountry(updatedCountry);
                    resultList.get(pos).get(0).setEmail(updatedEmail);
                    resultList.get(pos).get(0).setPhone(updatedNumber);

                    if(magnetoVisible){
                        appActivityViewModel.getListMutableResult().setValue(resultList);
                    }else{
                        appActivityViewModel.getListMutableResult2().setValue(resultList);
                    }

                });
                mydialog.show();
            });




        }
    }

    /* appActivityViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(AppActivityViewModel.class);

            Button button = itemView.findViewById(R.id.buttonClose);
            button.setOnClickListener(view -> {

                //aca sigo con la logica

                appActivityViewModel.getListMutableResult().observe(lifecycleOwner, results -> {

                    appActivityViewModel.getListMutableResult().setValue(results.remove(pos));

                });

            });*/
}
