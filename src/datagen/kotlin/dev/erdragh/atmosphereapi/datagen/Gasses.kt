package dev.erdragh.atmosphereapi.datagen

import dev.erdragh.atmosphereapi.AtmosphereAPI
import dev.erdragh.atmosphereapi.Gas
import dev.erdragh.atmosphereapi.GasRegistry
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.resources.ResourceKey

private fun gas(id: String): ResourceKey<Gas> = ResourceKey.create(GasRegistry.GAS_REGISTRY_KEY, AtmosphereAPI.id(id))

val OXYGEN = gas("oxygen")
val NITROGEN = gas("nitrogen")
val ARGON = gas("argon")
val CARBON_DIOXIDE = gas("carbon_dioxide")

val gassesRegistrySet: RegistrySetBuilder = RegistrySetBuilder()
    .add(GasRegistry.GAS_REGISTRY_KEY) {
        it.register(OXYGEN, Gas(1.429))
        it.register(NITROGEN, Gas(1.2506))
        it.register(ARGON, Gas(1.784))
        it.register(CARBON_DIOXIDE, Gas(1.977))
    }