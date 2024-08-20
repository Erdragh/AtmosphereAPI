package dev.erdragh.atmosphereapi

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

data class Gas(val name: String) {
    companion object {
        val CODEC: Codec<Gas> = RecordCodecBuilder.create {
            it.group(
                Codec.STRING.fieldOf("name").forGetter(Gas::name)
            ).apply(it, ::Gas)
        }
    }
}
