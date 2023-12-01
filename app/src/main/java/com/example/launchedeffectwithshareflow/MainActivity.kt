package com.example.launchedeffectwithshareflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchedeffectwithshareflow.ui.theme.LaunchedEffectWithShareFlowTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffectWithShareFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}
class MyViewModel : ViewModel() {
    var _clickEvent: MutableSharedFlow<Unit?> = MutableSharedFlow()
    var clickEvent: SharedFlow<Unit?> = _clickEvent
    fun onClick() {
        viewModelScope.launch {
            _clickEvent.emit(Unit)
        }
    }
}

@Composable
fun Greeting(
    viewModel: MyViewModel = viewModel()
) {
    SubView(viewModel.clickEvent, viewModel::onClick)
}

@Composable
fun SubView(
    clickEvent: Flow<Unit?>? = null,
    callBack: () -> Unit = { println("callBack") }
) {
    LaunchedEffect(Unit) {
        clickEvent?.collect { e ->
            println("clickEvent=$e")
        }
    }

    Button(onClick = { callBack() }) {
        Text(text = "Button")
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LaunchedEffectWithShareFlowTheme {
        Greeting()
    }
}