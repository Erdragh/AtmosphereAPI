package dev.erdragh.atmosphereapi

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.resources.ResourceKey

/**
 * Data class representing a Gas. The data stored in here assumes [STP](https://en.wikipedia.org/wiki/Standard_temperature_and_pressure)
 */
data class Gas(
    /**
     * Density of the Gas in g/L
     */
    val density: Double
) {
    companion object {
        val CODEC: Codec<Gas> = RecordCodecBuilder.create {
            it.group(
                Codec.DOUBLE.fieldOf("density").forGetter(Gas::density)
            ).apply(it, ::Gas)
        }

        fun translationKey(key: ResourceKey<Gas>): String {
            return key.location().toLanguageKey(GasRegistry.GAS_REGISTRY_KEY.location().toLanguageKey())
        }
    }
}
