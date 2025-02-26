package com.iti.java.foodplannerbykhalidamr.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.home.model.ApiService;
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
    private Toolbar toolbar;

    View myview;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.home_toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.accentColor));
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primaryColor));
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                presenter.logout();
                return true;
            }
            return false;
        });

        mealOfTheDayText = view.findViewById(R.id.mealOfTheDayText);
        mealOfTheDayImage = view.findViewById(R.id.mealOfTheDayImage);
        mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView);
        myview=view;
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mealsAdapter = new MealsAdapter(getContext(),new ArrayList<>(),this);
        mealsRecyclerView.setAdapter(mealsAdapter);

        presenter = new HomePresenter(this, FirebaseAuth.getInstance(),MealsRemoteDataSource.getApiService(),getContext());
        presenter.loadMealOfTheDay();
        presenter.loadMeals();
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.home_top_bar, menu);
    }
    @Override
    public void displayMealOfTheDay(Meal meal) {
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
        Navigation.findNavController(requireView()).navigate(R.id.authenticationFragment4);

    }


    public void onMealClick(Meal meal) {
        Bundle args = new Bundle();
        args.putString("MEAL_ID", meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_itemInfoFragment, args);

    }
}
