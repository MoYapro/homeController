package de.moyapro.homecontroller

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

fun request(
    command: TVCommand,
    successAction: (String) -> Unit = {}
) {
    command.url
        .httpPost()
        .header(command.headers)
        .body(command.value)
        .response { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    println(ex)
                }
                is Result.Success -> {
                    val data = result.get()
                    successAction.invoke(String(data))
                }
            }
        }
}

fun buildXmlHeader(): Map<String, Any> {
    return mapOf(
        Pair("X-Auth-PSK", "Superteam17"),
        Pair("SOAPACTION", "\"urn:schemas-sony-com:service:IRCC:1#X_SendIRCC\""),
        Pair("Content-Type", "text/xml; charset=UTF-8")
    )
}

fun buildJsonHeader(): Map<String, Any> {
    return mapOf(
        Pair("X-Auth-PSK", "Superteam17"),
        Pair("Content-Type", "application/json; charset=UTF-8")
    )
}

class TVCommand private constructor(
    val value: String,
    val url: String,
    val headers: Map<String, Any>
) {
    constructor(
        tvCommandEnum: TVCommandEnum,
        commandParameter: String
    ) : this(
        tvCommandEnum.contentGenerator.invoke(commandParameter)
        , tvCommandEnum.url
        , if (TVCommandEnum.IRCC == tvCommandEnum) buildXmlHeader() else buildJsonHeader()
    )
}
