package com.laboratorio.appinombiliariaast.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.laboratorio.appinombiliariaast.MainActivity;
import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.ActivityLoginBinding;
import com.laboratorio.appinombiliariaast.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Intent intent = new Intent(LoginActivity.this,  MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Error de inicio de sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

