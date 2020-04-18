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
import com.example.bioregproject.entities.ExternalHistory;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.ui.History.DeviceHistory;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends ListAdapter<ExternalHistory,HistoryAdapter.ExternalHistoryHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private DeviceHistory deviceHistory;
    private TextView itemSelected;
    private static int counter;
    private ConstraintLayout bar ,search;
    private Button cancel,delete;
    private ImageButton clear;
    private List<Long> listsOfDeletableItems ;

    public HistoryAdapter(Context context,Activity activity,DeviceHistory deviceHistory) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
        this.deviceHistory = deviceHistory;

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
        listsOfDeletableItems = new ArrayList<>();
        NavHostFragment navHostFragment = (NavHostFragment) deviceHistory.getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        itemSelected = parent.getView().findViewById(R.id.selected);
        cancel = parent.getView().findViewById(R.id.Cancel);
        delete = parent.getView().findViewById(R.id.Delete);
        clear = parent.getView().findViewById(R.id.clear);
        bar = parent.getView().findViewById(R.id.upperConst);
        search= parent.getView().findViewById(R.id.motherOfSearch);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Long id:listsOfDeletableItems)
                {

                   DeviceHistory.deleteHistory(id);
                   counter=0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Navigation.findNavController(activity,R.id.fragmentHistory).navigate(R.id.deviceHistory);
                            bar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                            itemSelected.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);
                            delete.setVisibility(View.GONE);
                            clear.setVisibility(View.GONE);
                            search.setVisibility(View.VISIBLE);
                        }
                    }, 1000);

                }


            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                Navigation.findNavController(activity,R.id.fragmentHistory).navigate(R.id.deviceHistory);
                bar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                itemSelected.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                clear.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                Navigation.findNavController(activity,R.id.fragmentHistory).navigate(R.id.deviceHistory);
                bar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                itemSelected.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                clear.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
        });
        PrettyTime p = new PrettyTime();
        Date creation = currentItem.getCreation();
        InnerHistoryAdapter innerHistoryAdapter =new InnerHistoryAdapter(mContext,activity);
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
        holder.dateExternalHistory.setText(indicator+creationString);
        holder.dateRecyle.setLayoutManager(new LinearLayoutManager(mContext));
        holder.dateRecyle.setAdapter(innerHistoryAdapter);
        innerHistoryAdapter.submitList(currentItem.getList());
        innerHistoryAdapter.setOnIteemClickListener(new InnerHistoryAdapter.OnItemClickLisnter() {
            @Override
            public void OnItemClick(History History) {
                return;
            }

            @Override
            public void Select(View v, long position , long id) {

                //Toast.makeText(activity, ""+position, Toast.LENGTH_SHORT).show();


                if(position == 0){
                    counter = counter -1;
                    listsOfDeletableItems.remove(id);
                itemSelected.setText(""+counter+" Selected");
                        if(counter == 0){
                            bar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                            itemSelected.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);
                            delete.setVisibility(View.GONE);
                            clear.setVisibility(View.GONE);
                            search.setVisibility(View.VISIBLE);

                        }

                }
                else {
                 counter++;
                    listsOfDeletableItems.add(id);
                        bar.setBackgroundColor(ContextCompat.getColor(activity, R.color.White));
                        itemSelected.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                        clear.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);

                itemSelected.setText(""+counter+" Selected");}
            }
        });



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
        void callUponMyBody();
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
