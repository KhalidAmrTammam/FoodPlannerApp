package com.iti.java.foodplannerbykhalidamr.search.view;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Meal> meals = new ArrayList<>();
    private final OnMealClickListener onMealClickListener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    public SearchAdapter(OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
    }

    public void submitList(List<Meal> newMeals) {
        meals = newMeals != null ? newMeals : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mealImage;
        private final TextView mealName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            mealName = itemView.findViewById(R.id.meal_name);
            itemView.setOnClickListener(v -> onMealClickListener.onMealClick(meals.get(getAdapterPosition())));
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getStrMeal());
            Glide.with(itemView).load(meal.getStrMealThumb()).into(mealImage);
        }
    }
}