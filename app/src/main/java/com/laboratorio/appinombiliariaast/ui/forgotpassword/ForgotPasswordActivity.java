package com.laboratorio.appinombiliariaast.ui.forgotpassword;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ForgotPasswordViewModel vm;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ForgotPasswordViewModel.class);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevaPassword = binding.etNuevaPassword.getText().toString();
                String confirmarPassword = binding.etConfirmarPassword.getText().toString();

                if(nuevaPassword.equals(confirmarPassword)){
                    vm.resetPassword(nuevaPassword, confirmarPassword);
                }else{
                    Toast.makeText(getApplication(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        Uri data = intent.getData();
        if(data != null && data.getPath().equals("/reset-password")){
            String email = data.getQueryParameter("email");
            String token = data.getQueryParameter("token");
            vm.setEmailToken(email, token);
        }
    }
}