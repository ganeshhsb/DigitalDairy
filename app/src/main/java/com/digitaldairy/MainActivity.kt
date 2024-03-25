package com.digitaldairy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitaldairy.compose.appcomponents.AppText
import com.digitaldairy.labour.LabourActivity
import com.digitaldairy.labour.theme.HelloComposeTheme
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // testSingle()
        setContent {
            HelloComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

//fun testSingle(){
//    val list = arrayListOf<Single<Int>>()
//    list.add(getError())
//    for (i in 1..5){
//        list.add(getSingleInteger(i))
//    }
//    list.add(getError())
//    Single.mergeDelayError(list).flatMap {
//        getSingleFloat(it).map {
//            it*100
//        }
//    }.subscribe({ it->
//        Log.d("Random", "testSingle: "+it)
//    },{it->
//        Log.d("Random", "Error testSingle: "+it)
//    },{
//        Log.d("Random", "Error completed")
//    }).dispose()
//}
//
//fun getError(): Single<Int> {
//    return Single.error(Throwable("Test"))
//}

fun getSingleInteger(i:Int): Single<Int> {
    return Single.create { emitter ->
        val random = Random(100).nextInt(0,100)
        Log.d("Random", "getSingleInteger: "+i)
        emitter.onSuccess(i)
    }
}

fun getSingleFloat(i:Int): Observable<Int> {
    return Observable.just(i)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current


    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier.weight(1f).height(100.dp).padding(10.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .align(alignment = Alignment.CenterVertically).clickable {
                        val intent = Intent(context, LabourActivity::class.java)
                        context.startActivity(intent)
                    }
            ) {
                AppText(
                    "Labour",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
                )
            }
            Box(
                modifier = Modifier.weight(1f).height(100.dp).padding(10.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .align(alignment = Alignment.CenterVertically)
            ) {
                AppText(
                    "Schedule",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloComposeTheme {
        Greeting("Android")
    }
}