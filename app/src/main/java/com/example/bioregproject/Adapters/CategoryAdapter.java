package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;


public class CategoryAdapter extends ListAdapter <CategorywithSurfaces,CategoryAdapter.CategoryHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public CategoryAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<CategorywithSurfaces> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategorywithSurfaces>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategorywithSurfaces oldItem, @NonNull CategorywithSurfaces newItem) {
            return oldItem.categorieTache.getIdCat()==newItem.categorieTache.getIdCat();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategorywithSurfaces oldItem, @NonNull CategorywithSurfaces newItem) {
            return oldItem.categorieTache.getName().equals(newItem.categorieTache.getName());
        }
    };




    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorie, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        CategorywithSurfaces currentCategorie = getItem(position);
        holder.namecategory.setText(currentCategorie.categorieTache.getName());
        final byte[] image = currentCategorie.categorieTache.getCategorieImage();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_warning_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        Glide.with(mContext).asBitmap().load(image).apply(options).into(holder.imageCategorie);




    }
    public CategorywithSurfaces getCategoryAt(int position) {
        return getItem(position);
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
       private TextView namecategory;
       private ImageView imageCategorie ;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            namecategory=itemView.findViewById(R.id.categoryname);
            imageCategorie=itemView.findViewById(R.id.imageView6);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));
                }
            });

        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(CategorywithSurfaces categorywithSurfaces);
        void onDelete(CategorywithSurfaces categorywithSurfaces);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}