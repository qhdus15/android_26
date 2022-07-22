//package com.example.android_26.utils
//
//import java.util.*
//
////파이어베이스 인듯 ?ㅅㅂ
//class FBAuth {
//    companion object{
//        private lateinit var auth : FirebaseAuth
//
//        fun getUid():String{
//            auth = FirebaseAuth.getInstance()
//            return auth.currentUser?.uid.toString()
//        }
//        fun getTime() : String{
//            val currentDataTime = Calendar.getInstance().time
//            val dataFormat = SimpleDataFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDataTime)
//
//            return dataFormat
//        }
//    }
//}