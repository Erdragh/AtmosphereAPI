package dev.erdragh.atmosphereapi

import com.mojang.logging.LogUtils
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AtmosphereAPI.MODID)
class AtmosphereAPI {
    companion object {
        // Define mod id in a common place for everything to reference
        const val MODID = "atmosphereapi"
        // Directly reference a slf4j logger
        private val LOGGER = LogUtils.getLogger()
    }

    constructor(modEventBus: IEventBus, modContainer: ModContainer) {
    }
}