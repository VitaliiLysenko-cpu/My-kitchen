package com.example.mykitchen.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykitchen.R;
import com.example.mykitchen.pojo.Recipe;
import com.squareup.picasso.Picasso;

public class ViewHolderRecipe extends RecyclerView.ViewHolder {
    
    private ImageView imageRecipe;
    private TextView labelRecipe;
    
    public ViewHolderRecipe(@NonNull View itemView) {
        super(itemView);
        imageRecipe = itemView.findViewById(R.id.image_recipe);
        labelRecipe = itemView.findViewById(R.id.label_recipe);
    }
    
    void bind (Recipe recipe){
    
        Picasso.get()
                .load(recipe.image)
                .into(imageRecipe);
        labelRecipe.setText(recipe.label);
    }
}
