package com.example.bioregproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Surface;


public class SurfaceforCategrory2Adapter extends ListAdapter<Surface, SurfaceforCategrory2Adapter.SurfaceforCategory2Holder> {

    private Context mContext;
    private OnItemClickLisnter listener;
    int index_row= -1;


    public SurfaceforCategrory2Adapter(Context context) {
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
             return false;
        }
    };

    @NonNull
    @Override
    public SurfaceforCategory2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zone, parent, false);
        return new SurfaceforCategory2Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SurfaceforCategory2Holder holder, int position) {
        Surface currentSurface = getItem(position);
        holder.textnameSurface.setText(currentSurface.getNameSurface());
        holder.cardViewZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index_row=position;
                notifyDataSetChanged(); }
        });
        if (index_row==position){

            holder.cardViewZone.setBackgroundColor(Color.parseColor("#F0F8FF"));
        }else{

            holder.cardViewZone.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }



    class SurfaceforCategory2Holder extends RecyclerView.ViewHolder{

        private TextView textnameSurface;
        private CardView cardViewZone;



        public SurfaceforCategory2Holder(@NonNull View itemView) {
            super(itemView);
            textnameSurface=itemView.findViewById(R.id.textViewTitle);
            cardViewZone=itemView.findViewById(R.id.cardViewZone);


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
