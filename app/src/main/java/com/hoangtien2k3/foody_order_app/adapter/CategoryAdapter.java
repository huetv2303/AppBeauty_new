package com.hoangtien2k3.foody_order_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> listCategory;

    public CategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.categoryName);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = listCategory.get(position);
        holder.title.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
