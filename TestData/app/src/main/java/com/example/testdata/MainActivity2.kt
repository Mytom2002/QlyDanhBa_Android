package com.example.testdata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testdata.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent?.extras?.getInt("position")
        val name = intent?.extras?.getString("name").toString()
        val sdt = intent?.extras?.getString("sdt").toString()


        val name1 = findViewById<TextView>(R.id.titleName)
        val sdt1 = findViewById<TextView>(R.id.titleSDT)
        name1.setText(name)
        sdt1.setText(sdt)

        val buttonCall = findViewById<Button>(R.id.buttonCall)
        buttonCall.setOnClickListener {
            val callIntent: Intent = Uri.parse("tel:" + sdt).let { number ->
                Intent(Intent.ACTION_DIAL, number)
            }
            startActivity(callIntent)
        }

        val buttonMessage = findViewById<Button>(R.id.buttonMessage)
        buttonMessage.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sdt, null))
            intent.putExtra("smsbody", sdt)
            startActivity(intent)
        }

        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        buttonUpdate.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("position", position)
            intent.putExtra("name", name)
            intent.putExtra("sdt", sdt)
            startActivity(intent)
            finish()
        }
    }


}






