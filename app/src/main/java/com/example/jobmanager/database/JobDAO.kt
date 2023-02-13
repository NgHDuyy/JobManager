package com.example.jobmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jobmanager.model.Job

@Dao
interface JobDAO {

    @Insert
    fun insertJob(job: Job)

    @Query("SELECT * FROM jobs ORDER BY time")
    fun getAllJob(): List<Job>

    @Delete
    fun delete(job: Job)
}