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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jonathan McCann
 */
public class UpgradeLayout extends UpgradeProcess {

	protected void deleteLinkedOrphanedLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(3);

			sb.append("delete from Layout where layoutPrototypeUuid != '' ");
			sb.append("and layoutPrototypeUuid not in (select uuid_ from ");
			sb.append("LayoutPrototype) and layoutPrototypeLinkEnabled = TRUE");

			runSQL(sb.toString());
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteLinkedOrphanedLayouts();
		updateUnlinkedOrphanedLayouts();
		verifyFriendlyURL();
	}

	protected void updateUnlinkedOrphanedLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(4);

			sb.append("update Layout set layoutPrototypeUuid = null where ");
			sb.append("layoutPrototypeUuid != '' and layoutPrototypeUuid not ");
			sb.append("in (select uuid_ from LayoutPrototype) and ");
			sb.append("layoutPrototypeLinkEnabled = FALSE");

			runSQL(sb.toString());
		}
	}

	protected void verifyFriendlyURL() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps1 = connection.prepareStatement(
					"select layoutFriendlyURLId from LayoutFriendlyURL" +
						"where plid not in (select plid from Layout)");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from LayoutFriendlyURL where" +
							"layoutFriendlyURLId = ?");
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					long layoutFriendlyURLid = rs.getLong(
						"layoutFriendlyURLId");

					ps2.setLong(1, layoutFriendlyURLid);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}