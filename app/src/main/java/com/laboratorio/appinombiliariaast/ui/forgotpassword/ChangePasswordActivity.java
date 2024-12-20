package com.laboratorio.appinombiliariaast.ui.forgotpassword;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.ActivityChangePasswordBinding;
import com.laboratorio.appinombiliariaast.ui.login.LoginActivity;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    private ChangePasswordViewModel vm;
    private String token;
    private int idPropietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ChangePasswordViewModel.class);

            Intent intent = getIntent();
            if(Intent.ACTION_VIEW.equals(intent.getAction())){
                Uri uri = intent.getData();
                if(uri != null){
                    token = uri.getQueryParameter("token");
                    //verifica si el segmento anterior al ultimo es un numero valido
                    String[] pathSegments = Objects.requireNonNull(uri.getPath()).split("/");
                    if(pathSegments.length >=2){
                        try {
                            idPropietario = Integer.parseInt(pathSegments[pathSegments.length -2]);
                        } catch (NumberFormatException e) {
                            Log.e("ChangePasswordActivity", "Error al obtener el id del propietario: " + e.getMessage());
                        }
                    }
                    Log.d("ChangePasswordActivity", "Token obtenido: " + token);
                }
            }
        }catch (Exception e){
            Log.e("ChangePasswordActivity", "Error al obtener el token: " + e.getMessage());
        }

        binding.btnCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = binding.etNewPassword.getText().toString();
                String confirmPassword = binding.etConfirmPassword.getText().toString();

                if(newPassword.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!newPassword.equals(confirmPassword)){
                    Toast.makeText(ChangePasswordActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                    return;
                }
                vm.cambiarPassword(idPropietario, token, newPassword, new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

}