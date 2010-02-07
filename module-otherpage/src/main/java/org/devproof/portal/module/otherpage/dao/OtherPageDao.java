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
package org.devproof.portal.module.otherpage.dao;

import java.util.List;

import org.devproof.portal.core.module.common.annotation.Query;
import org.devproof.portal.core.module.common.dao.GenericDao;
import org.devproof.portal.core.module.right.entity.RightEntity;
import org.devproof.portal.module.otherpage.entity.OtherPageEntity;

/**
 * @author Carsten Hufe
 */
public interface OtherPageDao extends GenericDao<OtherPageEntity, Integer> {
	@Query("select op.allRights from OtherPageEntity op where op.modifiedBy = (select max(modifiedBy) from OtherPageEntity)")
	List<RightEntity> findLastSelectedRights();

	@Query("select count(op.contentId) from OtherPageEntity op where op.contentId like ?")
	long existsContentId(String contentId);

	@Query("select op from OtherPageEntity op where op.contentId like ?")
	OtherPageEntity findOtherPageByContentId(String contentId);
}
