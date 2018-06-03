/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
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

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;

/**
 * The class {@link ProxyDecorator} decorates a <code>{@link java.net.Proxy}</code> object.
 */
public class ProxyDecorator implements Serializable
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = -5551993083532366206L;

	/** The proxy. */
	private final Proxy proxy;

	/** The socket address. */
	private final InetSocketAddress socketAddress;

	/** The proxy type. */
	private final Type proxyType;

	/** The failed count. */
	private int failedCount = 0;

	/**
	 * Instantiates a new {@link ProxyDecorator} object.
	 * 
	 * @param proxyType
	 *            the proxy type
	 * @param inetSocketAddress
	 *            the inet socket address
	 */
	public ProxyDecorator(final Type proxyType, final InetSocketAddress inetSocketAddress)
	{
		if (inetSocketAddress == null || proxyType == null)
		{
			throw new IllegalArgumentException("Arguments can not be null.");
		}
		socketAddress = inetSocketAddress;
		this.proxyType = proxyType;
		proxy = new Proxy(this.proxyType, socketAddress);
	}

	/**
	 * Address.
	 * 
	 * @return the socket address
	 */
	public SocketAddress address()
	{
		return socketAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ProxyDecorator other = (ProxyDecorator)obj;
		if (failedCount != other.failedCount)
		{
			return false;
		}
		if (proxy == null)
		{
			if (other.proxy != null)
			{
				return false;
			}
		}
		else if (!proxy.equals(other.proxy))
		{
			return false;
		}
		if (proxyType != other.proxyType)
		{
			return false;
		}
		if (socketAddress == null)
		{
			if (other.socketAddress != null)
			{
				return false;
			}
		}
		else if (!socketAddress.equals(other.socketAddress))
		{
			return false;
		}
		return true;
	}

	/**
	 * Failed.
	 * 
	 * @return the int
	 */
	public int failed()
	{
		return ++failedCount;
	}

	/**
	 * Gets the failed count.
	 * 
	 * @return the failed count
	 */
	public int getFailedCount()
	{
		return failedCount;
	}

	/**
	 * Gets the proxy.
	 * 
	 * @return the proxy
	 */
	public Proxy getProxy()
	{
		return proxy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + failedCount;
		result = prime * result + (proxy == null ? 0 : proxy.hashCode());
		result = prime * result + (proxyType == null ? 0 : proxyType.hashCode());
		result = prime * result + (socketAddress == null ? 0 : socketAddress.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "ProxyDecorator [proxy=" + proxy.toString() + ", socketAddress="
			+ socketAddress.toString() + ", proxyType=" + proxyType.toString() + ", failedCount="
			+ failedCount + "]";
	}

	/**
	 * Type.
	 * 
	 * @return the type
	 */
	public Type type()
	{
		return proxyType;
	}
}