package com.example.testdata.data

import com.example.testdata.model.Task

data class AllData(
    var maxTaskId: Int = 0,
    var listTask: MutableList<Task> = mutableListOf()
) {
    fun addTask(task: Task) {
        maxTaskId += 1
        listTask.add(task)
    }

    fun updateTask(index: Int, task: Task) {
        listTask.removeAt(index)
        listTask.add(index, task)
    }
}
