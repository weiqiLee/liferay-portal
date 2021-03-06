/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.folder.apio.internal.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;

import java.util.function.BiFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.Folder"
)
public class FolderHasPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> BiFunction<Credentials, S, Boolean> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(RootFolderIdentifier.class)) {
			return (credentials, groupId) -> Try.fromFallible(
				() -> ModelResourcePermissionHelper.contains(
					_modelResourcePermission,
					(PermissionChecker)credentials.get(), (Long)groupId, 0,
					ActionKeys.ADD_FOLDER)
			).orElse(
				false
			);
		}

		if (identifierClass.equals(FolderIdentifier.class)) {
			return (credentials, folderId) -> Try.fromFallible(
				() -> _modelResourcePermission.contains(
					(PermissionChecker)credentials.get(), (Long)folderId,
					ActionKeys.ADD_FOLDER)
			).orElse(
				false
			);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long folderId) {
		return Try.fromFallible(
			() -> _modelResourcePermission.contains(
				(PermissionChecker)credentials.get(), folderId,
				ActionKeys.DELETE)
		).orElse(
			false
		);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long folderId) {
		return Try.fromFallible(
			() -> _modelResourcePermission.contains(
				(PermissionChecker)credentials.get(), folderId,
				ActionKeys.UPDATE)
		).orElse(
			false
		);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission _modelResourcePermission;

}