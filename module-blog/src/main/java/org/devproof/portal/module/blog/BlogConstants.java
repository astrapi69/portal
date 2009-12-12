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
package org.devproof.portal.module.blog;

import org.apache.wicket.ResourceReference;

/**
 * @author Carsten Hufe
 */
public class BlogConstants {
	private BlogConstants() {
	}

	public static final String CONF_BLOG_ENTRIES_PER_PAGE = "blog_entries_per_page";
	public static final String CONF_BLOG_ENTRIES_IN_FEED = "blog_entries_in_feed";
	public static final String CONF_BLOG_FEED_TITLE = "blog_feed_title";

	public static final ResourceReference REF_BLOG_CSS = new ResourceReference(BlogConstants.class, "css/blog.css");
}
