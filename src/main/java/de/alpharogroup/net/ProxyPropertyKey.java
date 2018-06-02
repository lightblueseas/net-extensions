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
package de.alpharogroup.net;

import lombok.Getter;

/**
 * The enum {@link ProxyPropertyKey} holds system property keys for proxies.
 * 
 * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/net/properties.html">Oracle
 *      net properties</a>
 */
public enum ProxyPropertyKey
{

	/** The http proxy host. */
	HTTP_PROXY_HOST("http.proxyHost"),

	/** The key for the http proxy port. */
	HTTP_PROXY_PORT("http.proxyPort"),

	/** The key for the http non proxy hosts. */
	HTTP_NON_PROXY_HOSTS("http.nonProxyHosts"),

	/** The key for the socks proxy host. */
	SOCKS_PROXY_HOST("socksProxyHost"),

	/** The key for the socks proxy port. */
	SOCKS_PROXY_PORT("socksProxyPort"),

	/** The key for the ftp proxy host. */
	FTP_PROXY_HOST("ftp.proxyHost"),

	/** The key for the ftp proxy port. */
	FTP_PROXY_PORT("ftp.proxyPort"),

	/** The key for the ftp non proxy hosts. */
	FTP_NON_PROXY_HOSTS("ftp.nonProxyHosts");

	/** The value. */
	@Getter
	private String value;

	/**
	 * Instantiates a new {@link ProxyPropertyKey} object.
	 *
	 * @param value
	 *            the value
	 */
	ProxyPropertyKey(String value)
	{
		this.value = value;
	}

}
