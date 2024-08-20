package dev.erdragh.atmosphereapi.datagen

import dev.erdragh.atmosphereapi.Atmosphere
import dev.erdragh.atmosphereapi.AtmosphereDatamap
import dev.erdragh.atmosphereapi.GasAmount
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.data.DataMapProvider
import java.util.concurrent.CompletableFuture

class AtmosphereProvider(output: PackOutput, lookupProvider: CompletableFuture<HolderLookup.Provider>) :
    DataMapProvider(output, lookupProvider) {
    override fun gather() {
        builder(AtmosphereDatamap.ATMOSPHERE_DATA)
            .replace(true)
            .add(
                Level.OVERWORLD,
                Atmosphere(listOf(
                    GasAmount(NITROGEN, 78.08),
                    GasAmount(OXYGEN, 20.95),
                    GasAmount(ARGON, 0.93),
                    GasAmount(CARBON_DIOXIDE, 0.03)
                )),
                false
            )
    }
}