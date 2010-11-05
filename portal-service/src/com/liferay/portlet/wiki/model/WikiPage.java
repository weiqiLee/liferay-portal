/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.model;

/**
 * The model interface for the WikiPage service. Represents a row in the &quot;WikiPage&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.wiki.model.impl.WikiPageImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageModel
 * @see com.liferay.portlet.wiki.model.impl.WikiPageImpl
 * @see com.liferay.portlet.wiki.model.impl.WikiPageModelImpl
 * @generated
 */
public interface WikiPage extends WikiPageModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. All methods that expect a wiki page model instance should use the {@link WikiPage} interface instead.
	 */
	public java.lang.String getAttachmentsDir();

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildPages();

	public com.liferay.portlet.wiki.model.WikiNode getNode();

	public com.liferay.portlet.wiki.model.WikiPage getParentPage();

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getParentPages();

	public com.liferay.portlet.wiki.model.WikiPage getRedirectPage();

	public void setAttachmentsDir(java.lang.String attachmentsDir);
}