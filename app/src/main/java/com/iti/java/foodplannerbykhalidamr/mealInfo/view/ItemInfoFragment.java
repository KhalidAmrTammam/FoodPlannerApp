package com.iti.java.foodplannerbykhalidamr.mealInfo.view;

import static android.view.View.INVISIBLE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.favorites.model.AppDatabase;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.home.model.MealsRemoteDataSource;
import com.iti.java.foodplannerbykhalidamr.mealInfo.presenter.ItemInfoPresenter;

import java.util.Calendar;

public class ItemInfoFragment extends Fragment implements ItemInfoView {
    private ItemInfoPresenter presenter;
    private ImageView mealImage;
    private TextView mealName;
    private TextView mealCountry;
    private TextView mealInstructions;
    private RecyclerView recycler;
    private Button btnFavorite;
    private WebView webView;
    private Button btnAddToPlan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        recycler = view.findViewById(R.id.ingredients_recycler);
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        mealCountry = view.findViewById(R.id.mealCountry);
        mealInstructions = view.findViewById(R.id.mealInstructions);
        webView = view.findViewById(R.id.mealVideo);
        btnFavorite = view.findViewById(R.id.btn_favorite);
        btnAddToPlan = view.findViewById(R.id.btn_add_to_plan);
        btnAddToPlan.setOnClickListener(v -> showDatePickerDialog());
        boolean isGuest = requireActivity()
                .getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .getBoolean("isGuest", false);
        if (isGuest) {
            btnFavorite.setVisibility(INVISIBLE);
            btnAddToPlan.setVisibility(INVISIBLE);


        }

        recycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        presenter = new ItemInfoPresenter(
                MealsRemoteDataSource.getApiService(),
                AppDatabase.getInstance(requireContext()).favoriteDao(),
                AppDatabase.getInstance(requireContext()).weeklyPlanDao()

        );
        presenter.attachView(this);

        String mealId = getArguments().getString("MEAL_ID");
        if (mealId != null) {
            presenter.loadMealDetails(mealId);
        } else {
            Log.e("ItemInfoFragment", "Meal ID is null");
        }

        btnFavorite = view.findViewById(R.id.btn_favorite);
        btnFavorite.setOnClickListener(v -> {
            Meal currentMeal = presenter.getCurrentMeal();
            if (currentMeal != null) {
                presenter.toggleFavorite(currentMeal);
            }
        });
        String favmealId = getArguments().getString("MEAL_ID");
        presenter.checkFavoriteStatus(mealId);
    }

    @Override
    public void showMealDetails(Meal meal) {
        presenter.setCurrentMeal(meal);
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
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_MONTH, 6);

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);

                    if (selected.before(calendar) || selected.after(maxDate)) {
                        showMessage("Please select a date within the next 7 days");
                    } else {
                        String formattedDate = String.format("%04d-%02d-%02d", year, month+1, dayOfMonth);
                        presenter.saveToWeeklyPlan(formattedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePicker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        datePicker.show();
    }

   /* private boolean isValidDate(Calendar selectedDate) {
        Calendar today = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_MONTH, 7);
        return !selectedDate.before(today) && !selectedDate.after(maxDate);
    }*/
    @Override
    public Context getViewContext() {
        return isAdded() ? getContext() : null;
    }

    @Override
    public void showMessage(String message) {
        if (isAdded() && getContext() != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }    }

}