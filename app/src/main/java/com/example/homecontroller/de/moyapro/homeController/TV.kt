package com.example.homecontroller.de.moyapro.homeController

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

fun request(command: TVCommand) {
    Thread {
        val (request, response, result) = command.url()
            .httpPost()
            .header(Pair("X-Auth-PSK", "Superteam17"))
            .body(command.value())
            .responseString()

        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println(ex)
            }
            is Result.Success -> {
                val data = result.get()
                println(data)
            }
        }
    }.start()
}

class TVCommand(private val tvCommandEnum: TVCommandEnum, private val commandParameter: String) {

    fun value(): String {
        return tvCommandEnum.contentGenerator.invoke(commandParameter)
    }

    fun url(): String {
        return tvCommandEnum.url
    }

}

enum class TVCommandEnum(val contentGenerator: (String) -> String, val url: String) {
    POWER(
        { value: String -> "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"status\":${value}}]}" },
        "http://192.168.1.111/sony/system"
    ),
    VOLUME(
        { value: String -> "{\"method\":\"setAudioVolume\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"target\":\"speaker\",\"volume\":\"$value\"}]}" },
        "http://192.168.1.111/sony/audio"
    ),
    HDMI(
        { value: String -> "{\"method\":\"setPlayContent\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"uri\":\"extInput:hdmi?port=$value\"}]}" },
        "http://192.168.1.111/sony/avContent"
    )
}