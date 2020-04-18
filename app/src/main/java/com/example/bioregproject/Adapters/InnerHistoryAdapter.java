package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.History;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InnerHistoryAdapter extends ListAdapter<History,InnerHistoryAdapter.HistoryHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;


    public InnerHistoryAdapter(Context context,Activity activity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<History> DIFF_CALLBACK = new DiffUtil.ItemCallback<History>() {
        @Override
        public boolean areItemsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.getCreation().equals(newItem.getCreation()) ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return  oldItem.getCreation().equals(newItem.getCreation());
        }
    };

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inner_history_model, parent, false);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {

        final History currentItem = getItem(position);
        PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        String  owner = currentItem.getOwner();
        String action = currentItem.getDescription();
        String nameH = currentItem.getName();
        String categoryName = currentItem.getCategoryName();
        holder.owner.setText(owner);
        holder.action.setText(action);
        holder.name.setText(nameH);
        holder.category.setText(categoryName);
        final String creationString = new SimpleDateFormat("h:mm a").format(creation);
        holder.time.setText(creationString);

        holder.owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+currentItem.getOwnerLinking(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+currentItem.getSubjectLinking(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public History getHistoryAt(int postion) {
        return getItem(postion);
    }

    class HistoryHolder extends RecyclerView.ViewHolder {

        public Button owner,name;
        public TextView time,action,category;
        public CheckBox checkBox;
        public ImageButton menu;


        public HistoryHolder(View itemView) {
            super(itemView);
            owner = itemView.findViewById(R.id.owner2);
            action = itemView.findViewById(R.id.action);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            checkBox = itemView.findViewById(R.id.checkBox);
            menu = itemView.findViewById(R.id.menu);
            time = itemView.findViewById(R.id.time);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkBox.isChecked())
                        listener.Select(v,getHistoryAt(getAdapterPosition()).getId(),getHistoryAt(getAdapterPosition()).getId());
                        else
                        listener.Select(v,0,getHistoryAt(getAdapterPosition()).getId());
                    }
                });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION)
//                        listener.OnItemClick(getItem(position));
//                }
//            });
        }

    }

    public interface OnItemClickLisnter {
        void OnItemClick(History History);
        void Select(View v, long position,long id);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
