package com.example.bioregproject.ui.Settings.OilSetting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.Repositories.PostRepository;
import com.example.bioregproject.entities.Post;

import java.util.List;

public class OilSettingViewModel extends AndroidViewModel {


    private PostRepository postRepository;
    private LiveData<List<Post>> allPost;


    public OilSettingViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        allPost = postRepository.getAllPost();


    }

    public LiveData<List<Post>> getAllPost(){ return allPost; }
    public void insert(Post post) {
        postRepository.insert(post);
    }
    public void delete(Post post) { postRepository.delete(post); }
    public void update(Post post) {
        postRepository.update(post);
    }



}
