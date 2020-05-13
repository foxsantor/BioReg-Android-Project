package com.example.bioregproject.entities;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "account_table")
@TypeConverters(DateConverter.class)
public class Account {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private byte[] profileImage;
    private String firstName;
    private String lastName;
    private String password;
    private Date lastLoggedIn;
    private Date creationDate;
    private String email;
    private long phoneNumber;
    private int Selected;


    public Account() {
        super();
    }

    @Ignore
    public Account(Date creationDate,byte[] profileImage, String firstName, String lastName, String password, Date lastLoggedIn, String email, long phoneNumber) {
        this.creationDate = creationDate;
        this.profileImage = profileImage;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.lastLoggedIn = lastLoggedIn;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getSelected() {
        return Selected;
    }

    public void setSelected(int selected) {
        Selected = selected;
    }
}
