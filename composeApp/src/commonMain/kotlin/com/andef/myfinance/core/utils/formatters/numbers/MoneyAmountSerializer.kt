package com.andef.myfinance.core.utils.formatters.numbers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.math.roundToLong

object MoneyAmountSerializer : KSerializer<Long> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MoneyAmount", PrimitiveKind.DOUBLE)

    override fun deserialize(decoder: Decoder): Long {
        return (decoder.decodeDouble() * 100).roundToLong()
    }

    override fun serialize(encoder: Encoder, value: Long) {
        encoder.encodeDouble(value / 100.0)
    }
}
