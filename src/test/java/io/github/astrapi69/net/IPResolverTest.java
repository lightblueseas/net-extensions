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
package io.github.astrapi69.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.alpharogroup.BaseTestCase;

/**
 * The unit test class for the class {@link IPResolver}.
 *
 * @version 1.0
 * @author Asterios Raptis
 */
public class IPResolverTest extends BaseTestCase
{

	/** The byte ip for the ip from "oracle.com". */
	byte[] byteIpOracleCom;

	/** The localhost. */
	InetAddress localhost;

	/** The local ip. */
	String localIP;

	/** The local ip address. */
	byte[] localIpAddress;

	/** The InetAddress for the ip from "java.sun.com". */
	InetAddress oracleCom;

	/** The String for the ip from "java.sun.com". */
	String sJavaSunCom;

	/** The socket for the ip from "java.sun.com". */
	Socket socketJavaSunCom;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@BeforeMethod
	protected void setUp() throws Exception
	{
		super.setUp();
		this.localhost = InetAddress.getLocalHost();

		this.localIP = this.localhost.getHostAddress();
		System.out.println(this.localIP);
		this.localIpAddress = this.localhost.getAddress();
		this.sJavaSunCom = "72.5.124.55";
		try
		{
			this.oracleCom = InetAddress.getByName("oracle.com");
			this.byteIpOracleCom = this.oracleCom.getAddress();
			final int port = 3129;
			this.socketJavaSunCom = new Socket(this.oracleCom, port);
		}
		catch (final UnknownHostException e)
		{
		}
		catch (final IOException e)
		{
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@AfterMethod
	protected void tearDown() throws Exception
	{
		super.tearDown();

	}

	/**
	 * Test method for {@link IPResolver#getAllClients(String)}
	 * 
	 * @throws IOException
	 */
	@Test(enabled = false)
	public void test() throws IOException
	{
		List<InetAddress> allClients = IPResolver.getAllClients("192.168.178.");
		System.out.println(allClients);
	}

	/**
	 * Test method for {@link IPResolver#getIP(java.net.InetAddress)}.
	 */
	@Test(enabled = false)
	public void testGetIP()
	{
		if (this.oracleCom != null)
		{
			final String compare = IPResolver.getIP(this.oracleCom);
			final String expected = this.sJavaSunCom;
			this.actual = expected.equals(compare);
			AssertJUnit.assertTrue("", this.actual);
		}
		else
		{
			System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-");
			System.out.println("|No internet connection.|");
			System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_");
		}

	}

	/**
	 * Test method for {@link IPResolver#getIPAsByte(java.net.InetAddress)}.
	 */
	@Test(enabled = false)
	public void testGetIPAsByte()
	{
		byte[] compare;

		compare = IPResolver.getIPAsByte(this.localhost);
		if (this.oracleCom != null)
		{
			compare = IPResolver.getIPAsByte(this.oracleCom);
			final byte[] expected = this.byteIpOracleCom;
			for (int i = 0; i < compare.length; i++)
			{
				this.actual = expected[i] == compare[i];
				AssertJUnit.assertTrue("", this.actual);
			}
		}
		else
		{
			System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-");
			System.out.println("|No internet connection.|");
			System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_");
		}

	}

	/**
	 * Test method for {@link IPResolver#getLocalIP()}.
	 *
	 * @throws UnknownHostException
	 *             the unknown host exception
	 */
	@Test(enabled = false)
	public void testGetLocalIP() throws UnknownHostException
	{
		final String compare = IPResolver.getLocalIP();
		final String expected = this.localIP;
		this.actual = expected.equals(compare);
		AssertJUnit.assertTrue("", this.actual);
		String canonicalHostName = this.localhost.getCanonicalHostName();
		System.out.println(canonicalHostName);
	}

	/**
	 * Test method for {@link IPResolver#getLocalIPAsByte()}.
	 *
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	@Test(enabled = false)
	public void testGetLocalIPAsByte() throws UnknownHostException
	{
		final byte[] compare = IPResolver.getLocalIPAsByte();
		final byte[] expected = this.localIpAddress;
		for (int i = 0; i < compare.length; i++)
		{
			this.actual = expected[i] == compare[i];
			AssertJUnit.assertTrue("", this.actual);
		}
	}

	/**
	 * Test method for {@link IPResolver#getLocalIPFromServerSocket(int, int)}.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	@Test(enabled = false)
	public void testGetLocalIPFromServerSocket() throws UnknownHostException, IOException
	{
		final InetAddress compare = IPResolver.getLocalIPFromServerSocket(10080, 1000);
		final InetAddress expected = this.localhost;
		this.actual = expected.equals(compare);
		AssertJUnit.assertTrue("", this.actual);
	}

	/**
	 * Test method for {@link IPResolver#getLocalIPFromServerSocketAsString()}.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	@Test(enabled = false)
	public void testGetLocalIPFromServerSocketAsString() throws UnknownHostException, IOException
	{
		final String compare = IPResolver.getLocalIPFromServerSocketAsString();
		final String expected = this.localIP;
		this.actual = expected.equals(compare);
		AssertJUnit.assertTrue("", this.actual);
	}

}
