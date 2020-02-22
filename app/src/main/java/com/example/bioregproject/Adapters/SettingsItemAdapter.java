package com.example.bioregproject.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Settings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SettingsItemAdapter extends ListAdapter<Settings, SettingsItemAdapter.SettingsHolder> {

        private OnItemClickLisnter listener;

        private Context mContext;
        private int colorTextSelected = R.color.selectedText;
        private int colorTextNormal = R.color.darkLord;
        private int colorLayoutSelected = R.color.selectedGoogle;
        private int colorNotSelected = R.color.White;
        private int containerColor1;
        private int containerColor2;

        public SettingsItemAdapter(Context context) {
            super(DIFF_CALLBACK);
            this.mContext = context;

        }

        private static final DiffUtil.ItemCallback<Settings> DIFF_CALLBACK = new DiffUtil.ItemCallback<Settings>() {
            @Override
            public boolean areItemsTheSame(@NonNull Settings oldItem, @NonNull Settings newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Settings oldItem, @NonNull Settings newItem) {
                return  oldItem.getText().equals(newItem.getText()) &&
                        oldItem.getIcon()== newItem.getIcon()&&
                        oldItem.isSelected() == newItem.isSelected();
            }
        };

        @NonNull
        @Override
        public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_item, parent, false);
            return new SettingsItemAdapter.SettingsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingsHolder holder, int position) {

            final Settings currentItem = getItem(position);
            holder.mTextViewName.setText(currentItem.getText());

            if(currentItem.isSelected()){

                holder.mIconView.setImageResource(currentItem.getIconSelected());
                containerColor1 = this.colorLayoutSelected;
                containerColor2 = this.colorTextSelected;

            }else
            {
                holder.mIconView.setImageResource(currentItem.getIcon());
                containerColor1 = this.colorNotSelected;
                containerColor2 = this.colorTextNormal;
            }
            holder.view.setBackgroundColor(mContext.getResources().getColor(containerColor1));
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(containerColor1));
            holder.constraintLayout.setBackgroundColor(mContext.getResources().getColor(containerColor1));
            holder.mTextViewName.setTextColor(mContext.getResources().getColor(containerColor2));


        }


        public Settings getSettingAt(int postion) {
            return getItem(postion);
        }

        class SettingsHolder extends RecyclerView.ViewHolder {

            public ImageView mIconView;
            public TextView mTextViewName;
            public ConstraintLayout constraintLayout , view;
            public CardView cardView;

            public SettingsHolder(View itemView) {
                super(itemView);
                mIconView = itemView.findViewById(R.id.icon);
                mTextViewName = itemView.findViewById(R.id.text);
                constraintLayout=itemView.findViewById(R.id.consqt);
                cardView = itemView.findViewById(R.id.constCard);
                view = itemView.findViewById(R.id.views);



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
            void OnItemClick(Settings setting);
        }

        public void setOnIteemClickListener(OnItemClickLisnter listener) {
            this.listener = listener;
        }



    }

