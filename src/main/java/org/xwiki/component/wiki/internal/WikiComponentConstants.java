/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.component.wiki.internal;

/**
 * Constants for XClasses and XProperties.
 *
 * @version $Id$
 * @since 4.1M1
 */
public interface WikiComponentConstants
{
    /**
     * The XClass defining a component implementation.
     */
    String COMPONENT_CLASS = "Component.ComponentClass";

    /**
     * The XClass defining a component injection.
     */
    String DEPENDENCY_CLASS = "Component.ComponentDependencyClass";

    /**
     * The XClass defining a component method.
     */
    String METHOD_CLASS = "Component.ComponentMethodClass";

    /**
     * The XClass defining a component interface implementation.
     */
    String INTERFACE_CLASS = "Component.ComponentInterfaceClass";

    /**
     * The name property of the {@link #INTERFACE_CLASS} XClass.
     */
    String INTERFACE_NAME_FIELD = "name";

    /**
     * The name property of the {@link #METHOD_CLASS} XClass.
     */
    String METHOD_NAME_FIELD = INTERFACE_NAME_FIELD;

    /**
     * The role hint property of both {@link #COMPONENT_CLASS} and {@link #DEPENDENCY_CLASS}.
     */
    String COMPONENT_ROLE_HINT_FIELD = "roleHint";

    /**
     * The role type property of both {@link #COMPONENT_CLASS} and {@link #DEPENDENCY_CLASS}.
     */
    String COMPONENT_ROLE_TYPE_FIELD = "roleType";
}
