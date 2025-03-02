package com.iti.java.foodplannerbykhalidamr.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.search.model.Category;
import com.iti.java.foodplannerbykhalidamr.search.model.Country;
import com.iti.java.foodplannerbykhalidamr.search.model.Ingredient;
import com.iti.java.foodplannerbykhalidamr.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchViewInterface {

    private SearchPresenter presenter;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ChipGroup chipGroup;
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private List<String> currentOptions = new ArrayList<>();
    private List<String> allCategories = new ArrayList<>();
    private List<String> allIngredients = new ArrayList<>();
    private List<String> allCountries = new ArrayList<>();
    private String selectedFilter = NAME;

    private static final String NAME = "Name";
    private static final String CATEGORY="Category";
    private static final String COUNTRY="Country";
    private static final String INGREDIENTS="Ingredients";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        chipGroup = view.findViewById(R.id.chipGroup);
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        listView = view.findViewById(R.id.listView);

        presenter = new SearchPresenter(this);
        setupRecyclerView();
        setupListView();
        setupChipListeners();
        setupSearchViewListener();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SearchAdapter(this::navigateToMealDetails);
        recyclerView.setAdapter(adapter);
    }

    private void setupListView() {
        listAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, currentOptions);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Chip selectedChip = chipGroup.findViewById(chipGroup.getCheckedChipId());

            if (selectedChip != null) {
                int selectedChipId = selectedChip.getId();
                recyclerView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

                if (selectedChipId == R.id.chipCategory) {
                    presenter.fetchMealsByCategory(currentOptions.get(position));
                } else if (selectedChipId == R.id.chipIngredient) {
                    presenter.fetchMealsByIngredient(currentOptions.get(position));
                } else if (selectedChipId == R.id.chipCountry) {
                    presenter.fetchMealsByCountry(currentOptions.get(position));
                }
            }
        });
    }

    private void setupChipListeners() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {
                selectedFilter = null;
                recyclerView.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                currentOptions.clear();
                listAdapter.notifyDataSetChanged();
                searchView.setQuery("", false);
            } else {
                selectedFilter = null;
                recyclerView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                if (checkedId == R.id.chipCategory) {
                    selectedFilter = CATEGORY;
                    presenter.fetchCategories();
                } else if (checkedId == R.id.chipIngredient) {
                    selectedFilter = INGREDIENTS;
                    presenter.fetchIngredients();
                } else if (checkedId == R.id.chipCountry) {
                    selectedFilter = COUNTRY;
                    presenter.fetchCountries();
                }
            }
        });
    }

    private void setupSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearchQuery(newText);
                return true;
            }
        });
    }

    private void handleSearchQuery(String query) {
        if (selectedFilter == null) return;
        List<String> filtered;

        switch (selectedFilter) {
            case NAME:
                presenter.searchMealsByName(query);
                break;
            case CATEGORY:
                filtered = presenter.filterList(allCategories, query);
                updateListView(filtered);
                break;
            case INGREDIENTS:
                filtered = presenter.filterList(allIngredients, query);
                updateListView(filtered);
                break;
            case COUNTRY:
                filtered = presenter.filterList(allCountries, query);
                updateListView(filtered);
                break;
        }
    }



    @Override
    public void showSearchResults(List<Meal> meals) {
        recyclerView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        adapter.submitList(meals);
    }

    @Override
    public void showCategories(List<Category> categories) {
        allCategories = convertCategoryList(categories);
        updateListView(allCategories);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        allIngredients = convertIngredientList(ingredients);
        updateListView(allIngredients);
    }

    @Override
    public void showCountries(List<Country> countries) {
        allCountries = convertCountryList(countries);
        updateListView(allCountries);
    }

    private List<String> convertCategoryList(List<Category> categories) {
        List<String> result = new ArrayList<>();
        for (Category category : categories) {
            result.add(category.getStrCategory());
        }
        return result;
    }

    private List<String> convertIngredientList(List<Ingredient> ingredients) {
        List<String> result = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            result.add(ingredient.getStrIngredient());
        }
        return result;
    }

    private List<String> convertCountryList(List<Country> countries) {
        List<String> result = new ArrayList<>();
        for (Country country : countries) {
            result.add(country.getStrArea());
        }
        return result;
    }

    private void updateListView(List<String> options) {
        currentOptions.clear();
        currentOptions.addAll(options);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMealDetails(Meal meal) {
        Bundle args = new Bundle();
        args.putString("MEAL_ID", meal.getIdMeal());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_searchFragment_to_itemInfoFragment, args);
    }

    @Override
    public void showError(String message) {
        Log.i("TAG", "showError: "+message);
        recyclerView.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }
}