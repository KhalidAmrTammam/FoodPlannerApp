package com.iti.java.foodplannerbykhalidamr.mealInfo.view;

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


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private final Context context;
    Meal meal;



    public IngredientsAdapter(Context context, Meal meal) {
        this.context = context;
        this.meal = meal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ingredients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> ingredients = new ArrayList<>(meal.getStrIngredients().values());
        List<String> measures = new ArrayList<>(meal.getStrMeasure().values());
        if (position < ingredients.size() && position < measures.size()&& !ingredients.get(position).isEmpty()) {
            holder.name.setText(ingredients.get(position));
            holder.measure.setText(measures.get(position));
            Glide.with(context)
                    .load("https://www.themealdb.com/images/ingredients/" + ingredients.get(position) + "-Small.png")
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return meal.getStrIngredients().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, measure;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ingredient_image);
            name = itemView.findViewById(R.id.ingredient_name);
            measure = itemView.findViewById(R.id.ingredient_measure);
        }
    }
}