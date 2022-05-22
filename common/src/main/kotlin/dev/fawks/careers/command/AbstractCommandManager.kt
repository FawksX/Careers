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

package dev.fawks.careers.command

import cloud.commandframework.CommandManager
import cloud.commandframework.annotations.AnnotationParser
import cloud.commandframework.arguments.parser.ParserParameters
import cloud.commandframework.arguments.parser.StandardParameters
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.exceptions.InvalidSyntaxException
import cloud.commandframework.meta.CommandMeta
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler
import cloud.commandframework.minecraft.extras.MinecraftHelp
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.slf4j.Logger

abstract class AbstractCommandManager<C : Audience>(
    val sender: Class<C>,
    val logger: Logger
) : AutoCloseable {

    val cloudCommandManager: CommandManager<C>
    val annotationParser: AnnotationParser<C>

    companion object {
        // TODO: 12/05/2022 possibly make this configurable, not sure it's neccesary though. 
        val INVALID_SYNTAX_FUNCTION = { ex: Exception ->
            MiniMessage.miniMessage().deserialize(
                "<#FF6433>Invalid Syntax! Use <33C4FF> " +
                        String.format("/%s", (ex as InvalidSyntaxException).correctSyntax).split(" ")[0] +
                        "help <#FF6433>for available commands!"
            )
        }
    }

    init {
        cloudCommandManager = registerCommandManager()

        // Incase someone decides to try and run JobsReborn and Careers together, we should override the registrations
        this.cloudCommandManager.setSetting(CommandManager.ManagerSettings.ALLOW_UNSAFE_REGISTRATION, true)
        this.cloudCommandManager.setSetting(CommandManager.ManagerSettings.OVERRIDE_EXISTING_COMMANDS, true)

        val metaFunction = { p: ParserParameters ->
            CommandMeta.simple()
                .with(
                    CommandMeta.DESCRIPTION,
                    p.get(StandardParameters.DESCRIPTION, "No Description")
                ).build()
        }

        MinecraftExceptionHandler<C>()
            .withInvalidSenderHandler()
            .withNoPermissionHandler()
            .withArgumentParsingHandler()
            .withCommandExecutionHandler()
            .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SYNTAX, INVALID_SYNTAX_FUNCTION)
            .withDecorator(
                getPrefix()::append
            ).apply(
                cloudCommandManager,
                mapAudience()
            ) // TODO: 12/05/2022 possibly implement wrapper here so commands can be abstracted away from platforms


        this.annotationParser = AnnotationParser(
            this.cloudCommandManager,
            sender,
            metaFunction
        )
    }

    fun registerHelp(commandRoot: String, vararg aliases: String) {

        logger.info("Registering Help for Command $commandRoot")

        val help = MinecraftHelp(
            "/$commandRoot help",
            mapAudience(),
            this.cloudCommandManager
        )

        help.helpColors = MinecraftHelp.HelpColors.of(
            TextColor.color(240, 81, 226), // Primary
            TextColor.color(9, 147, 232), // Highlight
            TextColor.color(86, 198, 232), //alternateHighlight
            TextColor.color(142, 195, 207),
            TextColor.color(73, 252, 255) // accent
        )

        help.setMaxResultsPerPage(15)

        cloudCommandManager.command(
            cloudCommandManager.commandBuilder(commandRoot, *aliases)
                .literal("help").argument(StringArgument.of("query", StringArgument.StringMode.GREEDY))
                .handler { help.queryCommands(it.getOrDefault("query", "")!!, it.sender) }.build()
        )

    }

    // what is the worst which can happen :D
    @SafeVarargs
    fun registerCommands(vararg clazzes: Class<out AbstractCommand>) {
        clazzes.forEach {

            try {
                registerCommands(it.getConstructor().newInstance())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }

    fun registerCommands(vararg commands: AbstractCommand) {
        commands.forEach {
            this.logger.info("Registering ${it.javaClass.simpleName}")
            this.annotationParser.parse(it)
        }
    }

    private fun mapAudience() = { p: C -> p }

    abstract fun registerCommandManager(): CommandManager<C>

    // TODO: 12/05/2022 Implement this instead of leaving it up to implementing class
    abstract fun getPrefix(): Component

}