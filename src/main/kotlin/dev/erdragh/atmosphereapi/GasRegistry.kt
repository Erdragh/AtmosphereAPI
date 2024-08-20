package dev.erdragh.atmosphereapi

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.registries.DataPackRegistryEvent

@EventBusSubscriber(modid = AtmosphereAPI.MODID, bus = EventBusSubscriber.Bus.MOD)
object GasRegistry {
    val GAS_REGISTRY_KEY: ResourceKey<Registry<Gas>> = ResourceKey.createRegistryKey(AtmosphereAPI.id("gasses"))

    @SubscribeEvent
    fun registerDatapackRegistries(event: DataPackRegistryEvent.NewRegistry) {
        event.dataPackRegistry(GAS_REGISTRY_KEY, Gas.CODEC, Gas.CODEC)
    }
}