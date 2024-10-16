package com.laboratorio.appinombiliariaast.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.laboratorio.appinombiliariaast.MainActivity;
import com.laboratorio.appinombiliariaast.R;

import com.laboratorio.appinombiliariaast.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel vm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.callLogin(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
            }
        });
    }
}

