package com.example.mykitchen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykitchen.R;
import com.example.mykitchen.adapter.AdapterRecipe;
import com.example.mykitchen.network.NetworkClient;
import com.example.mykitchen.network.RecipeAPI;
import com.example.mykitchen.pojo.Recipes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    NetworkClient networkClient = new NetworkClient();
    private RecyclerView recyclerViewRecipe;
    private AdapterRecipe adapterRecipe;
    private ProgressBar progressBar;
    private Button searchButton;
    private EditText searchEditText;
    private ImageView foodImageView;
    private TextView foodTextView;
    private String q;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        progressBar = findViewById(R.id.progress_bar);
        searchButton = findViewById(R.id.button_search);
        searchEditText = findViewById(R.id.edit_text_search);
        foodImageView = findViewById(R.id.image_view_food);
        foodTextView = findViewById(R.id.text_view_food);
        searchButton.setOnClickListener(this);
        
        initRecyclerView();
    }
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.button_search){
           progressBar.setVisibility(View.VISIBLE);
           q = searchEditText.getText().toString();
       }
    }
    
    private void initRecyclerView() {
        recyclerViewRecipe = findViewById(R.id.recyclerView);
        adapterRecipe = new AdapterRecipe();
        recyclerViewRecipe.setAdapter(adapterRecipe);
       
        GridLayoutManager gridLayout = new GridLayoutManager(this, 3);
        recyclerViewRecipe.setLayoutManager(gridLayout);
        RecipeAPI recipeAPI = networkClient.getRecipeAPI();
        Call<Recipes> recipesCall = recipeAPI.getRecipes(q);
        recipesCall.enqueue(new Callback<Recipes>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {
                Recipes recipesResponse = response.body();
                assert recipesResponse != null;
                adapterRecipe.setRecipes(recipesResponse.recipes);
                adapterRecipe.notifyDataSetChanged();
                visibility();
            }
    
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Recipes> call, Throwable t) {
              visibility();
            }
        });
    }
    
    private void visibility(){
       progressBar.setVisibility(View.INVISIBLE);
       foodImageView.setVisibility(View.INVISIBLE);
       foodImageView.setVisibility(View.INVISIBLE);
       recyclerViewRecipe.setVisibility(View.VISIBLE);
    }
}