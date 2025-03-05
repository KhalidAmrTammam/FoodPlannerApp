package com.iti.java.foodplannerbykhalidamr.home.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.home.model.MealsRemoteDataSource;
import com.iti.java.foodplannerbykhalidamr.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener {

    private HomePresenter presenter;
    private TextView mealOfTheDayText;
    private ImageView mealOfTheDayImage;
    private RecyclerView mealsRecyclerView;
    private MealsAdapter mealsAdapter;
    private CardView mealOfTheDayCard;
    private Meal currentMealOfTheDay;
    private ImageButton imageButton;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView ;



    View myview;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        imageButton=view.findViewById(R.id.btn_logout);

        mealOfTheDayCard = view.findViewById(R.id.meal_of_the_day_card);
        mealOfTheDayText = view.findViewById(R.id.mealOfTheDayText);
        mealOfTheDayImage = view.findViewById(R.id.mealOfTheDayImage);
        mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView);
        myview=view;

        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mealsAdapter = new MealsAdapter(getContext(),new ArrayList<>(),this);
        mealsRecyclerView.setAdapter(mealsAdapter);

        presenter = new HomePresenter(this, FirebaseAuth.getInstance(),MealsRemoteDataSource.getApiService(),getContext());
        boolean isGuest = requireActivity()
                .getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .getBoolean("isGuest", false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        if (isGuest) {
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.favoritesFragment).setVisible(false);
            menu.findItem(R.id.weeklyPlannerFragment).setVisible(false);
        }
        else {
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.favoritesFragment).setVisible(true);
            menu.findItem(R.id.weeklyPlannerFragment).setVisible(true);
        }
        presenter.loadMealOfTheDay();

        mealOfTheDayCard.setOnClickListener(v -> {
            if (currentMealOfTheDay != null) {
                onMealClick(currentMealOfTheDay);
            }
        });
        presenter.loadMeals();
        imageButton.setOnClickListener(v -> logoutUser());


    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.home_top_bar, menu);
    }
    @Override
    public void displayMealOfTheDay(Meal meal) {
        if (!isAdded() || getContext() == null) return;
        currentMealOfTheDay = meal;
        mealOfTheDayText.setText(meal.getStrMeal());
        Glide.with(this).load(meal.getStrMealThumb()).into(mealOfTheDayImage);
    }

    @Override
    public void displayMeals(List<Meal> meals) {
        mealsAdapter.setMeals(meals);
    }


    @Override
    public void showError(String message) {
        Snackbar.make(myview,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_authenticationFragment4);
        });
        getActivity().getSharedPreferences("AppPrefs", getContext().MODE_PRIVATE)
                .edit()
                .remove("isGuest")
                .apply();



    }


    public void onMealClick(Meal meal) {
        Bundle args = new Bundle();
        args.putString("MEAL_ID", meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_itemInfoFragment, args);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean isGuest = requireActivity()
                .getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .getBoolean("isGuest", false);

        if (isGuest && (item.getItemId() == R.id.favoritesFragment || item.getItemId() == R.id.weeklyPlannerFragment)) {
            showGuestRestrictionMessage();
            return false; // Block navigation
        }

        return super.onOptionsItemSelected(item);
    }
    private void showGuestRestrictionMessage() {
        Toast.makeText(getContext(), "Login required to access this feature", Toast.LENGTH_SHORT).show();
    }
}
