package com.example.jobmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobmanager.R
import com.example.jobmanager.model.Job
import com.example.jobmanager.presenter.JobPresenter

class JobAdapter(
    private var context: Context, private var listJob: ArrayList<Job>, private var jobPresenter: JobPresenter
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {
    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(job: Job) {
            val time: TextView = itemView.findViewById(R.id.time)
            val content: TextView = itemView.findViewById(R.id.content)
            val drop: ImageView = itemView.findViewById(R.id.drop)
            var isExpand = false
            time.text = job.time
            content.text = job.content
            drop.setOnClickListener {
                if (!isExpand) {
                    content.isSingleLine = false
                    drop.setImageResource(R.drawable.ic_drop_up)
                    isExpand = true
                } else {
                    content.isSingleLine = true
                    drop.setImageResource(R.drawable.ic_drop_down)
                    isExpand = false
                }
            }
        }
    }

    fun deleteJob(pos: Int) {
        listJob.removeAt(pos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): JobViewHolder {
        return JobViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_item_job, p0, false)
        )
    }

    override fun getItemCount(): Int = listJob.size

    override fun onBindViewHolder(p0: JobViewHolder, p1: Int) {
        p0.bindData(listJob[p1])
        val delete: ImageView = p0.itemView.findViewById(R.id.delete)
        delete.setOnClickListener {
            jobPresenter.deleteJob(listJob[p1], p1)
        }
    }

}