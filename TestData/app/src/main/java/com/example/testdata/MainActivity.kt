package com.example.testdata

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testdata.adapter.TaskAdapter
import com.example.testdata.data.AllData
import com.example.testdata.databinding.ActivityMainBinding
import com.example.testdata.model.Task
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var allData: AllData

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        adapter = TaskAdapter(allData.listTask)
        binding.listTaskRecylerView.adapter = adapter
        binding.listTaskRecylerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val buttonAdd = findViewById<Button>(R.id.buttonAddTask)
        buttonAdd.setOnClickListener { this.createNewTask() }
//
        val buttonRemove = findViewById<Button>(R.id.buttonRemoveTask)
        buttonRemove.setOnClickListener { this.removeLastTask() }

        search()
        delete()
    }


    private fun loadData() {
        var pref = getSharedPreferences("database", MODE_PRIVATE)
        var jsonData = pref.getString("allTask", null)
        if (jsonData != null) {
            this.allData = Gson().fromJson(jsonData, AllData::class.java)
        } else {
            this.allData = AllData()
        }
    }

    private fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun delete() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                allData.listTask.removeAt(position)
                binding.listTaskRecylerView.adapter?.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.listTaskRecylerView)
    }

    private fun filterList(query: String?) {
        if (query != null) {
            var filteredList = ArrayList<Task>()
            for (i in allData.listTask) {
                if (i.sdt.lowercase().contains(query) || i.name.lowercase().contains(query))
                {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun saveData() {
        var pref = getSharedPreferences("database", MODE_PRIVATE)

        val gson = Gson()
        val json = gson.toJson(allData)
        pref.edit().putString("allTask", json).commit()
    }

    private fun createNewTask() {
        val sdt = findViewById<EditText>(R.id.textsdt)
        val name = findViewById<EditText>(R.id.textname)

        this.allData.addTask(Task(name.text.toString(), sdt.text.toString(), Task.OPEN))
        saveData()
        adapter.setFilteredList(this.allData.listTask)
        loadData()
    }

    private fun removeLastTask() {
        allData.listTask.removeAt(allData.listTask.size - 1)
        saveData()
        adapter.setFilteredList(this.allData.listTask)
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
        adapter.setFilteredList(allData.listTask)
    }
}
