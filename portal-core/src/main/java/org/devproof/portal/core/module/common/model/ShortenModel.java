/*
 * Copyright 2009-2011 Carsten Hufe devproof.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devproof.portal.core.module.common.model;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * Shortens a string and appends ...
 *
 * @author Carsten Hufe
 */
public class ShortenModel extends AbstractReadOnlyModel<String> {

    private static final long serialVersionUID = -8158053867039063436L;
    private IModel<String> model;
    private int maxSize;

    public ShortenModel(IModel<String> model, int maxSize) {
        this.model = model;
        this.maxSize = maxSize;
    }

    @Override
    public String getObject() {
        String str = model.getObject();
        return StringUtils.abbreviate(str, maxSize);
    }
}
