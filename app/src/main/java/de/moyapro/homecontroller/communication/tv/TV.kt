package de.moyapro.homecontroller.communication.tv

import android.content.SharedPreferences
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
    val headers: Map<String, Any>
) {
    constructor(
        tvCommandEnum: TVCommandEnum,
        commandParameter: String,
        preferences: SharedPreferences
    ) : this(
        tvCommandEnum.contentGenerator.invoke(commandParameter)
        , tvCommandEnum.urlGenerator(preferences.getString(SettingsKeys.IP, "192.168.1.1"))
        , if (TVCommandEnum.IRCC == tvCommandEnum)
            buildXmlHeader(preferences.getString(SettingsKeys.PASSWORD, "invalid"))
        else
            buildJsonHeader(preferences.getString(SettingsKeys.PASSWORD, "invalid"))
    )

    constructor(
        tvStatusEnum: TvStatusEnum,
        preferences: SharedPreferences
    ) : this(
        tvStatusEnum.content
        , tvStatusEnum.urlGenerator(preferences.getString(SettingsKeys.IP, "192.168.1.1"))
        , buildJsonHeader(preferences.getString(SettingsKeys.PASSWORD, "invalid"))
    )

}
