package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.bioregproject.entities.Post;
import java.util.List;

@Dao
public interface PostDAO {

    @Insert
    void insert(Post Post);

    @Delete
    void delete(Post Post);

    @Update
    void update(Post Post);

    @Query("DELETE FROM post_table")
    void deleteAllPost();

    @Query("SELECT * FROM post_table ORDER BY creation")
    LiveData<List<Post>> getAllPost();

    @Query("SELECT * from post_table where id = :id LIMIT 1")
    LiveData<List<Post>> loadPostById(long id);







}

