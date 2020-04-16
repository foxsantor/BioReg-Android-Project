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
import com.example.bioregproject.entities.ExternalHistory;
import com.example.bioregproject.entities.History;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends ListAdapter<ExternalHistory,HistoryAdapter.ExternalHistoryHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;


    public HistoryAdapter(Context context,Activity activity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;


    }

    private static final DiffUtil.ItemCallback<ExternalHistory> DIFF_CALLBACK = new DiffUtil.ItemCallback<ExternalHistory>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExternalHistory oldItem, @NonNull ExternalHistory newItem) {
            return oldItem.getCreation().equals(newItem.getCreation()) ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExternalHistory oldItem, @NonNull ExternalHistory newItem) {
            return  oldItem.getCreation().equals(newItem.getCreation());
        }
    };

    @NonNull
    @Override
    public ExternalHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_model, parent, false);
        return new ExternalHistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalHistoryHolder holder, int position) {

        final ExternalHistory currentItem = getItem(position);
        PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        InnerHistoryAdapter innerHistoryAdapter =new InnerHistoryAdapter(mContext,activity);
        String indicator;
        final String creationString = new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(creation);
        if(DateUtils.isToday(creation.getTime() + DateUtils.DAY_IN_MILLIS))
        {
            indicator ="Yesterday";
        }else if (DateUtils.isToday(creation.getTime()))
        {
            indicator ="Today";
        }else
        {
            indicator = "";
        }
        holder.dateExternalHistory.setText(indicator+" - "+creationString);
        holder.dateRecyle.setLayoutManager(new LinearLayoutManager(mContext));
        holder.dateRecyle.setAdapter(innerHistoryAdapter);
        innerHistoryAdapter.submitList(currentItem.getList());




    }


    public ExternalHistory getExternalHistoryAt(int postion) {
        return getItem(postion);
    }

    class ExternalHistoryHolder extends RecyclerView.ViewHolder {

        public RecyclerView dateRecyle;
        public TextView dateExternalHistory;


        public ExternalHistoryHolder(View itemView) {
            super(itemView);
            dateRecyle = itemView.findViewById(R.id.dateRecyle);
            dateExternalHistory = itemView.findViewById(R.id.dateHistory);
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
        void OnItemClick(ExternalHistory ExternalHistory);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
