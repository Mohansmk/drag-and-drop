package com.example.dragdrop2

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dragView = findViewById<View>(R.id.dragView)

        dragView.setOnLongClickListener {
            val clipText = "This is our clipText"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            it.visibility = View.INVISIBLE
            true
        }
    }


    val dragListener = View.OnDragListener { view, dragEvent ->
        {
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
                    view.invalidate()
                }

                DragEvent.ACTION_DRAG_LOCATION -> true

                DragEvent.ACTION_DRAG_EXITED -> {
                    view.invalidate()
                }

                DragEvent.ACTION_DROP -> {
                    val item = dragEvent.clipData.getItemAt(0)
                    val dragData = item.text
                    Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()
                    view.invalidate()

                    val v = dragEvent.localState as View
                    val owner = v.parent as ViewGroup
                    owner.removeView(v)
                    val destination = View as LinearLayout
                    v.visibility = View.VISIBLE
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    view.invalidate()
                }
            }

            else ->{

        }
        }
    }
}


