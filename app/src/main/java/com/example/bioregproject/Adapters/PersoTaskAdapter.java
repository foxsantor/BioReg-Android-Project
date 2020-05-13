package com.example.bioregproject.Adapters;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.ExternalPersoTask;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.ui.History.DeviceHistory;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersoTaskAdapter extends ListAdapter<ExternalPersoTask,PersoTaskAdapter.ExternalPersoTaskHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private TextView itemSelected;
    private static int counter;
    private Button markDone;
    private ImageButton clear;
    private List<Long> listsOfDeletableItems ;
    private View currentView;

    public PersoTaskAdapter(Context context,Activity activity,View currentView) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
        this.currentView =currentView;


    }

    private static final DiffUtil.ItemCallback<ExternalPersoTask> DIFF_CALLBACK = new DiffUtil.ItemCallback<ExternalPersoTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExternalPersoTask oldItem, @NonNull ExternalPersoTask newItem) {
            return oldItem.getCreation().equals(newItem.getCreation()) ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExternalPersoTask oldItem, @NonNull ExternalPersoTask newItem) {
            return  oldItem.getCreation().equals(newItem.getCreation());
        }
    };

    @NonNull
    @Override
    public ExternalPersoTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.persotakscontainer_item, parent, false);
        return new ExternalPersoTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalPersoTaskHolder holder, int position) {

        final ExternalPersoTask currentItem = getItem(position);
        listsOfDeletableItems = new ArrayList<>();
        itemSelected = currentView.findViewById(R.id.indi);
        clear = currentView.findViewById(R.id.cancel);
        markDone = currentView.findViewById(R.id.markdone);





        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                Navigation.findNavController(activity,R.id.fragmentHistory).navigate(R.id.deviceHistory);
                itemSelected.setVisibility(View.GONE);
                clear.setVisibility(View.GONE);

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                Navigation.findNavController(activity,R.id.fragmentHistory).navigate(R.id.deviceHistory);
                itemSelected.setVisibility(View.GONE);
                clear.setVisibility(View.GONE);

            }
        });
        PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        MyTaskAdapter innerExternalPersoTaskAdpater =new MyTaskAdapter(mContext,activity,1);
        String indicator;
        final String creationString = new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(creation);
        if(DateUtils.isToday(creation.getTime() + DateUtils.DAY_IN_MILLIS))
        {
            indicator ="Yesterday - ";
        }else if (DateUtils.isToday(creation.getTime()))
        {
            indicator ="Today - ";
        }else
        {
            indicator = "";
        }
        holder.dateExternalPersoTask.setText(indicator+creationString);
        holder.dateRecyle.setLayoutManager(new LinearLayoutManager(mContext));
        holder.dateRecyle.setAdapter(innerExternalPersoTaskAdpater);
        innerExternalPersoTaskAdpater.submitList(currentItem.getList());
        innerExternalPersoTaskAdpater.setOnIteemClickListener(new MyTaskAdapter.OnItemClickLisnter() {
            @Override
            public void OnItemClick(PersoTask PersoTask) {

            }

            @Override
            public void Select(View v, long position, long id) {

                if(position == 0){
                    counter = counter -1;
                    listsOfDeletableItems.remove(id);
                    itemSelected.setText(""+counter+" Selected");
                    if(counter == 0){
                        itemSelected.setVisibility(View.GONE);
                        clear.setVisibility(View.GONE);
                    }

                }
                else {
                    counter++;
                    listsOfDeletableItems.add(id);
                    itemSelected.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    itemSelected.setText(""+counter+" Selected");}
            }


        });



}


    public ExternalPersoTask getExternalPersoTaskAt(int postion) {
        return getItem(postion);
    }

    class ExternalPersoTaskHolder extends RecyclerView.ViewHolder {

        public RecyclerView dateRecyle;
        public TextView dateExternalPersoTask;


        public ExternalPersoTaskHolder(View itemView) {
            super(itemView);

            dateRecyle = itemView.findViewById(R.id.dateRecyle);
            dateExternalPersoTask = itemView.findViewById(R.id.dateHistory);
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
        void OnItemClick(ExternalPersoTask ExternalPersoTask);
        void callUponMyBody();
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
