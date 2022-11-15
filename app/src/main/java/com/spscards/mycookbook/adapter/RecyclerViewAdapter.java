package com.spscards.mycookbook.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.spscards.mycookbook.R;
import com.spscards.mycookbook.model.Recipe;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private OnRecipeClickListener onRecipeClickListener;
    private List<Recipe> recipeList;
    private Context context;

    public RecyclerViewAdapter(List<Recipe> recipeList, Context context, OnRecipeClickListener onRecipeClickListener) {
        this.recipeList = recipeList;
        this.context = context;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row, parent, false);
        return new ViewHolder(view, onRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = Objects.requireNonNull(recipeList.get(position));
        holder.name.setText(recipe.getName());
        holder.type.setText(recipe.getType());

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnRecipeClickListener onRecipeClickListener;
        public TextView name;
        public TextView type;
        public ViewHolder(@NonNull View itemView, OnRecipeClickListener onRecipeClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name_textview);
            type = itemView.findViewById(R.id.row_type_textview);
            this.onRecipeClickListener = onRecipeClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRecipeClickListener.onRecipeClick(getAdapterPosition());
        }
    }

    public interface OnRecipeClickListener{
        void onRecipeClick(int position);
    }
}

