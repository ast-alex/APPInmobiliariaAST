package com.laboratorio.appinombiliariaast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.laboratorio.appinombiliariaast.databinding.ActivityMainBinding;
import com.laboratorio.appinombiliariaast.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel vm;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hv = navigationView.getHeaderView(0);

        TextView userNameTextView = hv.findViewById(R.id.textViewName);
        TextView userEmailTextView = hv.findViewById(R.id.textViewEmail);
        ImageView userImageView = hv.findViewById(R.id.imageView);

        String userName = vm.getUserName();
        String userEmail = vm.getUserEmail();
        String userImage = vm.getUserAvatarUrl();

        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

        String baseUrl = "http://192.168.1.2:5166";
        String avatarPath = vm.getUserAvatarUrl();
        String avatarUrl = baseUrl + avatarPath;
        Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.loading_plc)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImageView);

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.map, R.id.nav_perfil, R.id.nav_inmueble,R.id.nav_slideshow, R.id.nav_contrato, R.id.nav_inquilino, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}