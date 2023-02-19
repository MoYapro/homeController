package de.moyapro.homecontroller.util

import com.github.kittinunf.fuel.core.FoldableRequestInterceptor
import com.github.kittinunf.fuel.core.FoldableResponseInterceptor
import com.github.kittinunf.fuel.core.RequestTransformer
import com.github.kittinunf.fuel.core.ResponseTransformer


object RequestLoggingInterceptor : FoldableRequestInterceptor {

    override fun invoke(next: RequestTransformer): RequestTransformer {
        return { request ->
            println("send request $request")
            next(request)
        }
    }
}

object ResponseLoggingInterceptor : FoldableResponseInterceptor {
    override fun invoke(next: ResponseTransformer): ResponseTransformer {
        return { request, response ->
            println("got response $response")
            next(request, response)
        }
    }
}
