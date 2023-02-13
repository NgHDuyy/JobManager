package com.example.jobmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jobmanager.model.Job

@Database(entities = [Job::class], version = 1)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDAO
}