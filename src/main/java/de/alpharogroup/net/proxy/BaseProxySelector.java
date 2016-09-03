/**
 * The MIT License
 *
 * Copyright (C) 2007 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.net.proxy;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class BaseProxySelector holds a map with proxy addresses. It can replace the default
 * ProxySelector.
 * 
 * Example:
 * 
 * <pre>
 * BaseProxySelector proxySelector = new BaseProxySelector(ProxySelector.getDefault(), proxies);
 * ProxySelector.setDefault(proxySelector);
 * </pre>
 *
 * The argument proxies is the map with the map of the addresses to be used as proxy.
 */
public class BaseProxySelector extends ProxySelector
{

	/** A reference to the default ProxySelector. */
	private ProxySelector defaultSelector = null;

	/** A map of proxies. */
	private final Map<SocketAddress, ProxyDecorator> proxies;

	/**
	 * Instantiates a new base proxy selector.
	 * 
	 * @param defaultSelector
	 *            the default selector
	 * @param proxies
	 *            the proxies
	 */
	public BaseProxySelector(final ProxySelector defaultSelector,
		final Map<SocketAddress, ProxyDecorator> proxies)
	{
		this.defaultSelector = defaultSelector;
		this.proxies = proxies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectFailed(final URI uri, final SocketAddress sa, final IOException ioe)
	{
		if (uri == null || sa == null || ioe == null)
		{
			throw new IllegalArgumentException("Arguments can not be null.");
		}
		final ProxyDecorator p = proxies.get(sa);
		if (p != null)
		{
			if (p.failed() >= 3)
			{
				proxies.remove(sa);
			}
		}
		else
		{
			if (defaultSelector != null)
			{
				defaultSelector.connectFailed(uri, sa, ioe);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Proxy> select(final URI uri)
	{
		if (uri == null)
		{
			throw new IllegalArgumentException("Arguments can not be null.");
		}
		final String protocol = uri.getScheme();
		if ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol))
		{
			final ArrayList<Proxy> proxyList = new ArrayList<>();
			for (final ProxyDecorator p : proxies.values())
			{
				proxyList.add(p.getProxy());
			}
			return proxyList;
		}
		if (defaultSelector != null)
		{
			return defaultSelector.select(uri);
		}
		else
		{
			final ArrayList<Proxy> proxyList = new ArrayList<>();
			proxyList.add(Proxy.NO_PROXY);
			return proxyList;
		}
	}
}