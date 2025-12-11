package com.example.runa2b

data class Run (val id: String = "", var distance: Double, var name: String, var date: String) {

    constructor():this("", 0.0, "", "0000-00-00")
}