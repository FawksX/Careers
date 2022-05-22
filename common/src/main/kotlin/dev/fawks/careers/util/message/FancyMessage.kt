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

package dev.fawks.careers.util.message

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

/**
 * A Wrapper for sending complex minecraft messages.
 * Whilst chat messages are great, this allows for the
 * end user to customize to their preference - be it actionbar, titles
 * or sounds.
 *
 * For Configurate's sake, these are all vars and not vals due to serialisation issues in the jvm.
 */
@ConfigSerializable
class FancyMessage private constructor(
    private var message: List<String>,
    private var sound: String?,
    private var soundsource: Sound.Source?,
    private var actionbar: String?,
    private var title: String?,
    private var subtitle: String?
) : Serializable {

    companion object {
        fun of(message: String): FancyMessage = FancyMessage(message)
        fun builder(): Builder = Builder()
    }

    private constructor(message: String) : this(listOf(message), null, null, null, null, null)

    fun send(audience: Audience, vararg placeholders: Any) {

        if (message.isNotEmpty()) {
            message(audience, message, placeholders)
        }

        if (title != null) {
            val titleText = parse(title!!, placeholders)
            var subtitleText = Component.empty() as Component

            if (subtitle != null) {
                subtitleText = parse(subtitle!!, placeholders)
            }

            audience.showTitle(Title.title(titleText, subtitleText))
        }

        if (actionbar != null) {
            audience.sendActionBar(parse(actionbar!!, placeholders))
        }

        if (sound != null && soundsource != null) {
            audience.playSound(Sound.sound(Key.key(sound!!), soundsource!!, 1f, 1f))
        }

    }

    data class Builder(
        var message: List<String> = listOf(),
        var sound: String? = null,
        var soundsource: Sound.Source? = null,
        var actionbar: String? = null,
        var title: String? = null,
        var subtitle: String? = null
    ) {

        fun message(message: List<String>) = apply { this.message = message }
        fun sound(sound: String) = apply { this.sound = sound }
        fun soundsource(soundsource: Sound.Source) = apply { this.soundsource = soundsource }
        fun actionbar(actionbar: String) = apply { this.actionbar = actionbar }
        fun title(title: String) = apply { this.title = title }
        fun subtitle(subtitle: String) = apply { this.subtitle = subtitle }

        fun build() = FancyMessage(message, sound, soundsource, actionbar, title, subtitle)

    }

}