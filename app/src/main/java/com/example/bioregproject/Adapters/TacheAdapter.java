package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.Tache;
import com.example.bioregproject.ui.Cleaning.ListPlanningCleanViewModel;
import com.example.bioregproject.ui.Settings.GeneralSettings.GeneralSettingsViewModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TacheAdapter extends ListAdapter<Tache, TacheAdapter.TacheHolder> {
    private Context mContext;
    private OnItemClickLisnter listener;
    private long timeLeft;


    public TacheAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Tache> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tache>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tache oldItem, @NonNull Tache newItem) {
            return oldItem.getIdtask() == newItem.getIdtask();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tache oldItem, @NonNull Tache newItem) {
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
        Tache currentTache = getItem(position);
        PrettyTime p = new PrettyTime();
        Date creation = currentTache.getCreatedAt();
        String  assgine = currentTache.getUser();
        String owner = currentTache.getOwnerName();
        Date due = currentTache.getDue();
        String formater,dueString2;
        holder.validation.setVisibility(View.GONE);
        holder.nameCat.setText(currentTache.getIdCategorie());
        Date now = new Date();


        final String dueString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(due);


        if (currentTache.getDue().compareTo(now) <= 0)
        {
//            holder.body.setCardBackgroundColor(mContext.getResources().getColor(R.color.disabled));
            dueString2 ="<font color='#fb4444'><u>"+dueString+"</u></font>";

        }else
        {
            dueString2="<u>"+dueString+"</u>";
        }
        formater =  "<strong>Task N"+currentTache.getIdtask()+"</strong>"+" "+" <font color='#1877F2'><strong>"+"</strong></font>"+" due "+dueString2+" Created: "+p.format(currentTache.getCreatedAt())+"</strong></font>"+" from "+owner+" assined to "+currentTache.getUser() ;
        holder.textViewTime.setText(Html.fromHtml(formater));





        if (currentTache.isStatus()) {
            final String validationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentTache.getValidationDate());
            holder.validation.setVisibility(View.VISIBLE);
            formater =  "<font color='#00A86B'><strong>Done on: "+validationDate+"</strong></font>";
            holder.validation.setText(Html.fromHtml(formater));
            holder.checkDone.setChecked(true);
            holder.checkDone.setEnabled(true);
//            holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.greenJade));

        }else{
           // holder.color.setCardBackgroundColor(mContext.getResources().getColor(R.color.redErrorDeep));
            holder.checkDone.setChecked(false);
            holder.checkDone.setEnabled(true);

        }


    }



    public Tache getTacheAt(int position) {
        return getItem(position);
    }

    class TacheHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime,nameCat;
        private CheckBox checkDone;
        private ImageButton imageButton;
        public CardView body;
        public TextView validation;



        public TacheHolder(View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewtime);
            checkDone = itemView.findViewById(R.id.checkBox2);
            imageButton=itemView.findViewById(R.id.imageButton);
            validation =itemView.findViewById(R.id.validation2);
            nameCat =itemView.findViewById(R.id.nameCat);
            body = itemView.findViewById(R.id.body);
            checkDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkDone.isChecked())
                        //Toast.makeText(activity, ""+getPersoTaskAt(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                        listener.Select(v,getItem(getAdapterPosition()).getIdtask(),getItem(getAdapterPosition()).getIdtask());
                    else
                        listener.Select(v,0,getItem(getAdapterPosition()).getIdtask());
                }
            });
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


       ;

        }

    }

    public interface OnItemClickLisnter {
        void onItemClick(Tache tache);
        void onItemUpdateClick(Tache tache);
        void Select(View v, long position,long id);


    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }

    public void StartTimer(TextView textView)
    {
        CountDownTimer countDownTimer =new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft=millisUntilFinished;
                UpateTimer(textView);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void UpateTimer(TextView textView)
    {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        textView.setText(timeLeftFormatted);
    }
}


