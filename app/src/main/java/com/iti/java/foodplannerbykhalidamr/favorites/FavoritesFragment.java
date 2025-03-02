package com.iti.java.foodplannerbykhalidamr.favorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements FavoritesView, FavoritesAdapter.OnFavoriteClickListener {
    private FavoritesPresenter presenter;
    private FavoritesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        presenter = new FavoritesPresenter(AppDatabase.getInstance(requireContext()).favoriteDao(), this);
        presenter.loadFavorites();
    }

    @Override
    public void showFavorites(List<Meal> favorites) {
        adapter.setFavorites(favorites);
    }

    @Override
    public void onFavoriteDeleted(Meal meal) {
        adapter.removeFavorite(meal);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.deleteFavorite(meal);
    }

    @Override
    public void onMealClick(Meal meal) {
        Bundle args = new Bundle();
        args.putString("MEAL_ID", meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(R.id.action_favoritesFragment_to_itemInfoFragment, args);
    }
}