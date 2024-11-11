package com.laboratorio.appinombiliariaast;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.laboratorio.appinombiliariaast.ui.login.LoginActivity;

public class MainActivityViewModel extends AndroidViewModel {
    private SharedPreferences sp;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        sp = application.getSharedPreferences("usuario", 0);

    }

    public String getUserName(){
        return sp.getString("user_name", "");
    }
    public String getUserEmail(){
        return sp.getString("user_email", "");
    }
    public String getUserAvatarUrl(){
        return sp.getString("user_avatar_url", "");
    }

    public void logout(Application application){
        sp.edit().clear().apply();

        //redirigir al login
        Intent intent = new Intent(application, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }
}
