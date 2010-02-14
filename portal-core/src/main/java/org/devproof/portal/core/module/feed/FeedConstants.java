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
package org.devproof.portal.core.module.feed;

import org.apache.wicket.ResourceReference;

/**
 * Contains common constants
 * 
 * @author Carsten Hufe
 */
public class FeedConstants {
	private FeedConstants() {
	}

	public static final ResourceReference REF_ATOM1 = new ResourceReference(FeedConstants.class, "img/feed_atom1-0.gif");
	public static final ResourceReference REF_RSS2 = new ResourceReference(FeedConstants.class, "img/feed_rss2-0.gif");
}