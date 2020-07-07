package com.zestworks.websocketsample

import FlowStreamAdapterFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val okHttpClient = OkHttpClient()

    private val scarletInstance = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory("wss://echo.websocket.org"))
        .addStreamAdapterFactory(FlowStreamAdapterFactory())
        .build()

    private val echoService = scarletInstance.create<EchoService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            echoService.listen().collect {
                Log.d("collect one", it)
            }
        }

        GlobalScope.launch {
            echoService.listen().collect {
                Log.d("collect two", it)
            }
        }

        button.setOnClickListener {
            val message = editText.text.toString()
            val string = JSONObject().put("message", message).toString()
            echoService.ping(string)
        }
    }
}