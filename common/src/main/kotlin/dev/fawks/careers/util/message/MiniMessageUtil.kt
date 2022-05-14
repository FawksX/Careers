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

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

val MINIMESSAGE = MiniMessage.builder().tags(TagResolver.standard()).editTags { e -> e.resolvers(RESOLVERS) }.build()

// todo make prefix not hard-coded
val RESOLVERS = listOf(
    TagResolver.resolver("prefix", Tag.selfClosingInserting(parse("<gray>[<<#E333FF>Jobs<gray>]")))
)

/**
 * Parse MiniMessage markdown into a component. This should be used rather than whenever
 * there are placeholders which are of non-String types. This allows cleaner code in implementation,
 * as it removes the need for all values to be a String.
 *
 * @param text         The MiniMessage syntax
 * @param placeholders A key, value array of placeholders.
 * @return The formatted component
 */
fun parse(text: String, vararg placeholders: Any): Component {
    if (placeholders.isNotEmpty()) {
        check(placeholders.size % 2 == 0) { "Placeholders Must be in a key: replacement order, found missing value!" }
        val TAG_BUILDER = TagResolver.builder()
        var i = 0
        while (i < placeholders.size) {
            val key = placeholders[i].toString()
            val value: Component = deserialize(placeholders[i + 1].toString())
            TAG_BUILDER.tag(key, Tag.selfClosingInserting(value))
            i += 2
        }
        return miniMessage().deserialize(text, TAG_BUILDER.build())
            .decoration(TextDecoration.ITALIC, false)
    }
    return miniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false)
}

fun deserialize(message: String): Component {
    return miniMessage().deserialize(message).decoration(TextDecoration.ITALIC, false)
}

fun miniMessage() : MiniMessage {
    return MINIMESSAGE
}