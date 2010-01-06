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
package org.devproof.portal.module.comment.panel;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devproof.portal.core.module.common.dataprovider.QueryDataProvider;
import org.devproof.portal.core.module.common.panel.MetaInfoPanel;
import org.devproof.portal.module.comment.CommentConstants;
import org.devproof.portal.module.comment.entity.CommentEntity;
import org.devproof.portal.module.comment.query.CommentQuery;
import org.devproof.portal.module.comment.service.CommentService;

/**
 * @author Carsten Hufe
 */
public class CommentPanel extends Panel {

	private static final long serialVersionUID = 1L;

	// @SpringBean(name = "configurationService")
	// private ConfigurationService configurationService;
	@SpringBean(name = "commentDataProvider")
	private QueryDataProvider<CommentEntity> commentDataProvider;
	@SpringBean(name = "commentService")
	private CommentService commentService;

	private CommentQuery query;

	public CommentPanel(String id, String moduleName, String moduleContentId) {
		super(id);
		add(CSSPackageResource.getHeaderContribution(CommentConstants.class, "css/comment.css"));
		query = new CommentQuery();
		query.setModuleName(moduleName);
		query.setModuleContentId(moduleContentId);
		commentDataProvider.setQueryObject(query);
		// Repeater
		add(new CommentDataView("listComment"));
		// Form
		final CommentEntity comment = new CommentEntity();
		comment.setModuleName(moduleName);
		comment.setModuleContentId(moduleContentId);
		Form<CommentEntity> form = new Form<CommentEntity>("form", new CompoundPropertyModel<CommentEntity>(comment)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				comment.setIpAddress("123.123.123.123");
				commentService.save(comment);
			}
		};

		form.add(new TextArea<String>("comment"));
		add(form);
	}

	private class CommentDataView extends DataView<CommentEntity> {
		private static final long serialVersionUID = 1L;

		public CommentDataView(String id) {
			super(id, commentDataProvider);
			// TODO in config
			setItemsPerPage(10);
		}

		@Override
		protected void populateItem(Item<CommentEntity> item) {
			item.setOutputMarkupId(true);
			item.add(new CommentView("commentView", item));
		}
	}

	public class CommentView extends Fragment {

		private static final long serialVersionUID = 1L;

		private CommentEntity comment;

		public CommentView(String id, Item<CommentEntity> item) {
			super(id, "commentView", CommentPanel.this);
			comment = item.getModelObject();
			add(new MetaInfoPanel("metaInfo", comment));
			add(new Label("comment", comment.getComment()));
		}
	}
}
