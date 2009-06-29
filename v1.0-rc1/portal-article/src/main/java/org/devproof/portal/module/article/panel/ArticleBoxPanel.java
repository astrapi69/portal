/*
 * Copyright 2009 Carsten Hufe devproof.org
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
package org.devproof.portal.module.article.panel;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devproof.portal.core.app.PortalSession;
import org.devproof.portal.core.module.configuration.service.ConfigurationService;
import org.devproof.portal.module.article.ArticleConstants;
import org.devproof.portal.module.article.entity.ArticleEntity;
import org.devproof.portal.module.article.page.ArticlePage;
import org.devproof.portal.module.article.service.ArticleService;

/**
 * Latest article box
 * 
 * @author Carsten Hufe
 */
public class ArticleBoxPanel extends Panel {

	private static final long serialVersionUID = 1L;
	@SpringBean(name = "articleService")
	private ArticleService articleService;
	@SpringBean(name = "configurationService")
	private ConfigurationService configurationService;

	public ArticleBoxPanel(final String id) {
		super(id);
		PortalSession session = (PortalSession) getSession();
		Integer num = this.configurationService.findAsInteger(ArticleConstants.CONF_BOX_NUM_LATEST_ARTICLES);
		List<ArticleEntity> articles = this.articleService.findAllArticlesForRoleOrderedByDateDesc(session.getRole(), 0, num);
		RepeatingView repeating = new RepeatingView("repeating");
		for (ArticleEntity article : articles) {
			WebMarkupContainer item = new WebMarkupContainer(repeating.newChildId());
			repeating.add(item);
			BookmarkablePageLink<ArticlePage> link = new BookmarkablePageLink<ArticlePage>("link", ArticlePage.class);
			link.setParameter("id", article.getId());
			link.add(new Label("linkName", article.getTitle()));
			item.add(link);
		}
		this.add(repeating);
		setVisible(articles.size() > 0);
	}
}