package dev.erdragh.atmosphereapi.datagen

import dev.erdragh.atmosphereapi.AtmosphereAPI
import dev.erdragh.atmosphereapi.Gas
import dev.erdragh.atmosphereapi.GasRegistry
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.resources.ResourceKey

val gassesRegistrySet = RegistrySetBuilder()
    .add(GasRegistry.GAS_REGISTRY_KEY) {
        it.register(ResourceKey.create(GasRegistry.GAS_REGISTRY_KEY, AtmosphereAPI.id("oxygen")), Gas("Oxygen"))
    }