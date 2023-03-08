package com.example.testdata

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.testdata.data.AllData
import com.example.testdata.model.Task
import com.google.gson.Gson

class MainActivity3 : AppCompatActivity() {
    lateinit var allData: AllData
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        pref = getSharedPreferences("database", MODE_PRIVATE)
        loadData()
        val position = intent?.extras?.getInt("position")
        val name = intent?.extras?.getString("name").toString()
        val sdt = intent?.extras?.getString("sdt").toString()


        val name1 = findViewById<EditText>(R.id.textname1)
        val sdt1 = findViewById<EditText>(R.id.textsdt1)

        name1.setText(name)
        sdt1.setText(sdt)

        val buttonok = findViewById<Button>(R.id.buttonOK)
        buttonok.setOnClickListener {
            this.allData.updateTask(position!!, Task(name1.text.toString(), sdt1.text.toString(), Task.OPEN))
            saveData()
            finish()
        }
    }

    private fun loadData() {
        var jsonData = pref.getString("allTask", null)
        if (jsonData != null) {
            this.allData = Gson().fromJson(jsonData, AllData::class.java)
        } else {
            this.allData = AllData()
        }
    }

    private fun saveData() {
        val gson = Gson()
        val json = gson.toJson(allData)
        pref.edit().putString("allTask", json).commit()
    }
}