package com.zestworks.websocketsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()
    private val websocketClient = WebsocketClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request = Request.Builder()
            .url("wss://echo.websocket.org")
            .build()
        okHttpClient.newWebSocket(request, websocketClient)
        button.setOnClickListener {
            val message = editText.text.toString()
            websocketClient.sendMessage(JSONObject().put("message", message).toString())
        }
    }
}

class WebsocketClient: WebSocketListener() {
    private var websocket: WebSocket? = null
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d("Message onClosed", reason)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d("Message onClosing", reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d("Message onFailure", response.toString())
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d("Message received text", text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d("Message received bytes", bytes.hex())
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d("Message onOpen", response.toString())
        this.websocket = webSocket
    }

    fun sendMessage(message: String) {
        websocket?.send(message)
    }
}
