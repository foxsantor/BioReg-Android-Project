package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.Utils.DatabaseExporter;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.ExportFiles;
import com.example.bioregproject.entities.ExportFiles;

import java.util.ArrayList;

public class ExportAdapter  extends ListAdapter<ExportFiles, ExportAdapter.ExportFilesHolder> {


    private ExportAdapter.OnItemClickLisnter listener;

    private Context mContext;
    private Activity activity;
    private int colorTextSelected = R.color.selectedText;
    private int colorTextNormal = R.color.darkLord;
    private int colorLayoutSelected = R.color.selectedGoogle;
    private int colorNotSelected = R.color.White;
    private int containerColor1;
    private int containerColor2;

    public ExportAdapter(Context context,Activity activity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity=activity;

    }

    private static final DiffUtil.ItemCallback<ExportFiles> DIFF_CALLBACK = new DiffUtil.ItemCallback<ExportFiles>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExportFiles oldItem, @NonNull ExportFiles newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExportFiles oldItem, @NonNull ExportFiles newItem) {
            return  oldItem.getText().equals(newItem.getText()) &&
                    oldItem.getTableName().equals(newItem.getTableName());

        }
    };

    @NonNull
    @Override
    public ExportAdapter.ExportFilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.export_card, parent, false);
        return new ExportAdapter.ExportFilesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExportAdapter.ExportFilesHolder holder, int position) {

        final ExportFiles currentItem = getItem(position);
        holder.textView.setText(currentItem.getText());
        StaticUse.backgroundAnimator(holder.background);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.io.File filePath = DatabaseExporter.ExporterCSV(currentItem.getTableName(),activity);
                Uri path = FileProvider.getUriForFile(activity,"com.example.bioregproject.fileprovider",filePath);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT,currentItem.getTableName());
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM,path);
                activity.startActivity(Intent.createChooser(fileIntent,"0DATA"));
            }
        });



    }


    public ExportFiles getSettingAt(int postion) {
        return getItem(postion);
    }

    class ExportFilesHolder extends RecyclerView.ViewHolder {

        public ImageButton button;
        public TextView textView;
        public ConstraintLayout background;

        public ExportFilesHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.export);
            textView = itemView.findViewById(R.id.exportsT);
            background=itemView.findViewById(R.id.mother);




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
        void OnItemClick(ExportFiles setting);
    }

    public void setOnIteemClickListener(ExportAdapter.OnItemClickLisnter listener) {
        this.listener = listener;
    }

}
