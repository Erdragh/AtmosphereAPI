package dev.erdragh.atmosphereapi.datagen

import dev.erdragh.atmosphereapi.AtmosphereAPI
import net.minecraft.data.DataProvider
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = AtmosphereAPI.MODID)
object AtmosphereAPIDatagen {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        println("Hello World Datagen 2")
        val generator = event.generator
        val lookupProvider = event.lookupProvider

        generator.addProvider(
            event.includeServer(),
            DataProvider.Factory {
                DatapackBuiltinEntriesProvider(
                    it,
                    lookupProvider,
                    gassesRegistrySet,
                    setOf(AtmosphereAPI.MODID)
                )
            }
        )
    }
}