package com.iti.java.foodplannerbykhalidamr.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.List;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Meal> favorites;
    private final OnFavoriteClickListener listener;

    public FavoritesAdapter(List<Meal> favorites, OnFavoriteClickListener listener) {
        this.favorites = favorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = favorites.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .into(holder.mealImage);

        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(meal));
        holder.itemView.setOnClickListener(v -> listener.onMealClick(meal));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void setFavorites(List<Meal> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void removeFavorite(Meal meal) {
        int position = favorites.indexOf(meal);
        favorites.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        Button deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            mealName = itemView.findViewById(R.id.meal_name);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }

    public interface OnFavoriteClickListener {
        void onDeleteClick(Meal meal);
        void onMealClick(Meal meal);
    }
}