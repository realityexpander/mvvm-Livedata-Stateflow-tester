package com.devtides.mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Observer
import com.devtides.mvvm.R
import com.devtides.mvvm.databinding.ActivityMainBinding
import com.devtides.mvvm.ui.FlowExperimentTheme
import com.devtides.mvvm.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)


//        setContentView(bind.root)
//        observeViewModel()

        setContent {
            FlowExperimentTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Render(viewmodel)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewmodel.itemLD.observe(this, Observer { itemsList ->
            bind.imageView.setImageResource(R.drawable.ic_ok)
            bind.textView.text = "Received ${itemsList.size} items"
            bind.textView.visibility = View.VISIBLE
        })


        bind.triggerLivePostDataBtn.setOnClickListener {
            viewmodel.triggerLivePostData()
        }
        viewmodel.liveDataPost.observe(this, Observer { item ->
            bind.textView2.text = "Received ${item}"
        })


        bind.triggerStateFlowBtn.setOnClickListener {
            viewmodel.triggerStateFlow()
        }
//        viewmodel.stateFlow.collectAsState() { item ->
//            bind.textView3.text = "Received ${item}"
//        }


        bind.triggerLiveSetWithContextDataBtn.setOnClickListener {
            viewmodel.triggerLiveSetWithContextData()
        }
        viewmodel.liveDataSetWithContext.observe(this, Observer { item ->
            bind.textView4.text = "Received ${item}"
        })


        bind.triggerLiveSetLaunchBtn.setOnClickListener {
            viewmodel.triggerLiveSetLaunch()
        }
        viewmodel.liveDataSetLaunch.observe(this, Observer { item ->
            bind.textView5.text = "Received ${item}"
        })
    }

    fun getItems(v: View) {
        viewmodel.getItems()
    }

    @Composable
    fun Render(viewModel: MainViewModel) {

        val liveDataPostResult = viewModel.liveDataPost.observeAsState()
        val liveDataSetWithContextResult = viewModel.liveDataSetWithContext.observeAsState()
        val liveDataSetLaunchResult = viewModel.liveDataSetLaunch.observeAsState()
        val liveDataFlowResult = viewModel.stateFlow.collectAsState()

        Column {
            Button(onClick = { viewModel.triggerStateFlow() }) {
                Text(text = "Click Me!")
            }
            Text(text = "StateFlow: ${liveDataFlowResult.value}")

            Button(onClick = { viewModel.triggerLivePostData() }) {
                Text(text = "Click Me!")
            }
            Text(text = "LiveData Post ${liveDataPostResult.value}")

            Button(onClick = { viewModel.triggerLiveSetWithContextData() }) {
                Text(text = "Click Me!")
            }
            Text(text = "LiveData Set WithContext ${liveDataSetWithContextResult.value}")

            Button(onClick = { viewModel.triggerLiveSetLaunch() }) {
                Text(text = "Click Me!")
            }
            Text(text = "LiveData Set Launch ${liveDataSetLaunchResult.value}")
        }
    }

}
