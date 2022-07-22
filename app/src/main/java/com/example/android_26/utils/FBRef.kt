package com.example.android_26.utils

class FBRef {

    companion object{
        private val database = Firebase.database

        val category1 = database.getReference("contents")
        val category2 = database.getRegerence("contents2")

        val bookmarkRef = database.getReference("bookmark_list")

        val boardRef = database.getReference("board")

        val commentRef = database.getReference("comment")

    }
}