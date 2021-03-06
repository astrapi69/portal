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
package org.devproof.portal.module.blog.service;

import org.devproof.portal.core.module.historization.service.Action;
import org.devproof.portal.core.module.mount.service.MountService;
import org.devproof.portal.module.blog.BlogConstants;
import org.devproof.portal.module.blog.entity.Blog;
import org.devproof.portal.module.blog.entity.BlogHistorized;
import org.devproof.portal.module.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Carsten Hufe
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    private BlogRepository blogRepository;
    private BlogTagService blogTagService;
    private BlogHistorizer blogHistorizer;
    private MountService mountService;

    @Override
    @Transactional
    public void restoreFromHistory(BlogHistorized historized) {
        blogHistorizer.restore(historized);
    }

    @Override
    @Transactional
    public void delete(Blog entity) {
        blogHistorizer.deleteHistory(entity);
        blogRepository.delete(entity);
        blogTagService.deleteUnusedTags();
        mountService.delete(entity.getId().toString(), BlogConstants.HANDLER_KEY);
    }

    @Override
    @Transactional(readOnly = true)
    public Blog findById(Integer id) {
        return blogRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Blog entity) {
        Action action = entity.isTransient() ? Action.CREATED : Action.MODIFIED;
        blogRepository.save(entity);
        blogTagService.deleteUnusedTags();
        blogHistorizer.historize(entity, action);
    }

    @Transactional(readOnly = true)
    @Override
    public Blog newBlogEntity() {
        Blog blog = new Blog();
        blog.setAllRights(blogRepository.findLastSelectedRights());
        return blog;
    }

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Autowired
    public void setBlogTagService(BlogTagService blogTagService) {
        this.blogTagService = blogTagService;
    }

    @Autowired
    public void setBlogHistorizer(BlogHistorizer blogHistorizer) {
        this.blogHistorizer = blogHistorizer;
    }

    @Autowired
    public void setMountService(MountService mountService) {
        this.mountService = mountService;
    }
}
