package com.example.contactos_y_sensores.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactos_y_sensores.dto.Perfil;
import com.example.contactos_y_sensores.dto.Result;

import java.util.List;

public class AppActivityViewModel extends ViewModel {

    MutableLiveData<Perfil> perfil = new MutableLiveData<>();

    MutableLiveData<List<List<Result>>> listMutableResult = new MutableLiveData<>();
    MutableLiveData<List<List<Result>>> listMutableResult2 = new MutableLiveData<>();

    public MutableLiveData<List<List<Result>>> getListMutableResult2() {
        return listMutableResult2;
    }

    public void setListMutableResult(MutableLiveData<List<List<Result>>> listMutableResult) {
        this.listMutableResult = listMutableResult;
    }

    MutableLiveData<Boolean> magnetometroVisible = new MutableLiveData<>();

    public MutableLiveData<Boolean> getMagnetometroVisible() {
        return magnetometroVisible;
    }

    public MutableLiveData<List<List<Result>>> getListMutableResult() {
        return listMutableResult;
    }

    public MutableLiveData<Perfil> getPerfil() {
        return perfil;
    }
}
