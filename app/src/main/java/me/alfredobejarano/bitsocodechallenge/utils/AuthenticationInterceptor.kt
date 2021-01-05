package me.alfredobejarano.bitsocodechallenge.utils

import me.alfredobejarano.bitsocodechallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.math.BigInteger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class AuthenticationInterceptor : Interceptor {
    private companion object {
        const val SIG_NUM = 1
        const val ALGORITHM_NAME = "HmacSHA256"
        const val AUTHORIZATION_HEADER_PREFIX = "Bitso"
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizationHeader = generateAuthorizationHeader(chain.request())
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER_NAME, authorizationHeader)
            .build()
        return chain.proceed(request)
    }

    private fun generateAuthorizationHeader(request: Request): String {
        val apiKey = BuildConfig.BITSO_API_KEY
        val nonce = System.currentTimeMillis()
        val signature = generateSignature(nonce, request)
        return "$AUTHORIZATION_HEADER_PREFIX $apiKey:$nonce:$signature"
    }

    private fun generateSignature(nonce: Long, request: Request): String {
        val candidate = "$nonce${request.method}${request.url.encodedPath}${request.bodyPayload}"

        val sha256Result = encryptToSHA256(candidate)
        val localBigInteger = BigInteger(SIG_NUM, sha256Result)

        return String.format("%0" + (sha256Result.size shl 1) + "x", localBigInteger)
    }

    private val Request.bodyPayload
        get() = Buffer().run {
            body?.writeTo(this)
            readUtf8()
        }

    private fun encryptToSHA256(candidate: String) = Mac.getInstance(ALGORITHM_NAME).apply {
        init(SecretKeySpec(BuildConfig.BITSO_SECRET_KEY.toByteArray(), ALGORITHM_NAME))
    }.doFinal(candidate.toByteArray())
}