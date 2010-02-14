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
package org.devproof.portal.core.module.common.dao;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.devproof.portal.core.module.email.entity.EmailTemplateEntity;
import org.devproof.portal.core.module.user.service.UsernameResolver;
import org.easymock.EasyMock;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Carsten Hufe
 */
public class GenericHibernateDaoImplTest extends TestCase {
	private GenericHibernateDaoImpl<EmailTemplateEntity, Integer> impl;
	private SessionFactory sessionFactory;
	private Session session;
	private Query query;
	private UsernameResolver usernameResolver;

	@Override
	public void setUp() throws Exception {
		sessionFactory = EasyMock.createMock(SessionFactory.class);
		session = EasyMock.createMock(Session.class);
		query = EasyMock.createMock(Query.class);
		usernameResolver = EasyMock.createMock(UsernameResolver.class);
		impl = new GenericHibernateDaoImpl<EmailTemplateEntity, Integer>(EmailTemplateEntity.class);
		impl.setSessionFactory(sessionFactory);
		impl.setUsernameResolver(usernameResolver);
		EasyMock.expect(session.getSessionFactory()).andReturn(sessionFactory);
		EasyMock.expect(sessionFactory.openSession()).andReturn(session);
		SessionHolder sessionHolder = new SessionHolder(session);
		TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
		EasyMock.expect(session.isOpen()).andReturn(false);
		EasyMock.expect(session.getSessionFactory()).andReturn(sessionFactory);
	}

	public void testFindById() {
		EmailTemplateEntity expectedTemplates = newEmailTemplate();
		EasyMock.expect(session.get(EmailTemplateEntity.class, 1)).andReturn(expectedTemplates);
		EasyMock.replay(sessionFactory, session);
		EmailTemplateEntity newTemplate = impl.findById(1);
		assertEquals(expectedTemplates, newTemplate);
		EasyMock.verify(session, sessionFactory);
	}

	public void testSave() {
		EmailTemplateEntity template = newEmailTemplate();
		EasyMock.expect(session.beginTransaction()).andReturn(null);
		EasyMock.expect(session.merge(template)).andReturn(1);
		EasyMock.expect(usernameResolver.getUsername()).andReturn("testuser");
		EasyMock.replay(sessionFactory, session, query, usernameResolver);
		impl.save(template);
		EasyMock.verify(session, sessionFactory, query, usernameResolver);
		assertNotNull(template.getCreatedAt());
		assertEquals("testuser", template.getCreatedBy());
		assertNotNull(template.getModifiedAt());
		assertEquals("testuser", template.getModifiedBy());
	}

	public void testRefresh() {
		EmailTemplateEntity template = newEmailTemplate();
		session.refresh(template);
		EasyMock.replay(sessionFactory, session, query);
		impl.refresh(template);
		EasyMock.verify(session, sessionFactory, query);
	}

	public void testDelete() {
		EmailTemplateEntity template = newEmailTemplate();
		EasyMock.expect(session.beginTransaction()).andReturn(null);
		session.delete(template);
		EasyMock.replay(sessionFactory, session, query);
		impl.delete(template);
		EasyMock.verify(session, sessionFactory, query);
	}

	public void testExecuteFinder_UniqueResult() throws Exception {
		EmailTemplateEntity expectedTemplate = newEmailTemplate();
		EasyMock.expect(session.createQuery("Select from FakeEntity where fakeKey = ?")).andReturn(query);
		EasyMock.expect(query.setParameter(0, "fakeValue")).andReturn(query);
		EasyMock.expect(query.setFirstResult(0)).andReturn(query);
		EasyMock.expect(query.setMaxResults(10)).andReturn(query);
		EasyMock.expect(query.uniqueResult()).andReturn(expectedTemplate);
		EasyMock.replay(sessionFactory, session, query);
		Method method = this.getClass().getMethod("methodObject");
		Object template = impl.executeFinder("Select from FakeEntity where fakeKey = ?", new Object[] { "fakeValue" },
				method, 0, 10);
		EasyMock.verify(session, sessionFactory, query);
		assertEquals(expectedTemplate, template);
	}

	public void testExecuteFinder_ResultList() throws Exception {
		List<EmailTemplateEntity> expectedTemplates = Arrays.asList(newEmailTemplate());
		EasyMock.expect(session.createQuery("Select from EmailTemplateEntity where fakeKey = ?")).andReturn(query);
		EasyMock.expect(query.setParameter(0, "fakeValue")).andReturn(query);
		EasyMock.expect(query.setFirstResult(0)).andReturn(query);
		EasyMock.expect(query.setMaxResults(10)).andReturn(query);
		EasyMock.expect(query.list()).andReturn(expectedTemplates);
		EasyMock.replay(sessionFactory, session, query);
		Method method = this.getClass().getMethod("methodList");
		List<?> templates = (List<?>) impl.executeFinder("Select from $TYPE where fakeKey = ?",
				new Object[] { "fakeValue" }, method, 0, 10);
		EasyMock.verify(session, sessionFactory, query);
		assertEquals(expectedTemplates.get(0), templates.get(0));
	}

	public void testExecuteUpdate() {
		EasyMock.expect(session.createQuery("update EmailTemplateEntity set someKey = 'someValue' where fakeKey = ?"))
				.andReturn(query);
		EasyMock.expect(query.setParameter(0, "fakeValue")).andReturn(query);
		EasyMock.expect(query.executeUpdate()).andReturn(0);
		EasyMock.replay(sessionFactory, session, query);
		impl.executeUpdate("update $TYPE set someKey = 'someValue' where fakeKey = ?", new Object[] { "fakeValue" });
		EasyMock.verify(session, sessionFactory, query);
	}

	private EmailTemplateEntity newEmailTemplate() {
		EmailTemplateEntity expectedConfig = new EmailTemplateEntity();
		expectedConfig.setId(1);
		return expectedConfig;
	}

	public List<?> methodList() {
		return null;
	}

	public EmailTemplateEntity methodObject() {
		return null;
	}
}