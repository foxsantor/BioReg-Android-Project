package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;
import com.example.bioregproject.ui.Settings.GeneralSettings.GeneralSettings;
import com.example.bioregproject.ui.Settings.GeneralSettings.GeneralSettingsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryWithSurfaceAdapter extends ListAdapter<CategorywithSurfaces, CategoryWithSurfaceAdapter.CategoryWithSurfaceHolder> {
    private List<CategorywithSurfaces> Surface = new ArrayList<>();
    private OnItemClickLisnter listener;
    private Context mContext;
    private SurfaceforCategroryAdapter surfaces;
    private Fragment fragment;


    public CategoryWithSurfaceAdapter(Context context,Fragment fragment) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.fragment=fragment;
    }

    private static final DiffUtil.ItemCallback<CategorywithSurfaces> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategorywithSurfaces>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategorywithSurfaces oldItem, @NonNull CategorywithSurfaces newItem) {
            return oldItem.categorieTache.getIdCat()==newItem.categorieTache.getIdCat();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategorywithSurfaces oldItem, @NonNull CategorywithSurfaces newItem) {
            return false;
        }
    };


    public CategorywithSurfaces getCategorieAt(int position) {
        return getItem(position);
    }


    @Override
    public CategoryWithSurfaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorie_surface, parent, false);
        return new CategoryWithSurfaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryWithSurfaceHolder holder, int position) {
        CategorywithSurfaces currentCategorie = getItem(position);
        holder.textViewnameCategorie.setText(currentCategorie.categorieTache.getName());
    surfaces=new SurfaceforCategroryAdapter(mContext);
    holder.recycleSurface.setLayoutManager(new LinearLayoutManager(mContext));
    holder.recycleSurface.setAdapter(surfaces);
    GeneralSettingsViewModel mViewModel = ViewModelProviders.of(fragment).get(GeneralSettingsViewModel.class);

        surfaces.submitList(currentCategorie.surfaces);


              new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                  @Override
                  public boolean onMove(@NonNull RecyclerView recycleSurface, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                      return false;
                  }


                  @Override
                  public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
surfaces.setOnItemClickListener(new SurfaceforCategroryAdapter.OnItemClickLisnter() {
    @Override
    public void onItemClick(com.example.bioregproject.entities.Surface Surface) {
        mViewModel.delete(Surface);
        Toast.makeText(mContext, "Task delete", Toast.LENGTH_SHORT).show();

    }
});
                                              }
              }).attachToRecyclerView(holder.recycleSurface);









        holder.imageButtonvisible.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             holder.recyli.setVisibility(View.VISIBLE);
             holder.imageButtonvisible.setVisibility(View.GONE);
             holder.imageButtonGone.setVisibility(View.VISIBLE);
         }
     });

     holder.imageButtonGone.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             holder.recyli.setVisibility(View.GONE);
             holder.imageButtonGone.setVisibility(View.GONE);
             holder.imageButtonvisible.setVisibility(View.VISIBLE);
         }
     });
        //listener.onItemClick(getItem(position));


    }

    class CategoryWithSurfaceHolder extends RecyclerView.ViewHolder{
        private TextView textViewnameCategorie;
        private RecyclerView recycleSurface;
        ImageButton imageButtonGone, imageButtonvisible;
        private CardView recyli;
        public CategoryWithSurfaceHolder(@NonNull View itemView) {
            super(itemView);
            textViewnameCategorie=itemView.findViewById(R.id.textViewNameCat);
            recyli = itemView.findViewById(R.id.recyli);
            recycleSurface=itemView.findViewById(R.id.recycleViewSurfacesaff);
            imageButtonGone=itemView.findViewById(R.id.imageButtonGone);
            imageButtonvisible=itemView.findViewById(R.id.imageAffichage);


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
        void onItemClick(CategorywithSurfaces Surface);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}
