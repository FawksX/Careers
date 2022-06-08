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

import net.kyori.adventure.audience.Audience
import java.util.*

class CareersSenderImpl<T : Audience> internal constructor(
    val sender: T,
    val uuid: UUID,
    val displayName: String,
    val senderType: CareersSender.SenderType
) : CareersSender<T> {

    override fun sender(): T {
        return sender
    }

    override fun audience(): Audience {
        return sender
    }

    override fun getId(): UUID {
        return uuid
    }

    override fun getName(): String {
        return displayName
    }

}