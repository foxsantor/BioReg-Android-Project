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

public class LogsAdapter extends ListAdapter<History,LogsAdapter.HistoryHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;


    public LogsAdapter(Context context,Activity activity) {
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
                .inflate(R.layout.row_logs, parent, false);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {

        final History currentItem = getItem(position);
        //PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        String  owner = StaticUse.capitalize(currentItem.getOwnerFirstName()) + " "+ StaticUse.capitalize(currentItem.getOwnerLastName());
        String action = currentItem.getDescription();
        String nameH = currentItem.getName();
        String categoryName = currentItem.getCategoryName();
        String subCat = currentItem.getSubCategoryName();
        final String creationString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(creation);
        if(nameH== null ||nameH.equals("") || nameH.isEmpty())
        {
            nameH="";
        }else
        {
            nameH=" by the name of "+"<font color='#1877F2'><strong>"+nameH+"</strong></font>";
        }
        if(subCat== null ||subCat.equals("") || subCat.isEmpty())
        {
            subCat="";
        }else
        {

            subCat=" more precisely the "+"<strong>"+subCat+"</strong>";
        }

        final String textString = "<font color='#1877F2'><strong>"+owner+"</strong></font>"+" "+action+
                nameH+" on the <font color='#1877F2'><strong>"+creationString+"</strong></font>"+
                " affecting the "+"<strong>"+categoryName+"</strong>"+ subCat+".";
        holder.text.setText( Html.fromHtml(textString));
        holder.id.setText(Html.fromHtml("<strong>"+currentItem.getId()+"</strong>"));

    }


    public History getHistoryAt(int postion) {
        return getItem(postion);
    }

    class HistoryHolder extends RecyclerView.ViewHolder {

        public TextView text,id;


        public HistoryHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            id = itemView.findViewById(R.id.id);



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
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
