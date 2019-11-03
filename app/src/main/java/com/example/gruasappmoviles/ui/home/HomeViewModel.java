package com.example.gruasappmoviles.ui.home;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.gruasappmoviles.Home;
import com.example.gruasappmoviles.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("¡Bienvenido!");
    }

    public LiveData<String> getText() {
        return mText;
    }


}