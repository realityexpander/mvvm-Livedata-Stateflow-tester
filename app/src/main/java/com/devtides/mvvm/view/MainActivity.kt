package com.devtides.mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.devtides.mvvm.R
import com.devtides.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewmodel.itemLD.observe(this, Observer { itemsList ->
            imageView.setImageResource(R.drawable.ic_ok)
            textView.setText("Received ${itemsList.size} items")
            textView.visibility = View.VISIBLE
        })
    }

    fun getItems(v: View) {
        viewmodel.getItems()
    }
}
