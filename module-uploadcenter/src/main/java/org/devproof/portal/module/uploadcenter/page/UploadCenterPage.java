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
package org.devproof.portal.module.uploadcenter.page;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tree.table.*;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devproof.portal.core.app.PortalSession;
import org.devproof.portal.core.config.ModulePage;
import org.devproof.portal.core.config.Secured;
import org.devproof.portal.core.module.common.page.TemplatePage;
import org.devproof.portal.core.module.common.panel.BubblePanel;
import org.devproof.portal.core.module.configuration.service.ConfigurationService;
import org.devproof.portal.module.uploadcenter.UploadCenterConstants;
import org.devproof.portal.module.uploadcenter.bean.FileBean;
import org.devproof.portal.module.uploadcenter.model.FileTreeModel;
import org.devproof.portal.module.uploadcenter.panel.CreateFolderPanel;
import org.devproof.portal.module.uploadcenter.panel.UploadCenterPanel;
import org.devproof.portal.module.uploadcenter.panel.UploadFilePanel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carsten Hufe
 */
@Secured(UploadCenterConstants.AUTHOR_RIGHT)
@ModulePage(mountPath = "/uploadcenter", registerPageAdminLink = true)
public class UploadCenterPage extends TemplatePage {

    private static final long serialVersionUID = 3247255196536400455L;

    @SpringBean(name = "configurationService")
    private ConfigurationService configurationService;
    private File rootFolder;
    private IModel<File> selectedFolderModel;
    private BubblePanel bubblePanel;
    private TreeTable folderTreeTable;
    private FileTreeModel fileTreeModel;

    public UploadCenterPage(PageParameters params) {
        super(params);
        this.rootFolder = configurationService.findAsFile(UploadCenterConstants.CONF_UPLOADCENTER_FOLDER);
        this.selectedFolderModel = Model.of(rootFolder);
        this.fileTreeModel = createTreeModel();
        add(createBubblePanel());
        add(createFolderTreeTable());
    }

    @Override
    protected List<Component> newPageAdminBoxLinks(String linkMarkupId, String labelMarkupId) {
        List<Component> links = new ArrayList<Component>();
        links.add(createUploadLink(linkMarkupId, labelMarkupId));
        links.add(createFolderLink(linkMarkupId, labelMarkupId));
        return links;
    }

    private AjaxLink<BubblePanel> createFolderLink(String linkMarkupId, String labelMarkupId) {
        AjaxLink<BubblePanel> createFolderLink = newCreateFolderLink(linkMarkupId);
        createFolderLink.add(createFolderLinkLabel(labelMarkupId));
        return createFolderLink;
    }

    private Label createFolderLinkLabel(String labelMarkupId) {
        return new Label(labelMarkupId, getString("createFolderLink"));
    }

    private AjaxLink<BubblePanel> createUploadLink(String linkMarkupId, String labelMarkupId) {
        AjaxLink<BubblePanel> uploadLink = newUploadLink(linkMarkupId);
        uploadLink.add(createUploadLinkLabel(labelMarkupId));
        return uploadLink;
    }

    private Label createUploadLinkLabel(String labelMarkupId) {
        return new Label(labelMarkupId, getString("uploadLink"));
    }

    private TreeTable createFolderTreeTable() {
        IColumn columns[] = new IColumn[]{new PropertyTreeColumn(new ColumnLocation(Alignment.LEFT, 500, Unit.PX), getString("tableFilename"), "userObject.name"), new PropertyRenderableColumn(new ColumnLocation(Alignment.LEFT, 80, Unit.PX), getString("tableFilesize"), "userObject.size"), new PropertyLinkedColumn(new ColumnLocation(Alignment.RIGHT, 100, Unit.PX), "", "userObject.file", bubblePanel)};
        folderTreeTable = newFolderTreeTable(columns);
        folderTreeTable.getTreeState().collapseAll();
        folderTreeTable.setRootLess(true);
        return folderTreeTable;
    }

    private TreeTable newFolderTreeTable(IColumn[] columns) {
        return new TreeTable("treeTable", fileTreeModel, columns) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onNodeLinkClicked(AjaxRequestTarget target, TreeNode node) {
                if (getTreeState().isNodeSelected(node)) {
                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
                    FileBean fileBean = (FileBean) n.getUserObject();
                    if (fileBean.getFile().isDirectory()) {
                        selectedFolderModel.setObject(fileBean.getFile());
                    } else {
                        selectedFolderModel.setObject(fileBean.getFile().getParentFile());
                    }
                } else {
                    selectedFolderModel.setObject(rootFolder);
                }
            }
        };
    }

    private AjaxLink<BubblePanel> newCreateFolderLink(String linkMarkupId) {
        return new AjaxLink<BubblePanel>(linkMarkupId) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                bubblePanel.setContent(createCreateFolderPanel());
                bubblePanel.showModal(target);
            }

            private CreateFolderPanel createCreateFolderPanel() {
                return new CreateFolderPanel(bubblePanel.getContentId(), selectedFolderModel) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onCreate(AjaxRequestTarget target) {
                        UploadCenterPage.this.forceRefresh(target);
                        bubblePanel.hide(target);
                    }
                };
            }
        };
    }

    private BubblePanel createBubblePanel() {
        bubblePanel = new BubblePanel("bubblePanel");
        return bubblePanel;
    }

    private boolean hasRightToCreateDownload() {
        PortalSession session = (PortalSession) getSession();
        return session.hasRight(UploadCenterConstants.DOWNLOAD_AUTHOR_RIGHT);
    }

    private AjaxLink<BubblePanel> newUploadLink(String markupId) {
        return new AjaxLink<BubblePanel>(markupId) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                bubblePanel.setContent(createUploadFilePanel(bubblePanel));
                bubblePanel.showModal(target);
            }

            private UploadFilePanel createUploadFilePanel(final BubblePanel bubblePanel) {
                return new UploadFilePanel(bubblePanel.getContentId(), selectedFolderModel) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onSubmit() {
                        forceRefresh(null);
                    }

                    @Override
                    protected void onCancel(AjaxRequestTarget target) {
                        bubblePanel.hide(target);
                    }
                };
            }
        };
    }

    private FileTreeModel createTreeModel() {
        return new FileTreeModel();

    }

    private class PropertyLinkedColumn extends PropertyRenderableColumn {
        private static final long serialVersionUID = 1L;
        private BubblePanel bubblePanel;

        public PropertyLinkedColumn(ColumnLocation location, String header, String propertyExpression, BubblePanel bubblePanel) {
            super(location, header, propertyExpression);
            this.bubblePanel = bubblePanel;

        }

        @Override
        public Component newCell(MarkupContainer parent, String id, final TreeNode node, int level) {
            return new UploadCenterPanel(id, new PropertyModel<File>(node, getPropertyExpression()), bubblePanel) {
                private static final long serialVersionUID = 1L;

                @Override
                public void onDelete(AjaxRequestTarget target) {
                    forceRefresh(target);
                }

                @Override
                protected boolean isAllowedToCreateDownload() {
                    return hasRightToCreateDownload();
                }
            };
        }

        @Override
        public IRenderable newCell(TreeNode node, int level) {
            return null;
        }
    }

    private void forceRefresh(AjaxRequestTarget target) {
        setResponsePage(UploadCenterPage.class);
    }
}
