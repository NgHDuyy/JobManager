package com.example.jobmanager.presenter

import android.app.AlarmManager
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.example.jobmanager.R
import com.example.jobmanager.activity.AddJobActivity
import com.example.jobmanager.broadcastReceiver.*
import com.example.jobmanager.database.JobDatabase
import com.example.jobmanager.interfaces.JobInterface
import com.example.jobmanager.model.Job
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class JobPresenter(private val context: Context, private val jobInterface: JobInterface) {

    private val db = Room.databaseBuilder(
        context,
        JobDatabase::class.java, "jobs"
    ).allowMainThreadQueries().build()

    fun addJob(job: Job) {
        createNotificationChannel()
        scheduleNotification(job)
        db.jobDao().insertJob(job)
    }

    private fun scheduleNotification(job: Job) {
        val intent = Intent(context, Notification::class.java)
        intent.putExtra(title, job.time)
        intent.putExtra(message, job.content)
        intent.putExtra(notificationID, job.id)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            job.id!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("dd/MM/yyyy hh:mm").parse(job.time!!) as Date
        alarmManager.setExactAndAllowWhileIdle( AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun createNotificationChannel() {
        val name = "notif channel"
        val desc = "Description of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun getAllJob(): ArrayList<Job> {
        return db.jobDao().getAllJob() as ArrayList<Job>
    }

    fun deleteJob(job: Job, pos: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_custom_dialog)

        val window = dialog.window ?: return

        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setGravity(Gravity.BOTTOM)

        dialog.setCancelable(false)

        val title: TextView = dialog.findViewById(R.id.question)
        val cancel: TextView = dialog.findViewById(R.id.cancel)
        val accept: TextView = dialog.findViewById(R.id.accept)

        title.text = "Xóa nhắc nhở?"

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        accept.setOnClickListener {
            db.jobDao().delete(job)
            jobInterface.acceptDelete(pos)
            Toast.makeText(context, "Đã xóa!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun nextActivity() {
        context.startActivity(Intent(context, AddJobActivity::class.java))
    }
}