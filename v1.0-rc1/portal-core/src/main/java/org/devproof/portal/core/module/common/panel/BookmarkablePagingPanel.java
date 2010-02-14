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
package org.devproof.portal.core.module.common.panel;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Carsten Hufe
 */
public class BookmarkablePagingPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private final BookmarkablePageLink<String> backLink;
	private final BookmarkablePageLink<String> forwardLink;

	public BookmarkablePagingPanel(final String id, final IPageable pageable, final Class<? extends Page> parentClazz, final PageParameters params) {
		super(id);
		if (params != null && params.containsKey("page")) {
			int page = params.getAsInteger("page", 1);
			if (page > 0 && page <= pageable.getPageCount()) {
				pageable.setCurrentPage(page - 1);
			}
		}

		this.backLink = new BookmarkablePageLink<String>("backLink", parentClazz) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return pageable.getCurrentPage() != 0;
			}
		};
		this.backLink.setParameter("page", pageable.getCurrentPage());
		this.add(this.backLink);

		this.forwardLink = new BookmarkablePageLink<String>("forwardLink", parentClazz) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return (pageable.getPageCount() - 1) > pageable.getCurrentPage();
			}

		};
		this.forwardLink.setParameter("page", pageable.getCurrentPage() + 2);
		this.add(this.forwardLink);
		if (params != null) {
			for (String key : params.keySet()) {
				// if(!"page".equals(key) && !"rateid".equals(key) &&
				// !"vote".equals(key)) {
				if ("broken".equals(key) || "search".equals(key) || "tag".equals(key)) {
					String value = params.getString(key);
					this.backLink.setParameter(key, value);
					this.forwardLink.setParameter(key, value);
				}
			}
		}

	}
}