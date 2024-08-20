package dev.erdragh.atmosphereapi

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.resources.ResourceKey

data class Gas(val name: String) {
    companion object {
        val CODEC: Codec<Gas> = RecordCodecBuilder.create {
            it.group(
                Codec.STRING.fieldOf("name").forGetter(Gas::name)
            ).apply(it, ::Gas)
        }

        fun translationKey(key: ResourceKey<Gas>): String {
            return key.location().toLanguageKey(GasRegistry.GAS_REGISTRY_KEY.location().toLanguageKey())
        }
    }
}
