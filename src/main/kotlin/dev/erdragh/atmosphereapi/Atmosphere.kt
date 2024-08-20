package dev.erdragh.atmosphereapi

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Tuple

data class GasAmount(val gas: ResourceKey<Gas>, val amount: Double)

data class Atmosphere(val gasses: List<GasAmount>) {
    companion object {
        private val GAS_USAGE_CODEC = RecordCodecBuilder.create {
            it.group(
                ResourceKey.codec(GasRegistry.GAS_REGISTRY_KEY).fieldOf("gas").forGetter(GasAmount::gas),
                Codec.DOUBLE.fieldOf("amount").forGetter(GasAmount::amount)
            ).apply(it, ::GasAmount)
        }
        val CODEC = RecordCodecBuilder.create {
            it.group(
                GAS_USAGE_CODEC.listOf().fieldOf("gasses").forGetter(Atmosphere::gasses)
            ).apply(it, ::Atmosphere)
        }

        fun getAverageDensity(level: ServerLevel): Double {
            val atmosphere: Atmosphere = level.registryAccess().registryOrThrow(Registries.DIMENSION).wrapAsHolder(level)
                .getData(AtmosphereDatamap.ATMOSPHERE_DATA) ?: throw IllegalStateException("Atmosphere datamap not available")
            val gasses = atmosphere.gasses
            val gasRegistry = level.registryAccess().registryOrThrow(GasRegistry.GAS_REGISTRY_KEY)
            val fetchedGasses = gasses.map { Tuple(gasRegistry.getOrThrow(it.gas), it.amount) }
            return fetchedGasses.sumOf { it.a.density * it.b } / fetchedGasses.sumOf { it.b }
        }
    }
}
