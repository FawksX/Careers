/*
 * Copyright (C) 2022 Careers Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.fawks.careers.config.handler

import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.reference.ConfigurationReference
import org.spongepowered.configurate.reference.ValueReference
import org.spongepowered.configurate.reference.WatchServiceListener
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 * A Wrapper class for Configurate Configurations.
 *
 * When [ConfigManager.initConfig] is called, this class is instantiated to
 * automatically setup a hot-reloading configurate instance, using the watchdog.
 * This means no reload command is needed for the configuration and automatically
 * maps the players' configuration to our config objects on changes.
 */
class ConfigHandler<T>(
    val dataFolder: Path,
    val configName: String,
    val clazz: Class<T>
) : AutoCloseable {

    companion object {
        const val EXTENSION = "yml"
    }

    val listener: WatchServiceListener = WatchServiceListener.create()
    val configFile = Paths.get(dataFolder.toString() + File.separator + configName)

    lateinit var base: ConfigurationReference<CommentedConfigurationNode>
    lateinit var config: ValueReference<T, CommentedConfigurationNode>

    init {

        try {
            this.base = this.listener.listenToConfiguration(
                { file ->
                    YamlConfigurationLoader.builder()
                        .defaultOptions { opts -> opts.shouldCopyDefaults(true) }
                        .path(file)
                        .build()
                }, configFile
            )

            this.config = this.base.referenceTo(clazz)
            this.base.save()

        } catch (exception: Throwable) {
            exception.printStackTrace()
        }

    }

    fun getConfig(): T {
        return config.get()!!
    }

    @kotlin.jvm.Throws(ConfigurateException::class)
    fun saveToFile() {
        this.base.node().set(clazz, clazz.cast(getConfig()))
        this.base.loader().save(this.base.node())
    }

    override fun close() {
        try {
            this.listener.close()
            this.base.close()
        } catch (exception: Throwable) {
            exception.printStackTrace()
        }
    }
}