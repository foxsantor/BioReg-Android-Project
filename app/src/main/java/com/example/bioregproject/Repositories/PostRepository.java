package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.PostDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Post;

import java.util.List;

public class PostRepository {
    private PostDAO PostDAO;
    private LiveData<List<Post>> allPost;

    public PostRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        PostDAO = database.postDAO();
        allPost = PostDAO.getAllPost();
    }

    public void insert(Post Post){

        new InsertPostAsynTask(PostDAO).execute(Post);

    }
    public void delete(Post Post){

        new DeletePostAsynTask(PostDAO).execute(Post);
    }
    public void update(Post Post){

        new UpdatePostAsynTask(PostDAO).execute(Post);
    }
    public void deleteAllAccounts(){

        new DeleteAllPostAsynTask(PostDAO).execute();
    }
    public LiveData<List<Post>> getAllPost(){

        return allPost;
    }



    private static class InsertPostAsynTask extends AsyncTask<Post,Void,Void>
    {
        private PostDAO PostDAO;
        private InsertPostAsynTask(PostDAO PostDAO)
        {
            this.PostDAO=PostDAO;
        }
        @Override
        protected Void doInBackground(Post... categories) {
            PostDAO.insert(categories[0]);
            return null;
        }
    }
    private static class DeletePostAsynTask extends AsyncTask<Post,Void,Void>
    {
        private PostDAO PostDAO;
        private DeletePostAsynTask(PostDAO PostDAO)
        {
            this.PostDAO=PostDAO;
        }
        @Override
        protected Void doInBackground(Post... categories) {
            PostDAO.delete(categories[0]);
            return null;
        }
    }
    private static class UpdatePostAsynTask extends AsyncTask<Post,Void,Void>
    {
        private PostDAO PostDAO;
        private UpdatePostAsynTask(PostDAO PostDAO)
        {
            this.PostDAO=PostDAO;
        }
        @Override
        protected Void doInBackground(Post... categories) {
            PostDAO.update(categories[0]);
            return null;
        }
    }
    private static class DeleteAllPostAsynTask extends AsyncTask<Void,Void,Void>
    {
        private PostDAO PostDAO;
        private DeleteAllPostAsynTask(PostDAO PostDAO)
        {
            this.PostDAO=PostDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PostDAO.deleteAllPost();
            return null;
        }
    }


}
