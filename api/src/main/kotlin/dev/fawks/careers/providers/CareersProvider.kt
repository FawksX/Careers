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

package dev.fawks.careers.providers

import dev.fawks.careers.Careers
import org.jetbrains.annotations.NotNull

/**
 * Static-based access to the Careers API.
 * This should be avoided and use the service managers instead.
 */
class CareersProvider private constructor() {

    companion object {
        var instance: Careers? = null

        /**
         * Gets the instance of [Careers] API.
         * If this has not been initialised, an [IllegalStateException] will be thrown.
         *
         * @return The Careers API instance
         * @throws IllegalStateException if the API has not initialised
         */
        @NotNull
        private fun getApi() : Careers {
            if(instance == null) {
                throw IllegalStateException("Careers has not yet initialised.")
            }
            return instance!!
        }

        fun register(careers: Careers) {
            this.instance = careers
        }

        fun unregister() {
            this.instance = null;
        }

    }

    init {
        throw UnsupportedOperationException("This class cannot be instantiated")
    }

}