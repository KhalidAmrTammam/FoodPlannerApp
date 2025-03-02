package com.iti.java.foodplannerbykhalidamr.main;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.java.foodplannerbykhalidamr.R;

public class MainActivity extends AppCompatActivity implements MainView {
    private BottomNavigationView bottomNavigationView;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mainPresenter = new MainPresenter(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        mainPresenter.handleNavigation(navController, bottomNavigationView);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    }

    @Override
    public void showBottomNav() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBottomNav() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void animateNavItem(View view) {
        if (view != null) {
            view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.nav_item_scale));
        }
    }
}
