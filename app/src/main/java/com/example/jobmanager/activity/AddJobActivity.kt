package com.example.jobmanager.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.jobmanager.databinding.ActivityAddJobBinding
import com.example.jobmanager.interfaces.JobInterface
import com.example.jobmanager.model.Job
import com.example.jobmanager.presenter.JobPresenter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class AddJobActivity : AppCompatActivity(), JobInterface {

    private lateinit var binding: ActivityAddJobBinding
    private lateinit var jobPresenter: JobPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jobPresenter = JobPresenter(this, this)

        setLayout()

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            binding.time.text =
                "${DecimalFormat("00").format(hourOfDay)}:${DecimalFormat("00").format(minute)}"
        }
        binding.datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            binding.day.text = "$dayOfMonth/$monthOfYear/$year"
        }

        binding.reset.setOnClickListener {
            setLayout()
        }

        binding.cancel.setOnClickListener {
            finish()
        }
        binding.accept.isEnabled = false
        binding.edtContent.addTextChangedListener {
            binding.accept.isEnabled = binding.edtContent.text != null
        }
        binding.accept.setOnClickListener {
            val time = binding.day.text.toString().plus(" ").plus(binding.time.text.toString())
            val content = binding.edtContent.text.toString()
            val str: String =
                binding.timePicker.hour.toString().plus(binding.timePicker.minute.toString())
                    .plus(binding.datePicker.dayOfMonth.toString())
                    .plus(binding.datePicker.month.toString())
            val id = str.toInt()

            Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show()

            val job = Job(id, time, content)
            jobPresenter.addJob(job)
            finish()
        }

    }

    private fun setLayout() {
        val cal = Calendar.getInstance()
        binding.timePicker.setIs24HourView(true)


        binding.timePicker.hour = cal.get(Calendar.HOUR_OF_DAY)
        binding.timePicker.minute = cal.get(Calendar.MINUTE)
        binding.datePicker.updateDate(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        binding.time.text = SimpleDateFormat("HH:mm").format(Date()).toString()
        binding.day.text = SimpleDateFormat("dd/MM/yyyy").format(Date()).toString()
    }

    override fun acceptDelete(pos: Int) {
    }
}