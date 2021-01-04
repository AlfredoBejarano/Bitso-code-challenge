package me.alfredobejarano.bitsocodechallenge.utils

import androidx.annotation.StringRes

private const val DAI = "dai"
private const val GOLEM = "gnt"
private const val RIPPLE = "xrp"
private const val BITCOIN = "btc"
private const val ETHEREUM = "eth"
private const val LITECOIN = "ltc"
private const val TRUEUSD = "tusd"
private const val BITCOIN_CASH = "bch"
private const val DECENTRALAND = "mana"
private const val BASIC_ATTENTION_TOKEN = "bat"

fun String?.getCryptoName() = this?.split("_")?.first()?.let { coinName ->
    when (coinName) {
        DAI -> "Dai"
        GOLEM -> "Golem"
        RIPPLE -> "Ripple"
        TRUEUSD -> "TrueUSD"
        BITCOIN -> "Bitcoin"
        ETHEREUM -> "Ethereum"
        LITECOIN -> "Litecoin"
        BITCOIN_CASH -> "Bitcoin Cash"
        DECENTRALAND -> "Decentraland"
        BASIC_ATTENTION_TOKEN -> "Basic Attention Token"
        else -> coinName
    }
} ?: run {
    ""
}