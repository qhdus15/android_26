package com.example.android_26.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android_26.R


class BoardWriteActivity : AppCompatActivity() {

//    private lateinit var binding : ActivityBoardWriteBinding
//
//    private val TAG = BoardWriteActivity :: class.java

        private var isImageUpload = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)
//
//        binding.writeBtn.setOnClickListener{
//            val title = binding.titleArea.text.toString()
//            val content = binding.contentArea.text.toString()
//            val uid = FBAuth.getUid()
//            val time = FBAuth.getTime()
//
//            Log.d(TAG, title)
//            Log.d(TAG, content)
//            //Log.d(TAG, time)
//
//            val key = FBRef.boardRef.push().key.toString()
//            FBRef.boardRef
//                .child(key)
//                .setValue(BoardModel(title,content,uid,time))
//        Toast.makeText(this,"게시글 입력 완료",Toast.LENGHT_LONG).show()
        if(isImageUpload==true){
                imageUpload(key)
        }
//
//            finish()
//
//        }
//        binding.imageArea.setOnClickListner{
//                val gallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//                startActivityForResult(gallery,100)
        isImageUpload=true
//        }
//}
        private fun imageUpload(key:String){
                val storage = Firebase.storage
                val storageRef = storage.reference
                val mountainsRef = storageRef.child(key+".png")

                val imageView = binding.imageArea


}        }


//        override fun OnActivityResult(requestCode:Int, resultCode: Int, data:Intent?){
//                super.onActivityResult(requestCode,resultCode,data)
//                if(resultCode==RESULT_OK&&requestCode==100){
//                        binding.imageArea.setImageURI(data?.data)
//
//                }
//        }
//}


