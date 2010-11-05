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

package com.liferay.portlet.social.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the SocialEquityGroupSetting service. Represents a row in the &quot;SocialEquityGroupSetting&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.social.model.impl.SocialEquityGroupSettingModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.social.model.impl.SocialEquityGroupSettingImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityGroupSetting
 * @see com.liferay.portlet.social.model.impl.SocialEquityGroupSettingImpl
 * @see com.liferay.portlet.social.model.impl.SocialEquityGroupSettingModelImpl
 * @generated
 */
public interface SocialEquityGroupSettingModel extends BaseModel<SocialEquityGroupSetting> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a social equity group setting model instance should use the {@link SocialEquityGroupSetting} interface instead.
	 */

	/**
	 * Gets the primary key of this social equity group setting.
	 *
	 * @return the primary key of this social equity group setting
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this social equity group setting
	 *
	 * @param pk the primary key of this social equity group setting
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the equity group setting id of this social equity group setting.
	 *
	 * @return the equity group setting id of this social equity group setting
	 */
	public long getEquityGroupSettingId();

	/**
	 * Sets the equity group setting id of this social equity group setting.
	 *
	 * @param equityGroupSettingId the equity group setting id of this social equity group setting
	 */
	public void setEquityGroupSettingId(long equityGroupSettingId);

	/**
	 * Gets the group id of this social equity group setting.
	 *
	 * @return the group id of this social equity group setting
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this social equity group setting.
	 *
	 * @param groupId the group id of this social equity group setting
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the company id of this social equity group setting.
	 *
	 * @return the company id of this social equity group setting
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this social equity group setting.
	 *
	 * @param companyId the company id of this social equity group setting
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the class name of the model instance this social equity group setting is polymorphically associated with.
	 *
	 * @return the class name of the model instance this social equity group setting is polymorphically associated with
	 */
	public String getClassName();

	/**
	 * Gets the class name id of this social equity group setting.
	 *
	 * @return the class name id of this social equity group setting
	 */
	public long getClassNameId();

	/**
	 * Sets the class name id of this social equity group setting.
	 *
	 * @param classNameId the class name id of this social equity group setting
	 */
	public void setClassNameId(long classNameId);

	/**
	 * Gets the type of this social equity group setting.
	 *
	 * @return the type of this social equity group setting
	 */
	public int getType();

	/**
	 * Sets the type of this social equity group setting.
	 *
	 * @param type the type of this social equity group setting
	 */
	public void setType(int type);

	/**
	 * Gets the enabled of this social equity group setting.
	 *
	 * @return the enabled of this social equity group setting
	 */
	public boolean getEnabled();

	/**
	 * Determines if this social equity group setting is enabled.
	 *
	 * @return <code>true</code> if this social equity group setting is enabled; <code>false</code> otherwise
	 */
	public boolean isEnabled();

	/**
	 * Sets whether this social equity group setting is enabled.
	 *
	 * @param enabled the enabled of this social equity group setting
	 */
	public void setEnabled(boolean enabled);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(SocialEquityGroupSetting socialEquityGroupSetting);

	public int hashCode();

	public SocialEquityGroupSetting toEscapedModel();

	public String toString();

	public String toXmlString();
}