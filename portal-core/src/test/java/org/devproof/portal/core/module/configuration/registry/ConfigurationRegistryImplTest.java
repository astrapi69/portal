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
package org.devproof.portal.core.module.configuration.registry;

import junit.framework.TestCase;

import org.devproof.portal.core.module.configuration.entity.ConfigurationEntity;

/**
 * @author Carsten Hufe
 */
public class ConfigurationRegistryImplTest extends TestCase {
	private ConfigurationRegistryImpl impl;

	@Override
	public void setUp() throws Exception {
		impl = new ConfigurationRegistryImpl();
	}

	public void testGetConfiguration() {
		ConfigurationEntity conf = new ConfigurationEntity();
		conf.setKey("foo");
		conf.setValue("bar");
		conf.setType(String.class.getName());
		impl.registerConfiguration("foo", conf);
		assertEquals(conf, impl.getConfiguration("foo"));
	}

	public void testRegisterConfiguration() {
		ConfigurationEntity conf = new ConfigurationEntity();
		conf.setKey("foo");
		conf.setValue("bar");
		conf.setType(String.class.getName());
		impl.registerConfiguration("foo", conf);
		assertEquals(conf, impl.getConfiguration("foo"));
	}

	public void testRemoveConfiguration() {
		ConfigurationEntity conf = new ConfigurationEntity();
		conf.setKey("foo");
		conf.setValue("bar");
		conf.setType(String.class.getName());
		impl.registerConfiguration("foo", conf);
		assertEquals(conf, impl.getConfiguration("foo"));
		impl.removeConfiguration("foo");
		assertNull(impl.getConfiguration("foo"));
	}
}