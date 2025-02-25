package com.iti.java.foodplannerbykhalidamr.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    private final Context context;
    private List<Meal> meals = new ArrayList<>();
    private OnMealClickListener listener;

    public MealsAdapter(Context context,List<Meal> meals,OnMealClickListener listener) {
        this.meals=meals;
        this.context = context;
        this.listener=listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        holder.itemView.setOnClickListener(v -> {
                listener.onMealClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name);
            mealImage = itemView.findViewById(R.id.meal_image);
        }
    }
}