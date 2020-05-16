package com.example.bioregproject.ui.OilControl;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.bioregproject.Repositories.OilRepository;
import com.example.bioregproject.Repositories.PostRepository;
import com.example.bioregproject.entities.Oil;
import com.example.bioregproject.entities.Post;

import java.util.List;

public class OliControlViewModel extends AndroidViewModel {

    private OilRepository oilRepository;
    private PostRepository postRepository;
    private LiveData<List<Post>> allPost;
    private LiveData<List<Oil>> allOil;


    public OliControlViewModel(@NonNull Application application) {
        super(application);
        oilRepository = new OilRepository(application);
        postRepository = new PostRepository(application);
        allPost = postRepository.getAllPost();
        allOil = oilRepository.getallOils();


    }
    public LiveData<List<Post>> getAllPost() {
        return allPost;
    }
    public LiveData<List<Post>> getPostById(long id) {
        return postRepository.getPostById(id);
    }

    public LiveData<List<Oil>> getAllOil() {
        return allOil;
    }
    public LiveData<List<Oil>> getOilByAction(String action) {
        return oilRepository.getOilsByAction(action);
    }
    public LiveData<List<Oil>> getOilById(long id) {
        return oilRepository.getOilsById(id);
    }

    public void insert(Post post) {
        postRepository.insert(post);
    }
    public void insert(Oil oil) {
        oilRepository.insert(oil);
    }
    public void update(Oil oil){oilRepository.update(oil);}
    public void delete(Oil oil) {
        oilRepository.delete(oil);
    }










}
