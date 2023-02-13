package com.example.jobmanager.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobmanager.adapter.JobAdapter
import com.example.jobmanager.databinding.ActivityMainBinding
import com.example.jobmanager.interfaces.JobInterface
import com.example.jobmanager.model.Job
import com.example.jobmanager.presenter.JobPresenter

class MainActivity : AppCompatActivity(), JobInterface {

    private lateinit var binding: ActivityMainBinding

    private var listJob: ArrayList<Job> = arrayListOf()

    private lateinit var jobPresenter: JobPresenter

    private lateinit var jobAdapter: JobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        jobPresenter = JobPresenter(this, this)

        setAdapter()

        binding.add.setOnClickListener {
            jobPresenter.nextActivity()
        }
    }

    private fun setAdapter() {
        listJob.clear()
        listJob = jobPresenter.getAllJob()
        jobAdapter = JobAdapter(this, listJob, jobPresenter)
        binding.rcvJob.layoutManager =
            LinearLayoutManager(this)
        binding.rcvJob.adapter = jobAdapter
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    override fun acceptDelete(pos: Int) {
        jobAdapter.deleteJob(pos)
    }

}