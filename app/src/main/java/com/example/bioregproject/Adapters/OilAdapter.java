package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Oil;


public class OilAdapter extends ListAdapter <Oil,OilAdapter.OilHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public OilAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Oil> DIFF_CALLBACK = new DiffUtil.ItemCallback<Oil>() {
        @Override
        public boolean areItemsTheSame(@NonNull Oil oldItem, @NonNull Oil newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Oil oldItem, @NonNull Oil newItem) {
            return oldItem.getDateUtilisation().equals(newItem.getDateUtilisation());


        }
    };


    @Override
    public OilHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oil, parent, false);
        return new OilHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OilHolder holder, int position) {
        Oil currentOil = getItem(position);
        holder.post.setText(currentOil.getPost());
        holder.time.setText(currentOil.getDateUtilisation().toString());
        holder.action.setText(currentOil.getAction());
      // holder.filtrage.setText(currentOil.getFiltrage());

    }

    class OilHolder extends RecyclerView.ViewHolder{
       private TextView post,time,filtrage;
       private Button action;
       private ImageButton supp,modi;

        public OilHolder(@NonNull View itemView) {
            super(itemView);
            post=itemView.findViewById(R.id.post);
            supp=itemView.findViewById(R.id.suppOil);
            time=itemView.findViewById(R.id.time);
            action=itemView.findViewById(R.id.action);
            filtrage=itemView.findViewById(R.id.filtrage);
            modi=itemView.findViewById(R.id.modi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));

                }
            });
            supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.delete(getItem(position));
                }
            });
            modi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.update(getItem(position));
                }
            });

        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Oil oil);
        void delete (Oil oil);
        void update (Oil oil);

    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}