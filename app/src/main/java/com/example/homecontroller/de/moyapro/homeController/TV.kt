package com.example.homecontroller.de.moyapro.homeController

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

fun request(command: TVCommand) {
    command.url
        .httpPost()
        .header(Pair("X-Auth-PSK", "Superteam17"))
        .body(command.value)
        .response { request, response, result ->
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
        }
}

class TVCommand private constructor(val value: String, val url: String) {
    constructor(
        tvCommandEnum: TVCommandEnum,
        commandParameter: String
    ) : this(tvCommandEnum.contentGenerator.invoke(commandParameter), tvCommandEnum.url)

    constructor(statusEnum: TvStatusEnum) : this(statusEnum.content, statusEnum.url)

}
