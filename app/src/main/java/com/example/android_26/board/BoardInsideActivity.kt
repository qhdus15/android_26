package com.example.android_26.board

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.android_26.R
import com.example.android_26.comment.CommentListViewAdaptetval
import com.example.android_26.comment.CommentModel
import com.example.android_26.utils.FBRef
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() {
    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var key:String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentListViewAdaptetval

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSettingIcon.setOnClickListener{
            showDialog()
        }

        //두번째 방법
        key = intent.getStringExtra("key").toString()
        //Toast.makeText(this, key, Toast.LENGTH_LONG).show()

        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListnener{
            insertcomment(key)
        }
        getCommentData(key)

        commentAdapter = CommentListViewAdaptetval(commentDataList)
        binding.commentLV.adapter = commentAdapter

    }

    fun insertcomment(key:String){
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(binding.commentArea.text.toString(),
                FBAuth.getTime()
            )
            )

    }
    private fun getBoardData(key:String){
        val postListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){

                try{
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    Log.d(TAG,dataModel!!.title)

                    binding.titleArea.text=dataModel!!.title
                    binding.textArea.text=dataModel!!.content
                    binding.timeArea.text=dataModel!!.time

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)){
                        Toast.makeText(baseContext,"내가 글쓴이임", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(baseContext,"내가 글쓴이아님", Toast.LENGTH_LONG).show()

                    }

                }catch (e:Exception){
                    Log.d(TAG,"삭제완료")
                }

            }

            override fun onCancelled(databaseError:DatabaseError){
                Log.w(TAG,"loadPost:onCancelled",databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListnener(postListener)
    }

    private fun getImageData(key:String){
        val storageReference = Firebase.storage.reference.child(key+".png")
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener{task->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(storageReference)
                    .into(imageView)
            }else{
                binding.getImageArea.isvisible = false
            }
        })

    }

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alterDialog = mBuilder.show()
        alterDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this,"수정버튼을 눌렀습니다",Toast.LENGTH_LONG).show()

            val intent = Intent(this,BoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)
        }
        alterDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            FBRef.bookmarkRef.child(key).removeValue()
            Toast.makeText(this,"삭제완료",Toast.LENGTH_LONG).show()
            finish()
        }

    }

    fun getCommentData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }

                commentAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)


    }


}

