/*
 * Copyright 2009-2010 Carsten Hufe devproof.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.devproof.portal.core.module.common.registry;

import java.util.List;

import org.apache.wicket.Page;

/**
 * Registry for pages which will displayed in main menu (on top)
 * 
 * @author Carsten Hufe
 */
public interface MainNavigationRegistry {
	/**
	 * Registers a main navigation links. The language property file of the page
	 * must contain a property named "mainNavigationLinkLabel" for menu name
	 */
	public void registerPage(Class<? extends Page> page);

	/**
	 * Registers a main navigation links. The language property file of the page
	 * must contain a property named "mainNavigationLinkLabel" for menu name
	 */
	public void registerPages(List<Class<? extends Page>> pages);

	/**
	 * Clear registry
	 */
	public void clearRegistry();

	/**
	 * Removes a link
	 */
	public void removePage(Class<? extends Page> page);

	/**
	 * Returns all registered pages
	 * 
	 */
	public List<Class<? extends Page>> getRegisteredPages();

	/**
	 * Builds or rebuilds the main navigation from the database
	 */
	public void buildNavigation();

}
