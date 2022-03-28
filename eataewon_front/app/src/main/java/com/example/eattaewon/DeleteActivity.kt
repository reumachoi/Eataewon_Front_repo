package com.example.eattaewon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DeleteActivity : AppCompatActivity(), View.OnClickListener {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_delete)

            val deletebtn = findViewById<Button>(R.id.deletebtn)
            val deletId = findViewById<EditText>(R.id.deletId)
            deletId.setOnClickListener(this)
            deletebtn.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val deletId = findViewById<EditText>(R.id.deletId)
            when (view?.id) {
      /*
                R.id.deletebtn -> {
                    val id = deletId.text.toString().toInt()
                    DBHelper.getInstance(this, "Member.db").delete(id)
                    Toast.makeText(this, "title : ${id} / 삭제완료", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            */
            }
        }
    }