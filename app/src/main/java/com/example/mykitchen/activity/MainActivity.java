package com.example.mykitchen.activity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykitchen.R;
import com.example.mykitchen.adapter.AdapterRecipe;
import com.example.mykitchen.network.NetworkClient;
import com.example.mykitchen.network.RecipeAPI;
import com.example.mykitchen.pojo.Hits;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Parcelable {
    
    public static final Creator<MainActivity> CREATOR = new Creator<MainActivity>() {
        @Override
        public MainActivity createFromParcel(Parcel source) {
            return new MainActivity(source);
        }
        
        @Override
        public MainActivity[] newArray(int size) {
            return new MainActivity[size];
        }
    };
    NetworkClient networkClient = new NetworkClient();
    private RecyclerView recyclerViewRecipe;
    private AdapterRecipe adapterRecipe;
    private ProgressBar progressBar;
    private Button searchButton;
    private EditText searchEditText;
    private ImageView foodImageView;
    private TextView foodTextView;
    private String q;
    
    public MainActivity() {
    }
    
    protected MainActivity(Parcel in) {
        this.networkClient = in.readParcelable(NetworkClient.class.getClassLoader());
        this.recyclerViewRecipe = in.readParcelable(RecyclerView.class.getClassLoader());
        this.adapterRecipe = in.readParcelable(AdapterRecipe.class.getClassLoader());
        this.progressBar = in.readParcelable(ProgressBar.class.getClassLoader());
        this.searchButton = in.readParcelable(Button.class.getClassLoader());
        this.searchEditText = in.readParcelable(EditText.class.getClassLoader());
        this.foodImageView = in.readParcelable(ImageView.class.getClassLoader());
        this.foodTextView = in.readParcelable(TextView.class.getClassLoader());
        this.q = in.readString();
    }
    
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
    
    private void initRecyclerView() {
        recyclerViewRecipe = findViewById(R.id.recyclerView);
        adapterRecipe = new AdapterRecipe();
        recyclerViewRecipe.setAdapter(adapterRecipe);
        
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerViewRecipe.setLayoutManager(gridLayout);
        
    }
    
    private void visibility() {
        progressBar.setVisibility(View.INVISIBLE);
        foodImageView.setVisibility(View.INVISIBLE);
        foodTextView.setVisibility(View.INVISIBLE);
        recyclerViewRecipe.setVisibility(View.VISIBLE);
    }
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_search) {
            progressBar.setVisibility(View.VISIBLE);
            q = searchEditText.getText().toString();
            RecipeAPI recipeAPI = networkClient.getRecipeAPI();
            Call<Hits> recipesCall = recipeAPI.getRecipes(q);
            recipesCall.enqueue(new Callback<Hits>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Hits> call, Response<Hits> response) {
                    Hits recipesResponse = response.body();
                    assert recipesResponse != null;
                    adapterRecipe.setHits(recipesResponse.hits);
                    adapterRecipe.notifyDataSetChanged();
                    visibility();
                }
                
                @Override
                @EverythingIsNonNull
                public void onFailure(Call<Hits> call, Throwable t) {
                    visibility();
                }
            });
        }
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.networkClient, flags);
        dest.writeParcelable((Parcelable) this.recyclerViewRecipe, flags);
        dest.writeParcelable((Parcelable) this.adapterRecipe, flags);
        dest.writeParcelable((Parcelable) this.progressBar, flags);
        dest.writeParcelable((Parcelable) this.searchButton, flags);
        dest.writeParcelable((Parcelable) this.searchEditText, flags);
        dest.writeParcelable((Parcelable) this.foodImageView, flags);
        dest.writeParcelable((Parcelable) this.foodTextView, flags);
        dest.writeString(this.q);
    }
}