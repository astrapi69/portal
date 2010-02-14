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
package org.devproof.portal.module.article;

import org.apache.wicket.ResourceReference;

/**
 * @author Carsten Hufe
 */
public class ArticleConstants {
	private ArticleConstants() {
	}

	public static final String PAGEBREAK = "<div style=\"page-break-after: always;\">	<span style=\"display: none;\">&nbsp;</span></div>";
	public static final String CONF_ARTICLES_PER_PAGE = "articles_per_page";
	public static final String CONF_BOX_NUM_LATEST_ARTICLES = "box_num_latest_articles";
	public static final ResourceReference REF_ARTICLE_CSS = new ResourceReference(ArticleConstants.class,
			"css/article.css");
	public static final String CONF_ARTICLE_ENTRIES_IN_FEED = "article_entries_in_feed";
	public static final String CONF_ARTICLE_FEED_TITLE = "article_feed_title";

	public static final String ENTITY_CACHE_REGION = "entity.content";
	public static final String QUERY_CACHE_REGION = "query.content";

}