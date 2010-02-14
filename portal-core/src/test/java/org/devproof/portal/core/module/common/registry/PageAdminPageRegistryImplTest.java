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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.devproof.portal.core.config.PageConfiguration;
import org.devproof.portal.core.module.common.locator.PageLocator;
import org.devproof.portal.core.module.modulemgmt.entity.ModuleLinkEntity;
import org.devproof.portal.core.module.modulemgmt.service.ModuleService;
import org.devproof.portal.core.module.right.page.RightPage;
import org.easymock.EasyMock;

/**
 * @author Carsten Hufe
 */
public class PageAdminPageRegistryImplTest extends TestCase {
	private PageAdminPageRegistryImpl impl;
	private PageLocator pageLocatorMock;
	private ModuleService moduleServiceMock;

	@Override
	public void setUp() throws Exception {
		pageLocatorMock = EasyMock.createStrictMock(PageLocator.class);
		moduleServiceMock = EasyMock.createStrictMock(ModuleService.class);
		impl = new PageAdminPageRegistryImpl();
		impl.setModuleService(moduleServiceMock);
		impl.setPageLocator(pageLocatorMock);
	}

	public void testGetRegisteredPages() {
		impl.registerPageAdminPage(RightPage.class);
		assertEquals(RightPage.class, impl.getRegisteredPageAdminPages().get(0));
	}

	public void testBuildNavigation() {
		Collection<PageConfiguration> confs = new ArrayList<PageConfiguration>();
		PageConfiguration conf = new PageConfiguration();
		conf.setPageClass(RightPage.class);
		confs.add(conf);
		EasyMock.expect(pageLocatorMock.getPageConfigurations()).andReturn(confs);
		List<ModuleLinkEntity> links = new ArrayList<ModuleLinkEntity>();
		ModuleLinkEntity link = new ModuleLinkEntity();
		link.setPageName(RightPage.class.getSimpleName());
		links.add(link);
		EasyMock.expect(moduleServiceMock.findAllVisiblePageAdministrationLinks()).andReturn(links);
		EasyMock.replay(pageLocatorMock, moduleServiceMock);
		impl.buildNavigation();
		EasyMock.verify(pageLocatorMock, moduleServiceMock);
		assertEquals(RightPage.class, impl.getRegisteredPageAdminPages().get(0));
	}

	public void testAfterPropertiesSet() throws Exception {
		Collection<PageConfiguration> confs = new ArrayList<PageConfiguration>();
		PageConfiguration conf = new PageConfiguration();
		conf.setPageClass(RightPage.class);
		confs.add(conf);
		EasyMock.expect(pageLocatorMock.getPageConfigurations()).andReturn(confs);
		List<ModuleLinkEntity> links = new ArrayList<ModuleLinkEntity>();
		ModuleLinkEntity link = new ModuleLinkEntity();
		link.setPageName(RightPage.class.getSimpleName());
		links.add(link);
		EasyMock.expect(moduleServiceMock.findAllVisiblePageAdministrationLinks()).andReturn(links);
		EasyMock.replay(pageLocatorMock, moduleServiceMock);
		impl.afterPropertiesSet();
		EasyMock.verify(pageLocatorMock, moduleServiceMock);
		assertEquals(RightPage.class, impl.getRegisteredPageAdminPages().get(0));
	}

	public void testRegisterPage() {
		impl.registerPageAdminPage(RightPage.class);
		assertEquals(impl.getRegisteredPageAdminPages().get(0), RightPage.class);
	}

	public void testRemovePage() {
		impl.registerPageAdminPage(RightPage.class);
		assertEquals(1, impl.getRegisteredPageAdminPages().size());
		impl.removePageAdminPage(RightPage.class);
		assertEquals(0, impl.getRegisteredPageAdminPages().size());
	}
}