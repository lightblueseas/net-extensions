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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.testng.annotations.Test;

public class BaseProxySelectorTest
{

	@Test(enabled = false)
	public void testBaseProxySelector()
		throws URISyntaxException, MalformedURLException, IOException
	{
		// Populate the HashMap (List of proxies)
		final HashMap<SocketAddress, ProxyDecorator> proxies = new HashMap<>();
		// Change here to the appropriate InetAddress(ip address and port) from your proxy server.
		final ProxyDecorator pd = new ProxyDecorator(Proxy.Type.HTTP,
			new InetSocketAddress("127.0.0.1", 3128));
		proxies.put(pd.address(), pd);

		final BaseProxySelector proxySelector = new BaseProxySelector(ProxySelector.getDefault(),
			proxies);
		ProxySelector.setDefault(proxySelector);
		// if the proxy server neeeds an authentication then
		// uncomment next line and set the appropriate username and password for the proxy
		// authentication...
		// Authenticator.setDefault(new ProxyAuthenticator("TheUsernameChangeMe",
		// "ThePasswordChangeMe"));

		final String URL_BEHIND_SOCKS = "http://yahoo.com";
		final String URL_NO_SOCKS = "http://mail.yahoo.com";

		System.out.println("\nURL : " + URL_NO_SOCKS);
		URI uri = new URI(URL_NO_SOCKS);
		InputStream is = uri.toURL().openStream();
		BufferedReader rdr = new BufferedReader(new InputStreamReader(is));
		String str;
		while ((str = rdr.readLine()) != null)
		{
			System.out.println(str);
		}
		is.close();

		System.out.println("\nURL : " + URL_BEHIND_SOCKS);
		uri = new URI(URL_BEHIND_SOCKS);
		is = uri.toURL().openStream();
		rdr = new BufferedReader(new InputStreamReader(is));
		while ((str = rdr.readLine()) != null)
		{
			System.out.println(str);
		}
		is.close();
	}

}
