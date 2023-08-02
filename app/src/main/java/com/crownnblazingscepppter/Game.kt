package com.crownnblazingscepppter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlin.random.Random

class Game : AppCompatActivity() {

    private val drawableList = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img7,
        R.drawable.img8,
    )

    private val itemsView = arrayListOf<ImageView>()
    private val itemsCurrentId = arrayListOf<Int>()
    private val itemsCorrectId = arrayListOf<Int>()

    private var isGame = false


    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        itemsView.add(findViewById(R.id.img1))
        itemsView.add(findViewById(R.id.img2))
        itemsView.add(findViewById(R.id.img3))

        itemsView.forEachIndexed { ind, img ->
            var startPos = 0
            img.setOnTouchListener { _, event ->
                if (isGame) {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startPos = event.x.toInt()
                        }

                        MotionEvent.ACTION_UP -> {
                            if (event.x > startPos) {
                                itemsCurrentId[ind]++
                                if (itemsCurrentId[ind] > drawableList.indices.last()) itemsCurrentId[ind] =
                                    0
                                img.setImageDrawable(getDrawable(drawableList[itemsCurrentId[ind]]))
                            }
                            if (event.x < startPos) {
                                itemsCurrentId[ind]--
                                if (itemsCurrentId[ind] < 0) itemsCurrentId[ind] = drawableList.indices.last()
                                img.setImageDrawable(getDrawable(drawableList[itemsCurrentId[ind]]))
                            }
                        }
                    }
                }
                return@setOnTouchListener true
            }
        }

        shuffle(View(applicationContext))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun shuffle(view: View) {
        itemsCorrectId.clear()
        itemsView.forEach { img ->
            val drawableId = drawableList.indices.random()
            itemsCorrectId.add(drawableId)
            img.setImageDrawable(getDrawable(drawableList[drawableId]))
        }
        findViewById<Button>(R.id.play).visibility = View.VISIBLE
        isGame = false
        Toast.makeText(applicationContext, "Remember this combination", Toast.LENGTH_SHORT).show()
    }

    fun validCheck(view: View) {
        if (isGame) {
            var isEquals = true
            for (i in 0..2) {
                if (itemsCurrentId[i] != itemsCorrectId[i]) isEquals = false
            }
            if (isEquals) {
                Toast.makeText(applicationContext, "Great! Is Equals!", Toast.LENGTH_SHORT).show()
                shuffle(View(applicationContext))
            }
            else {
                Toast.makeText(applicationContext, "No Equals...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun play(view: View) {
        itemsCurrentId.clear()
        itemsView.forEach { img ->
            val drawableId = drawableList.indices.random()
            itemsCurrentId.add(drawableId)
            img.setImageDrawable(getDrawable(drawableList[drawableId]))
        }
        view.visibility = View.GONE
        isGame = true
    }
}