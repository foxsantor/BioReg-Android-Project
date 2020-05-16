package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.Realtions.TacheWithSurfaceAndCategoryTache;


public class TacheAdapter extends ListAdapter<TacheWithSurfaceAndCategoryTache, TacheAdapter.TacheHolder> {
    private Context mContext;
    private OnItemClickLisnter listener;

    public TacheAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<TacheWithSurfaceAndCategoryTache> DIFF_CALLBACK = new DiffUtil.ItemCallback<TacheWithSurfaceAndCategoryTache>() {
        @Override
        public boolean areItemsTheSame(@NonNull TacheWithSurfaceAndCategoryTache oldItem, @NonNull TacheWithSurfaceAndCategoryTache newItem) {
            return oldItem.tache.getIdtask() == newItem.tache.getIdtask();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TacheWithSurfaceAndCategoryTache oldItem, @NonNull TacheWithSurfaceAndCategoryTache newItem) {
            return false;
        }
    };


    @NonNull
    @Override
    public TacheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tache, parent, false);
        return new TacheHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TacheHolder holder, int position) {
        TacheWithSurfaceAndCategoryTache currentTache = getItem(position);
        holder.textViewTime.setText(currentTache.tache.getDate().toString());
       holder.textViewSurface.setText(currentTache.surface.getNameSurface().toString());
       if (currentTache.tache.isStatus()){
           holder.checkDone.setChecked(true);
           holder.checkDone.setEnabled(false);
           holder.imageDone.setVisibility(View.VISIBLE);
           holder.imageOnhold.setVisibility(View.GONE);
       }


    }



    public TacheWithSurfaceAndCategoryTache getTacheAt(int position) {
        return getItem(position);
    }

    class TacheHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime;
        private TextView textViewSurface;
        private ImageView imageOnhold, imageDone;
        private CheckBox checkDone;
        private ImageButton imageButton;

        public TacheHolder(View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewtime);
            textViewSurface = itemView.findViewById(R.id.textView12);
            imageOnhold = itemView.findViewById(R.id.imageViewOnHold);
            imageDone = itemView.findViewById(R.id.imageViewDone);
            checkDone = itemView.findViewById(R.id.checkBox2);
            imageButton=itemView.findViewById(R.id.imageButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));
                }
            });
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemUpdateClick(getItem(position));
                }
            });

            checkDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkDone.isChecked())
                        listener.Select(v,getTacheAt(getAdapterPosition()).tache.getIdtask(),getTacheAt(getAdapterPosition()).tache.getIdtask());
                    else
                        listener.Select(v,0,getTacheAt(getAdapterPosition()).tache.getIdtask());
                }
            });

       ;

        }

    }

    public interface OnItemClickLisnter {
        void onItemClick(TacheWithSurfaceAndCategoryTache tache);
        void onItemUpdateClick(TacheWithSurfaceAndCategoryTache tache);
        void Select(View v, long position,long id);


    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}
