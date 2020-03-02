package com.example.bioregproject.entities.Realtions;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.ProductLogs;

public class LogsAndUser {

    @Embedded public Account account;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id"
    )
    public ProductLogs productLogs;
}
