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

package com.liferay.portal.servlet;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.servlet.PortletResponseHeadersHelper;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Tina Tian
 */
public class PortletResponseHeadersHelperImpl
	implements PortletResponseHeadersHelper {

	@Override
	public RequestDispatcher getReloadHeadersRequestDispatcher(
		RequestDispatcher requestDispatcher) {

		return new ReloadHeadersRequestDispatcher(requestDispatcher);
	}

	@Override
	public void transferHeaders(
		Map<String, Object> headers, HttpServletResponse httpServletResponse) {

		_portletResponseHeaders.set(true);

		try {
			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				String name = entry.getKey();
				Object values = entry.getValue();

				if (values instanceof Integer[]) {
					Integer[] intValues = (Integer[])values;

					for (int value : intValues) {
						if (httpServletResponse.containsHeader(name)) {
							httpServletResponse.addIntHeader(name, value);
						}
						else {
							httpServletResponse.setIntHeader(name, value);
						}
					}
				}
				else if (values instanceof Long[]) {
					Long[] dateValues = (Long[])values;

					for (long value : dateValues) {
						if (httpServletResponse.containsHeader(name)) {
							httpServletResponse.addDateHeader(name, value);
						}
						else {
							httpServletResponse.setDateHeader(name, value);
						}
					}
				}
				else if (values instanceof String[]) {
					String[] stringValues = (String[])values;

					for (String value : stringValues) {
						if (httpServletResponse.containsHeader(name)) {
							httpServletResponse.addHeader(name, value);
						}
						else {
							httpServletResponse.setHeader(name, value);
						}
					}
				}
				else if (values instanceof Cookie[]) {
					Cookie[] cookies = (Cookie[])values;

					for (Cookie cookie : cookies) {
						httpServletResponse.addCookie(cookie);
					}
				}
			}
		}
		finally {
			_portletResponseHeaders.set(false);
		}
	}

	private static final ThreadLocal<Boolean> _portletResponseHeaders =
		new CentralizedThreadLocal<>(
			PortletResponseHeadersHelperImpl.class +
				"._portletResponseHeaders",
			() -> false);

	private class HeaderAction<T> {

		public String getName() {
			return _name;
		}

		public T getValue() {
			return _value;
		}

		public boolean isOverride() {
			return _override;
		}

		private HeaderAction(String name, T value, boolean override) {
			_name = name;
			_value = value;
			_override = override;
		}

		private final String _name;
		private final boolean _override;
		private final T _value;

	}

	private class ReloadHeadersRequestDispatcher implements RequestDispatcher {

		@Override
		public void forward(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {

			_requestDispatcher.forward(servletRequest, servletResponse);
		}

		@Override
		public void include(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {

			ReloadHeadersServletResponse reloadHeadersServletResponse =
				new ReloadHeadersServletResponse(
					(HttpServletResponse)servletResponse);

			try {
				_requestDispatcher.include(
					servletRequest, reloadHeadersServletResponse);
			}
			finally {
				reloadHeadersServletResponse.reloadHeaders();
			}
		}

		private ReloadHeadersRequestDispatcher(
			RequestDispatcher requestDispatcher) {

			_requestDispatcher = requestDispatcher;
		}

		private final RequestDispatcher _requestDispatcher;

	}

	private class ReloadHeadersServletResponse
		extends HttpServletResponseWrapper {

		public ReloadHeadersServletResponse(
			HttpServletResponse httpServletResponse) {

			super(httpServletResponse);

			_httpServletResponse = httpServletResponse;
		}

		@Override
		public void addCookie(Cookie cookie) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(
					new HeaderAction<>(cookie.getName(), cookie, false));

				return;
			}

			_httpServletResponse.addCookie(cookie);
		}

		@Override
		public void addDateHeader(String name, long value) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(new HeaderAction<>(name, value, false));

				return;
			}

			_httpServletResponse.addDateHeader(name, value);
		}

		@Override
		public void addHeader(String name, String value) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(new HeaderAction<>(name, value, false));

				return;
			}

			_httpServletResponse.addHeader(name, value);
		}

		@Override
		public void addIntHeader(String name, int value) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(new HeaderAction<>(name, value, false));

				return;
			}

			_httpServletResponse.addIntHeader(name, value);
		}

		public void reloadHeaders() {
			_portletResponseHeaders.set(true);

			try {
				for (int i = 0; i < _headerActions.size(); i++) {
					HeaderAction<?> headerAction = _headerActions.get(i);

					Object value = headerAction.getValue();

					if (value == null) {
						continue;
					}

					if (value instanceof String) {
						if (headerAction.isOverride()) {
							_httpServletResponse.setHeader(
								headerAction.getName(), (String)value);
						}
						else {
							_httpServletResponse.addHeader(
								headerAction.getName(), (String)value);
						}
					}
					else if (value instanceof Long) {
						if (headerAction.isOverride()) {
							_httpServletResponse.setDateHeader(
								headerAction.getName(), (Long)value);
						}
						else {
							_httpServletResponse.addDateHeader(
								headerAction.getName(), (Long)value);
						}
					}
					else if (value instanceof Cookie) {
						_httpServletResponse.addCookie((Cookie)value);
					}
					else if (value instanceof Integer) {
						if (headerAction.isOverride()) {
							_httpServletResponse.setIntHeader(
								headerAction.getName(), (Integer)value);
						}
						else {
							_httpServletResponse.addIntHeader(
								headerAction.getName(), (Integer)value);
						}
					}
					else {
						throw new IllegalStateException(
							"Unable to handle value " + value);
					}
				}
			}
			finally {
				_portletResponseHeaders.set(false);
			}
		}

		@Override
		public void setDateHeader(String name, long value) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(new HeaderAction<>(name, value, true));

				return;
			}

			_httpServletResponse.setDateHeader(name, value);
		}

		@Override
		public void setHeader(String name, String value) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(new HeaderAction<>(name, value, true));

				return;
			}

			_httpServletResponse.setHeader(name, value);
		}

		@Override
		public void setIntHeader(String name, int value) {
			if (_portletResponseHeaders.get()) {
				_headerActions.add(new HeaderAction<>(name, value, true));

				return;
			}

			_httpServletResponse.setIntHeader(name, value);
		}

		private final List<HeaderAction<?>> _headerActions = new ArrayList<>();
		private final HttpServletResponse _httpServletResponse;

	}

}