/*
 * Copyright 2009-2011 Carsten Hufe devproof.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devproof.portal.core.module.common.component;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;

/**
 * External link with redirection (if you wanna count the hits)
 *
 * @author Carsten Hufe
 */
public class ExternalRedirectLink extends StatelessLink {
    private static final long serialVersionUID = 1L;
    private String url = null;

    public ExternalRedirectLink(String id, String url) {
        super(id);
        this.url = url;
    }

    @Override
    public void onClick() {
        getRequestCycle().setRequestTarget(new RedirectRequestTarget(url));
    }
}
