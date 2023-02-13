package com.example.jobmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
class Job(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "time")
    var time: String?,
    @ColumnInfo(name = "content")
    var content: String?
)