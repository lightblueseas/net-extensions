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
package de.alpharogroup.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.alpharogroup.BaseTestCase;

/**
 * Test class for the class {@link IPResolver}.
 *
 * @version 1.0
 * @author Asterios Raptis
 */
public class IPResolverTest extends BaseTestCase
{

	/** The local ip. */
	String localIP;

	/** The local ip address. */
	byte[] localIpAddress;

	/** The localhost. */
	InetAddress localhost;

	/** The String for the ip from "java.sun.com". */
	String sJavaSunCom;

	/** The byte ip for the ip from "java.sun.com". */
	byte[] byteIpJavaSunCom;

	/** The InetAddress for the ip from "java.sun.com". */
	InetAddress javaSunCom;

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
			this.javaSunCom = InetAddress.getByName("java.sun.com");
			this.byteIpJavaSunCom = this.javaSunCom.getAddress();
			final int port = 3129;
			this.socketJavaSunCom = new Socket(this.javaSunCom, port);
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
	 * Test method for {@link de.alpharogroup.net.IPResolver#getIP(java.net.InetAddress)}.
	 */
	@Test(enabled = false)
	public void testGetIP()
	{
		if (this.javaSunCom != null)
		{
			final String compare = IPResolver.getIP(this.javaSunCom);
			final String expected = this.sJavaSunCom;
			this.result = expected.equals(compare);
			AssertJUnit.assertTrue("", this.result);
		}
		else
		{
			System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-");
			System.out.println("|No internet connection.|");
			System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_");
		}

	}

	/**
	 * Test method for {@link de.alpharogroup.net.IPResolver#getIPAsByte(java.net.InetAddress)}.
	 */
	@Test(enabled = false)
	public void testGetIPAsByte()
	{
		if (this.javaSunCom != null)
		{
			final byte[] compare = IPResolver.getIPAsByte(this.javaSunCom);
			final byte[] expected = this.byteIpJavaSunCom;
			for (int i = 0; i < compare.length; i++)
			{
				this.result = expected[i] == compare[i];
				AssertJUnit.assertTrue("", this.result);
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
	 * Test method for {@link de.alpharogroup.net.IPResolver#getLocalIP()}.
	 *
	 * @throws UnknownHostException
	 *             the unknown host exception
	 */
	@Test(enabled = false)
	public void testGetLocalIP() throws UnknownHostException
	{
		final String compare = IPResolver.getLocalIP();
		final String expected = this.localIP;
		this.result = expected.equals(compare);
		AssertJUnit.assertTrue("", this.result);
	}

	/**
	 * Test method for {@link de.alpharogroup.net.IPResolver#getLocalIPAsByte()}.
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
			this.result = expected[i] == compare[i];
			AssertJUnit.assertTrue("", this.result);
		}
	}

	/**
	 * Test method for {@link de.alpharogroup.net.IPResolver#getLocalIPFromServerSocket(int, int)}.
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
		this.result = expected.equals(compare);
		AssertJUnit.assertTrue("", this.result);
	}

	/**
	 * Test method for {@link de.alpharogroup.net.IPResolver#getLocalIPFromServerSocketAsString()}.
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
		this.result = expected.equals(compare);
		AssertJUnit.assertTrue("", this.result);
	}

}
