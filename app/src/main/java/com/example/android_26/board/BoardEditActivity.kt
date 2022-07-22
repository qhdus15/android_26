package com.example.android_26.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android_26.R
import com.example.android_26.utils.FBRef
import java.lang.Exception

class BoardEditActivity : AppCompatActivity() {

    private lateinit var key: String

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String

    private lateinit var binding: ActivityBoardInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)
        editBoardData(key)

    }

    private fun editBoardData(key:String){

        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(binding.titleArea.text.toString(),
                writerUid,
                binding.conetentArea.text.toString(),
                FBAuth.gettime())
            )
        Toast.makeText(this,"수정완료",Toast.LENGTH_LONG).show()
        finish()

    }

    private fun getImageData(key: String) {
        val storageReference = Firebase.storage.reference.child(key + ".png")
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(storageReference)
                    .into(imageView)
            } else {

            }
        })

    }


    private fun getBoardData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
                writerUid = dataModel!!.uid
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListnener(postListener)
    }
}