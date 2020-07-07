package com.zestworks.websocketsample

import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface EchoService {
    @Receive
    fun listen(): Flow<String>

    @Send
    fun ping(message: String)
}