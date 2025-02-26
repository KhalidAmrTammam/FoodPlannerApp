package com.iti.java.foodplannerbykhalidamr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.home.model.MealsRemoteDataSource;

public class ItemInfoFragment extends Fragment implements ItemInfoView {
    private ItemInfoPresenter presenter;
    private ImageView mealImage;
    private TextView mealName;
    private TextView mealCountry;
    private TextView mealInstructions;
    private RecyclerView recycler;
    private Toolbar toolbar;
    private Button btnFavorite;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.home_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Meal Info");
            toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.accentColor));
            toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primaryColor));
        } else {
            Log.e("ItemInfoFragment", "Toolbar not found in layout");
        }

        recycler = view.findViewById(R.id.ingredients_recycler);
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        mealCountry = view.findViewById(R.id.mealCountry);
        mealInstructions = view.findViewById(R.id.mealInstructions);
        webView = view.findViewById(R.id.mealVideo);
        btnFavorite = view.findViewById(R.id.btn_favorite);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        presenter = new ItemInfoPresenter(
                MealsRemoteDataSource.getApiService(),
                AppDatabase.getInstance(requireContext()).favoriteDao()
        );
        presenter.attachView(this);

        String mealId = getArguments().getString("MEAL_ID");
        if (mealId != null) {
            presenter.loadMealDetails(mealId);
        } else {
            Log.e("ItemInfoFragment", "Meal ID is null");
        }

        /*btnFavorite.setOnClickListener(v -> {
            // Assuming you have a method to get the current meal
            Meal currentMeal = presenter.getCurrentMeal();
            if (currentMeal != null) {
                presenter.toggleFavorite(currentMeal);
            }
        });*/
    }

    @Override
    public void showMealDetails(Meal meal) {
        Log.d("TAG", "showMealDetails: " + meal.getStrMeal());
        mealName.setText(meal.getStrMeal());
        mealCountry.setText("Country: " + meal.getStrArea());
        mealInstructions.setText("Instructions: " + meal.getStrInstructions());

        String imageUrl = meal.getStrMealThumb();
        if (imageUrl == null || imageUrl.isEmpty()) {
            mealImage.setImageResource(R.drawable.placeholder_image);
        } else {
            Glide.with(this)
                    .load(imageUrl)
                    .error(R.drawable.placeholder_image)
                    .into(mealImage);
        }

    }

    @Override
    public void showIngredients(Meal meal) {
        IngredientsAdapter adapter = new IngredientsAdapter(requireContext(), meal);
        recycler.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Log.e("ItemInfoFragment", message);
    }

    @Override
    public void loadYouTubeVideo(String embedUrl) {
        if (embedUrl != null && !embedUrl.isEmpty()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(embedUrl);
        } else {
            Log.e("ItemInfoFragment", "No valid YouTube embed URL provided");
        }

    }

    @Override
    public void updateFavoriteStatus(boolean isFavorite) {
        btnFavorite.setText(isFavorite ? "Remove Favorite" : "Add to Favorites");
    }
}