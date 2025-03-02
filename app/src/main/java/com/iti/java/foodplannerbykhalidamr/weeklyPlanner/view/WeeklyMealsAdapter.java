package com.iti.java.foodplannerbykhalidamr.weeklyPlanner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class WeeklyMealsAdapter extends RecyclerView.Adapter<WeeklyMealsAdapter.MealViewHolder> {
    private final PublishSubject<WeeklyPlan> clickSubject = PublishSubject.create();
    private final PublishSubject<WeeklyPlan> removeClickSubject = PublishSubject.create();

    private List<WeeklyPlan> meals = new ArrayList<>();

    public Observable<WeeklyPlan> getMealClicks() {
        return clickSubject.hide();
    }
    public Observable<WeeklyPlan> getRemoveClicks() {
        return removeClickSubject.hide();
    }

    public void submitList(List<WeeklyPlan> newMeals) {
        this.meals = newMeals != null ? newMeals : Collections.emptyList();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekly_meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        WeeklyPlan weeklyPlan = meals.get(position);
        holder.bind(weeklyPlan);

        holder.itemView.setOnClickListener(v ->
                clickSubject.onNext(weeklyPlan)
        );
        holder.btnRemove.setOnClickListener(v ->
                removeClickSubject.onNext(weeklyPlan)
        );
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mealImage;
        private final TextView mealName;
        private final ImageButton btnRemove;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            btnRemove = itemView.findViewById(R.id.btnRemove);

        }

        public void bind(WeeklyPlan weeklyPlan) {
            mealName.setText(weeklyPlan.getMeal().getStrMeal());
            Glide.with(itemView)
                    .load(weeklyPlan.getMeal().getStrMealThumb())
                    .into(mealImage);
        }
    }
}