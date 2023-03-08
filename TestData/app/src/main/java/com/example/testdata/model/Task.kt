package com.example.testdata.model

//data class Task(var id: Int, var title: String, var content: String, var state: Int) {
//    companion object STATE {
//        val OPEN: Int = 1
//        val DOING: Int = 2
//        val DONE: Int = 3
//    }
data class Task( var name: String, var sdt: String, var state: Int) {
    companion object STATE {
        val OPEN: Int = 1
        val DOING: Int = 2
        val DONE: Int = 3
    }
}
