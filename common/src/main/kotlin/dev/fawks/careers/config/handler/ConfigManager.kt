package dev.fawks.careers.config.handler

import com.google.common.collect.Maps
import org.jetbrains.annotations.Nullable
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.ConfigurateException
import java.nio.file.Path

/**
 * A Simple Configurate ConfigManager
 * This should not be used as part of the API, as it is bound to change at any time.
 * This allows for classes annotated with [ConfigSerializable] to be registered
 * and update automatically without reload commands.
 */
class ConfigManager(private val dir: Path) : AutoCloseable {

    companion object {
        private val LOGGER = LoggerFactory.getLogger("Careers/ConfigManager")
        private val CONFIGS: MutableMap<Class<*>, ConfigHandler<*>> = Maps.newConcurrentMap()
    }

    fun initConfigs(vararg configs: Class<*>) = configs.forEach { initConfig(it) }

    fun initConfig(config: Class<*>) {
        LOGGER.info("Initialising Configuration: {}", config.simpleName)
        val fileName = "${config.simpleName.lowercase()}.${ConfigHandler.EXTENSION}"
        CONFIGS[config] = ConfigHandler(this.dir, fileName, config)
    }

    @Nullable
    fun <T> getConfig(config: Class<T>) : T {
        return CONFIGS[config]?.getConfig() as T
    }

    fun saveConfig(config: Class<*>) {
        try {
            CONFIGS[config]?.saveToFile()
        } catch (exception: ConfigurateException) {
            LOGGER.info("Failed to save configuration {}; e:", config.simpleName, exception)
        }
    }

    override fun close() {
        CONFIGS.values.forEach {
            try {
                it.close()
            } catch (throwable: Throwable) {
                LOGGER.error("Failed to shutdown Config {}! e:", it.configName, throwable)
            }
        }
    }
}