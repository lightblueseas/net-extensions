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

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import de.alpharogroup.net.socket.SocketExtensions;

/**
 * Utility class for getting the ip from hosts.
 *
 * @version 1.0
 * @author Asterios Raptis
 */
public class IPResolver
{

	/**
	 * Gets all the clients from the given net mask
	 *
	 * @param netmask
	 *            the net mask
	 * @return all the clients from the given net mask
	 * @throws IOException
	 *             Signals that an I/O exception has occurred
	 */
	public static List<InetAddress> getAllClients(String netmask) throws IOException
	{
		List<InetAddress> availableIps = new ArrayList<>();
		for (int i = 1; i <= 255; i++)
		{
			InetAddress inetAddress = InetAddress.getByName(netmask + i);
			if (inetAddress.isReachable(2000))
			{
				availableIps.add(inetAddress);
			}
		}
		return availableIps;
	}

	/**
	 * Gets the ip address from the given InetAddress object as a String.
	 *
	 * @param inetAddress
	 *            the inet address
	 * @return Returns the ip address from the given InetAddress as a String.
	 */
	public static String getIP(final InetAddress inetAddress)
	{
		String ip = "";
		ip = inetAddress.getHostAddress();
		if (ip.equals(""))
		{
			final byte[] ipAddressInBytes = inetAddress.getAddress();
			for (int i = 0; i < ipAddressInBytes.length; i++)
			{
				if (i > 0)
				{
					ip += ".";
				}
				ip += ipAddressInBytes[i] & 0xFF;
			}
		}
		return ip;
	}

	/**
	 * Gets the ip address as a byte array. Wrappes the method getAddress() from the InetAddress.
	 *
	 * @param inetAddress
	 *            the inet address
	 * @return Returns the ip address as a byte array.
	 */
	public static byte[] getIPAsByte(final InetAddress inetAddress)
	{
		return inetAddress.getAddress();
	}

	/**
	 * Gets the local ip address as a String.
	 *
	 * @return Returns the local ip address as a String or null.
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	public static String getLocalIP() throws UnknownHostException
	{
		return getIP(InetAddress.getLocalHost());
	}

	/**
	 * Gets the local ip address as a byte array.
	 *
	 * @return Returns the local ip address as a byte array.
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	public static byte[] getLocalIPAsByte() throws UnknownHostException
	{
		return InetAddress.getLocalHost().getAddress();
	}

	/**
	 * Gets the InetAddress object from the local host from a ServerSocket object.
	 *
	 * @param port
	 *            the local TCP port
	 * @param backlog
	 *            requested maximum length of the queue of incoming connections
	 * @return Returns the InetAddress object from the local host from a ServerSocket object.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	public static InetAddress getLocalIPFromServerSocket(final int port, final int backlog)
		throws UnknownHostException, IOException
	{
		InetAddress inetAddress = null;
		ServerSocket socket = null;
		try
		{
			socket = new ServerSocket(port, backlog, InetAddress.getLocalHost());
			inetAddress = socket.getInetAddress();
			socket.close();
		}
		finally
		{
			SocketExtensions.closeServerSocket(socket);
		}
		return inetAddress;
	}

	/**
	 * Gets the ip address from the local host as String. It use a ServerSocket object to get it.
	 *
	 * @return Returns the ip from the local host from a ServerSocket object as String.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws UnknownHostException
	 *             is thrown if the local host name could not be resolved into an address.
	 */
	public static String getLocalIPFromServerSocketAsString()
		throws UnknownHostException, IOException
	{
		InetAddress inetAddress = null;
		inetAddress = IPResolver.getLocalIPFromServerSocket(10000, 20000);
		return IPResolver.getIP(inetAddress);
	}

	/**
	 * Resolves the ip address from the given InetAddress object.
	 *
	 * @param inetAddress
	 *            the inet address
	 * @return Returns the ip address from the given InetAddress as a String.
	 */
	public static String resolveIP(final InetAddress inetAddress)
	{
		return getIP(inetAddress);
	}

}
