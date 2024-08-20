package dev.erdragh.atmosphereapi.datagen

import dev.erdragh.atmosphereapi.AtmosphereAPI
import dev.erdragh.atmosphereapi.Gas
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.common.data.LanguageProvider

fun LanguageProvider.add(key: ResourceKey<Gas>, translation: String) {
    add(Gas.translationKey(key), translation)
}

class AtmosphereAPILangEn(output: PackOutput) : LanguageProvider(output, AtmosphereAPI.MODID, "en_US") {
    override fun addTranslations() {
        add(OXYGEN, "Oxygen")
        add(NITROGEN, "Nitrogen")
        add(ARGON, "Argon")
        add(CARBON_DIOXIDE, "Carbon Dioxide")
    }
}