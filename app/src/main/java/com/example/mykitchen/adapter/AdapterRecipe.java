package com.example.mykitchen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykitchen.R;
import com.example.mykitchen.pojo.Recipe;

public class AdapterRecipe extends RecyclerView.Adapter <ViewHolderRecipe>{
    
    private Recipe[] recipes =new Recipe[0];
    @NonNull
    @Override
    public ViewHolderRecipe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolderRecipe(view);
    }
  
    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecipe holder, int position) {
           holder.bind(recipes[position]);
    }
    
    @Override
    public int getItemCount() {
        return recipes.length;
    }
    
    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }
}
