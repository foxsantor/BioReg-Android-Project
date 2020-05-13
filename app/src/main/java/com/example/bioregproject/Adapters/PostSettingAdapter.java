package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Post;


public class PostSettingAdapter extends ListAdapter <Post, PostSettingAdapter.PostSettingHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public PostSettingAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };




    @Override
    public PostSettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_setting, parent, false);
        return new PostSettingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostSettingHolder holder, int position) {
        Post currentPost = getItem(position);
        holder.namePost.setText(currentPost.getName());




    }

    class PostSettingHolder extends RecyclerView.ViewHolder{
       private TextView namePost;
       private ImageButton supp,modif;
        public PostSettingHolder(@NonNull View itemView) {
            super(itemView);
            namePost=itemView.findViewById(R.id.textView32);
            supp=itemView.findViewById(R.id.imageButton4);
            modif=itemView.findViewById(R.id.imageButton5);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));
                }
            });
            supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.delete(getItem(position));
                }
            });
            modif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.update(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Post Post);
        void delete(Post Post);
        void update(Post Post);


    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}