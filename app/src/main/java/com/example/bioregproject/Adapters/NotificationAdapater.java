package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
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
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Notification;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class NotificationAdapater extends ListAdapter<Notification,NotificationAdapater.NotificationsHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;

    public NotificationAdapater(Context context,Activity activity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Notification> DIFF_CALLBACK = new DiffUtil.ItemCallback<Notification>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
            return  oldItem.isSeen()== newItem.isSeen() ;
        }
    };

    @NonNull
    @Override
    public NotificationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsHolder holder, int position) {

        final Notification currentItem = getItem(position);
        PrettyTime p = new PrettyTime();

        final String name = currentItem.getName();
        String description = currentItem.getDescription();
        Date creation = currentItem.getCreation();
        String owner = currentItem.getOwnerFirstName() + " "+ currentItem.getOwnerLastName();
        String categoryName = currentItem.getCategoryName();
        byte[] imageBase64 = currentItem.getImageBase64();
        byte[] objectImage64 = currentItem.getObjectImageBase64();
        final String ownerormated = StaticUse.capitalize(owner);
        //Log.i("owner", "onBindViewHolder: "+owner);
        final String nameFormated = StaticUse.capitalize(name);
        final String descriptionFormated = StaticUse.capitalize(description);
        final String categoryNameFormated = StaticUse.capitalize(categoryName);
        String stringFormatedFinal = ownerormated.replace(ownerormated,"<strong>"+ownerormated+"</strong>")+
                " "+descriptionFormated+
                nameFormated.replace(nameFormated,"<strong>"+nameFormated+"</strong>");
        holder.descriptionT.setText( Html.fromHtml(stringFormatedFinal));
        holder.categoryNameT.setText(categoryNameFormated);
        holder.timeT.setText(p.format(creation));
        if(currentItem.isSeen()== true)
        {
            holder.background.setBackgroundColor(activity.getResources().getColor(R.color.bpWhite));
        }else
        {
            holder.background.setBackgroundColor(activity.getResources().getColor(R.color.backgroundNotSelected));
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_warning_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

       Glide.with(mContext).asBitmap().load(imageBase64).apply(options).into(holder.ownerImage);

        if(objectImage64==null)
        {
            holder.objectImage.setVisibility(View.GONE);
        }else
        {

            Glide.with(mContext).asBitmap().load(objectImage64).apply(options).into(holder.objectImage);
        }
    }


    public Notification getNotificationAt(int postion) {
        return getItem(postion);
    }

    class NotificationsHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout background;
        public ImageView ownerImage,objectImage;
        public TextView descriptionT,categoryNameT,timeT;


        public NotificationsHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            ownerImage = itemView.findViewById(R.id.ownerImage);
            objectImage = itemView.findViewById(R.id.objectImage);
            descriptionT = itemView.findViewById(R.id.description);
            categoryNameT=itemView.findViewById(R.id.categoryName);
            timeT=itemView.findViewById(R.id.time);
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
        void OnItemClick(Notification Notification);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }


}
