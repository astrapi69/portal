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
package org.devproof.portal.core.module.user.dao;

import java.util.List;

import org.devproof.portal.core.module.common.annotation.Query;
import org.devproof.portal.core.module.common.dao.GenericDao;
import org.devproof.portal.core.module.role.entity.RoleEntity;
import org.devproof.portal.core.module.user.entity.UserEntity;

/**
 * @author Carsten Hufe
 */
public interface UserDao extends GenericDao<UserEntity, Integer> {
	@Query("select u from UserEntity u join fetch u.role r join fetch r.rights where u.username like ?")
	public UserEntity findUserByUsername(String username);

	@Query("select u from UserEntity u where u.sessionId = ?")
	public UserEntity findUserBySessionId(String sessionId);

	@Query("select distinct u from UserEntity u where u.email like ?")
	public List<UserEntity> findUserByEmail(String email);

	@Query("select count(u) from UserEntity u where u.username like ?")
	public long existsUsername(String username);

	@Query("select count(u) from UserEntity u where u.role = ?")
	public Long countUserForRole(RoleEntity role);

	@Query(value = "select distinct(u) from UserEntity u join u.role.rights as r where r.right = ?")
	public List<UserEntity> findUserWithRight(String right);
}
