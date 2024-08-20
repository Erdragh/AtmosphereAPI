package dev.erdragh.atmosphereapi

import net.minecraft.core.registries.Registries
import net.minecraft.world.level.Level
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.registries.datamaps.DataMapType
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = AtmosphereAPI.MODID)
object AtmosphereDatamap {
    val ATMOSPHERE_DATA: DataMapType<Level, Atmosphere> = DataMapType.builder(
        AtmosphereAPI.id("atmosphere_data"),
        Registries.DIMENSION,
        Atmosphere.CODEC
    )
        .synced(Atmosphere.CODEC, false)
        .build()

    @SubscribeEvent
    fun registerDataMapTypes(event: RegisterDataMapTypesEvent) {
        event.register(ATMOSPHERE_DATA)
    }
}