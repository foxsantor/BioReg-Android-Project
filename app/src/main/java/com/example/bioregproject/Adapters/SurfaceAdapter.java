package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Surface;

public class SurfaceAdapter extends ListAdapter<Surface,SurfaceAdapter.SurfaceHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public SurfaceAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Surface> DIFF_CALLBACK = new DiffUtil.ItemCallback<Surface>() {
        @Override
        public boolean areItemsTheSame(@NonNull Surface oldItem, @NonNull Surface newItem) {
            return oldItem.getIdSurface()==newItem.getIdSurface();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Surface oldItem, @NonNull Surface newItem) {
            return oldItem.getIdCategorie()==newItem.getIdCategorie()&&
                    oldItem.getNameSurface().equals(newItem.getNameSurface());
        }
    };


    @NonNull
    @Override
    public SurfaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surface,parent,false);
        return new SurfaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SurfaceHolder holder, int position) {
        Surface currentSurface = getItem(position);
    holder.name.setText(currentSurface.getNameSurface());
    }
    public Surface getSurfacesAt(int position) {
        return getItem(position);
    }



    class SurfaceHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public SurfaceHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameSurface);
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
        void onItemClick(Surface Surface);
    }
    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }

}
