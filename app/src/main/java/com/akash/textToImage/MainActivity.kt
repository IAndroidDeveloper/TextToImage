package com.akash.textToImage

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    var itemListLayout: ConstraintLayout? = null
    var txtName: EditText? = null
    var imgUser: ImageView? = null
    var btnSubmitButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progrss_bar)
        txtName = findViewById(R.id.edtName)
        itemListLayout = findViewById(R.id.itemList_layout)
        imgUser = findViewById(R.id.img_userImg)
        btnSubmitButton = findViewById(R.id.btnSubmitButton)

        clickListeners()
    }

    private fun clickListeners() {
        btnSubmitButton?.setOnClickListener {
            progressBar?.visibility = View.VISIBLE
            if (!txtName?.text.toString().isNullOrEmpty()) {
                val imageBite = getUserNameAsImage(this, txtName?.text.toString())
                progressBar?.visibility = View.GONE
                imgUser?.background =
                    ContextCompat.getDrawable(this, R.drawable.dashed_border)

                imgUser?.setImageBitmap(imageBite)
            }
        }

    }

    private fun getUserNameAsImage(context: Context, name: String?): Bitmap? {
        val first: String
        val spiltName = name?.split(" ")
        try {
            first = if (spiltName?.size!! > 1) {
                spiltName[0][0].uppercaseChar().toString() + spiltName[1][0].uppercaseChar()
                    .toString()
            } else {
                spiltName[0][0].uppercaseChar().toString()
            }
            return textAsBitmap(
                first, 85f, ContextCompat.getColor(context, R.color.primaryDarkColor)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun textAsBitmap(text: String?, textSize: Float, textColor: Int): Bitmap? {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT
        val baseline: Float = -paint.ascent() // ascent() is negative
        val width = (paint.measureText(text) + 80.85f).toInt() // round
        val height = (baseline + paint.descent() + 5.5f).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        if (text != null) {
            canvas.drawText(text, 34F, baseline, paint)
        }
        return image
    }

    override fun onBackPressed() {
        val alert = AlertDialog.Builder(this).create()
        alert.setTitle("Exit")
        alert.setMessage("Are you sure you want to exit?")
        alert.setIcon(android.R.drawable.ic_menu_close_clear_cancel)
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)
        alert.setButton(
            DialogInterface.BUTTON_POSITIVE, "OK"
        ) { dialog: DialogInterface?, which: Int -> finishAffinity() }
        alert.setButton(
            DialogInterface.BUTTON_NEGATIVE, "Cancel"
        ) { dialog: DialogInterface?, which: Int -> alert.dismiss() }
        alert.show()
    }
}