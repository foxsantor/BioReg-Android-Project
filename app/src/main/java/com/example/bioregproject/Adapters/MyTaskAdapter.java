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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.entities.PersoTask;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyTaskAdapter extends ListAdapter<PersoTask,MyTaskAdapter.PersoTaskHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private int mode ;


    public MyTaskAdapter(Context context,Activity activity,int mode) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
        this.mode=mode;
    }

    private static final DiffUtil.ItemCallback<PersoTask> DIFF_CALLBACK = new DiffUtil.ItemCallback<PersoTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull PersoTask oldItem, @NonNull PersoTask newItem) {
            return oldItem.getCreation().equals(newItem.getCreation()) ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PersoTask oldItem, @NonNull PersoTask newItem) {
            return  oldItem.getCreation().equals(newItem.getCreation());
        }
    };

    @NonNull
    @Override
    public PersoTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytask_item,parent, false);
        return new PersoTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersoTaskHolder holder, int position) {

        final PersoTask currentItem = getItem(position);
        PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        String  assgine = currentItem.getAssignedName();
        String action = currentItem.getDescription();
        String title= currentItem.getName();
        String owner = currentItem.getOwnerName();
        String stats = currentItem.getState();
        Date due = currentItem.getDue();
        String formater;
        String priority = currentItem.getPiority();
        final String dueString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(due);
        if(mode == 2)
        {
            formater = "<font color='#1877F2'><strong>"+title+"</strong></font>"+" due "+"<u>"+dueString+"</u>"+" assigned to "+"<strong>"+currentItem.getAssignedName()+"</strong>"+" Created:"+p.format(currentItem.getCreation()) ;
        }else
        {
            formater =  "<font color='#1877F2'><strong>"+title+"</strong></font>"+" due "+"<u>"+dueString+"</u>"+" Created: "+p.format(currentItem.getCreation()) ;
        }

        holder.itemText.setText(Html.fromHtml(formater));
        if(stats.equals("Open"))
        {
            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellowWarning));
        }else if (stats.equals("Invalid")){

            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.redErrorDeep));
        }
        else
        {
            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.greenJade));
        }
        if(priority.equals("Critic"))
        {
            holder.priority.setVisibility(View.VISIBLE);
            holder.prorityImage.setVisibility(View.VISIBLE);
            holder.priority.setText("Critic");
            holder.priority.setTextColor(mContext.getResources().getColor(R.color.redErrorDeep));
            Glide.with(mContext).load(R.drawable.ic_error_outline_red_24dp).into(holder.prorityImage);

        }else if(priority.equals("High"))
        { holder.priority.setVisibility(View.VISIBLE);
            holder.prorityImage.setVisibility(View.VISIBLE);
            holder.priority.setText("High");
            holder.priority.setTextColor(mContext.getResources().getColor(R.color.yellowWarning));
            Glide.with(mContext).load(R.drawable.ic_error_outline_warning_24dp).into(holder.prorityImage);
        }else
        {
            holder.priority.setVisibility(View.INVISIBLE);
            holder.prorityImage.setVisibility(View.INVISIBLE);
        }
    }



    public PersoTask getPersoTaskAt(int postion) {
        return getItem(postion);
    }

    class PersoTaskHolder extends RecyclerView.ViewHolder {

        public CardView color;
        public TextView itemText,priority;
        public CheckBox checkBox;
        public ImageButton mark;
        public ImageView prorityImage;


        public PersoTaskHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
            itemText = itemView.findViewById(R.id.priority);
            checkBox = itemView.findViewById(R.id.check);
            priority = itemView.findViewById(R.id.itemText);
            mark = itemView.findViewById(R.id.mark);
            prorityImage = itemView.findViewById(R.id.imageView13);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked())
                        //Toast.makeText(activity, ""+getPersoTaskAt(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                        listener.Select(v,getItem(getAdapterPosition()).getId(),getItem(getAdapterPosition()).getId());
                    else
                        listener.Select(v,0,getItem(getAdapterPosition()).getId());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.OnItemClick(getItem(position));
                }
            });
        }

    }

    public interface OnItemClickLisnter {
        void OnItemClick(PersoTask PersoTask);
        void Select(View v, long position,long id);

    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
