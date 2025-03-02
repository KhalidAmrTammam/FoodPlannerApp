package com.iti.java.foodplannerbykhalidamr.weeklyPlanner.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.favorites.AppDatabase;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlan;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlanDao;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.presenter.WeeklyPlannerPresenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;


public class WeeklyPlannerFragment extends Fragment {
    private WeeklyPlannerPresenter presenter;
    private DaysAdapter daysAdapter;
    private WeeklyMealsAdapter mealsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        WeeklyPlanDao weeklyPlanDao = AppDatabase.getInstance(requireContext()).weeklyPlanDao();
        presenter = new WeeklyPlannerPresenter(weeklyPlanDao);

        setupDaysRecyclerView(view);
        setupMealsRecyclerView(view);
        setupObservables();

        presenter.loadPlansForDate(LocalDate.now().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupDaysRecyclerView(View view) {
        RecyclerView daysRecyclerView = view.findViewById(R.id.daysRecyclerView);
        daysAdapter = new DaysAdapter(getNext7Days());
        daysRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
        ));
        daysRecyclerView.setAdapter(daysAdapter);
    }

    private void setupMealsRecyclerView(View view) {
        RecyclerView mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView);
        mealsAdapter = new WeeklyMealsAdapter();
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mealsRecyclerView.setAdapter(mealsAdapter);
    }

    private void setupObservables() {
        daysAdapter.getDayClicks()
                .subscribe(
                        date -> presenter.loadPlansForDate(date.toString()),
                        throwable -> showError("Error selecting day")
                );

        mealsAdapter.getMealClicks()
                .subscribe(
                        weeklyPlan -> navigateToMealDetails(weeklyPlan),
                        throwable -> showError("Error selecting meal")
                );

        presenter.getPlansObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        plans -> mealsAdapter.submitList(plans),
                        throwable -> showError("Error loading plans")
                );
        mealsAdapter.getRemoveClicks()
                .subscribe(
                        weeklyPlan -> presenter.deletePlan(weeklyPlan),
                        throwable -> showError("Error removing meal")
                );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<LocalDate> getNext7Days() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            dates.add(today.plusDays(i));
        }
        return dates;
    }

    private void navigateToMealDetails(WeeklyPlan weeklyPlan) {
        Bundle args = new Bundle();
        args.putString("MEAL_ID", weeklyPlan.getMealId());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_weeklyPlannerFragment_to_itemInfoFragment, args);
    }

    private void showError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show();
    }


}