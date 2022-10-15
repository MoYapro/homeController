package de.moyapro.homecontroller.communication.tv

import android.util.Log
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties

const val TAG = "request"
fun request(
    command: TVCommand,
    successAction: (String) -> Unit = {},
) {
    command.url
        .httpPost()
        .header(command.headers)
        .body(command.value)
        .response { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
//                    Log.e(TAG, "error when sending request", ex)
                }
                is Result.Success -> {
                    Log.d(TAG, "got ${String(result.get())}")
                    val data = result.get()
                    successAction.invoke(String(data))
                }
            }
        }
}

fun buildXmlHeader(password: String): Map<String, Any> {
    return mapOf(
        Pair("X-Auth-PSK", password),
        Pair("SOAPACTION", "\"urn:schemas-sony-com:service:IRCC:1#X_SendIRCC\""),
        Pair("Content-Type", "text/xml; charset=UTF-8")
    )
}

fun buildJsonHeader(password: String): Map<String, Any> {
    return mapOf(
        Pair("X-Auth-PSK", password),
        Pair("Content-Type", "application/json; charset=UTF-8")
    )
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TVCommand private constructor(
    val value: String,
    val url: String,
    val headers: Map<String, Any>,
) {
    constructor(
        tvCommandEnum: TVCommandEnum,
        commandParameter: String,
        connectionProperties: ConnectionProperties,
    ) : this(
        tvCommandEnum.contentGenerator.invoke(commandParameter),
        tvCommandEnum.urlGenerator(connectionProperties.ip),
        if (TVCommandEnum.IRCC == tvCommandEnum)
            buildXmlHeader(connectionProperties.password)
        else
            buildJsonHeader(connectionProperties.password)
    )

    constructor(
        tvStatusEnum: TvStatusEnum,
        connectionProperties: ConnectionProperties,
    ) : this(
        tvStatusEnum.content,
        tvStatusEnum.urlGenerator(connectionProperties.ip),
        buildJsonHeader(connectionProperties.password)
    )

}
