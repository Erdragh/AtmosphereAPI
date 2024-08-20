package dev.erdragh.atmosphereapi

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.logging.LogUtils
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.RegisterCommandsEvent
import java.util.function.Supplier

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AtmosphereAPI.MODID)
@EventBusSubscriber(modid = AtmosphereAPI.MODID)
class AtmosphereAPI {
    companion object {
        // Define mod id in a common place for everything to reference
        const val MODID = "atmosphereapi"
        // Directly reference a slf4j logger
        private val LOGGER = LogUtils.getLogger()

        fun id(path: String): ResourceLocation {
            return ResourceLocation.fromNamespaceAndPath(MODID, path)
        }
    }

    constructor(modContainer: ModContainer) {
        println(modContainer)
    }

    @SubscribeEvent
    fun registerCommands(event: RegisterCommandsEvent) {
        event.dispatcher.register(LiteralArgumentBuilder.literal<CommandSourceStack?>("atmosphere").executes {
            it.source.sendSuccess(Supplier {Component.literal("${Atmosphere.getAverageDensity(it.source.level)}")}, true)
            0
        })
    }
}