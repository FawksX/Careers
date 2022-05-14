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

package dev.fawks.careers;

import dev.fawks.careers.providers.CareersProvider

/**
 * Careers API
 *
 * <p>This is the main API for other plugins to interface with Careers to provide their own jobs.</p>
 *
 * <p>This interface has been implemented using the Service Managers of each platform.
 * This is the recommended way to access the plugin and should be used if possible.
 * For convenience this is always provided in @see [CareersProvider]. However, it is preferred to use the
 * service managers where possible. </p>
 */
interface Careers {

}
