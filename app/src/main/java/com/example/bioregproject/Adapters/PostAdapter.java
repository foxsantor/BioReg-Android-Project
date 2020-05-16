package com.example.bioregproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Post;


public class PostAdapter extends ListAdapter <Post,PostAdapter.PostHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;
    int index_row= -1;



    public PostAdapter(Context context) {
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
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post currentPost = getItem(position);
        holder.namePost.setText(currentPost.getName());







    }

    class PostHolder extends RecyclerView.ViewHolder{
       private TextView namePost;
       private CardView caradone;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            namePost=itemView.findViewById(R.id.textView30);
            caradone=itemView.findViewById(R.id.caardone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(getItem(position));
                    itemView.setBackgroundColor(Color.parseColor("#3797DD"));
               }
                }
            });

        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Post post);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}