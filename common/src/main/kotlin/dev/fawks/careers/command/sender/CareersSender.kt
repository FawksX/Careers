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

package dev.fawks.careers.command.sender

import dev.fawks.careers.util.CONSOLE_DISPLAY_NAME
import dev.fawks.careers.util.CONSOLE_UUID
import net.kyori.adventure.audience.ForwardingAudience
import dev.fawks.careers.util.Identifiable
import dev.fawks.careers.util.Named
import net.kyori.adventure.audience.Audience
import java.util.UUID

/**
 * Represents a CommandSender in Careers
 * This could be a Console or Player.
 *
 */
interface CareersSender<T : Audience> : ForwardingAudience.Single, Identifiable, Named {

    companion object {

        fun <T : Audience> ofConsole(sender: T): CareersSender<T> {
            return CareersSenderImpl(sender, CONSOLE_UUID, CONSOLE_DISPLAY_NAME, SenderType.CONSOLE)
        }

        fun <T : Audience> ofPlayer(sender: T, uuid: UUID, name: String): CareersSender<T> {
            return CareersSenderImpl(sender, uuid, name, SenderType.PLAYER)
        }

    }

    fun sender(): T

    /**
     * Represents the type of CommandSender
     */
    enum class SenderType {

        CONSOLE,
        PLAYER

    }

}