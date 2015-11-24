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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * Helper class for handling Sockets.
 * 
 * @version 1.0
 * @author Asterios Raptis
 */
public class SocketUtils
{

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(SocketUtils.class.getName());

	/**
	 * Checks if the given port at localhost is available.
	 *
	 * @param port
	 *            the port number.
	 * @return If the given port at the given host is available true, otherwise false.
	 */
	public static boolean available(final int port)
	{
		return available("localhost", port);
	}

	/**
	 * Checks if the given port at the given host is available. Note: Socket will be closed after
	 * the test.
	 *
	 * @param host
	 *            the host name.
	 * @param port
	 *            the port number.
	 * @return If the given port at the given host is available true, otherwise false.
	 */
	public static boolean available(final String host, final int port)
	{
		Socket socket = null;
		try
		{
			socket = newSocket(host, port);
			return false;
		}
		catch (final IOException ignored)
		{
			return true;
		}
		finally
		{
			if (socket != null && !socket.isClosed())
			{
				try
				{
					socket.close();
				}
				catch (final IOException e)
				{
					LOGGER.error("Socket could not be closed on host " + host + " on port " + port,
						e);
				}
			}
		}
	}

	/**
	 * Closes the given ServerSocket.
	 * 
	 * @param serverSocket
	 *            The ServerSocket to close.
	 * @return Returns true if the ServerSocket is closed otherwise false.
	 */
	public static boolean closeServerSocket(ServerSocket serverSocket)
	{
		boolean closed = true;
		try
		{
			if (serverSocket != null && !serverSocket.isClosed())
			{
				serverSocket.close();
				serverSocket = null;
			}
		}
		catch (final IOException e)
		{
			LOGGER.error("IOException occured by trying to close the server socket.", e);
			closed = false;
		}
		finally
		{
			try
			{
				if (serverSocket != null && !serverSocket.isClosed())
				{
					serverSocket.close();
				}
			}
			catch (final IOException e)
			{
				LOGGER.error("IOException occured by trying to close the server socket.", e);
				closed = false;
			}
		}
		return closed;
	}

	/**
	 * Factory method for a socket.
	 *
	 * @param host
	 *            the host name.
	 * @param port
	 *            the port number.
	 * @return The created Socket.
	 * @throws IOException
	 *             is thrown if the port is not available or other network errors.
	 */
	public static Socket newSocket(final String host, final int port) throws IOException
	{
		return new Socket(host, port);
	}

	/**
	 * Reads an object from the given socket InetAddress.
	 * 
	 * @param inetAddress
	 *            the InetAddress to read.
	 * @param port
	 *            The port to read.
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static Object readObject(final InetAddress inetAddress, final int port)
		throws IOException, ClassNotFoundException
	{
		return readObject(new Socket(inetAddress, port));
	}

	/**
	 * Reads an object from the given socket object.
	 * 
	 * @param clientSocket
	 *            the socket to read.
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static Object readObject(final Socket clientSocket) throws IOException,
		ClassNotFoundException
	{
		ObjectInputStream in = null;
		Object objectToReturn = null;

		try
		{
			in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));

			while (true)
			{
				objectToReturn = in.readObject();
				if (objectToReturn != null)
				{
					break;
				}
			}
			in.close();
			clientSocket.close();
		}
		catch (final IOException e)
		{
			LOGGER.error("IOException occured by trying to read the object.", e);
			throw e;
		}
		catch (final ClassNotFoundException e)
		{
			LOGGER.error(
				"ClassNotFoundException occured by trying to read the object from the socket.", e);
			throw e;
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}

				if (clientSocket != null)
				{
					clientSocket.close();
				}
			}
			catch (final IOException e)
			{
				LOGGER.error("IOException occured by trying to close the socket.", e);
				throw e;
			}
		}

		return objectToReturn;
	}


	/**
	 * Reads an object from the given socket InetAddress.
	 * 
	 * @param serverName
	 *            The Name from the address to read.
	 * @param port
	 *            The port to read.
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static Object readObject(final String serverName, final int port) throws IOException,
		ClassNotFoundException
	{
		final InetAddress inetAddress = InetAddress.getByName(serverName);
		return readObject(new Socket(inetAddress, port));
	}

	/**
	 * Writes the given Object to the given InetAddress who listen to the given port.
	 * 
	 * @param inetAddress
	 *            the inet address
	 * @param port
	 *            The port who listen the receiver.
	 * @param objectToSend
	 *            The Object to send.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeObject(final InetAddress inetAddress, final int port,
		final Object objectToSend) throws IOException
	{
		Socket socketToClient = null;
		ObjectOutputStream oos = null;

		try
		{
			socketToClient = new Socket(inetAddress, port);
			oos = new ObjectOutputStream(new BufferedOutputStream(socketToClient.getOutputStream()));
			oos.writeObject(objectToSend);
			oos.close();
			socketToClient.close();
		}
		catch (final IOException e)
		{
			LOGGER.error("IOException occured by trying to write the object.", e);
			throw e;
		}
		finally
		{
			try
			{
				if (oos != null)
				{
					oos.close();
				}

				if (socketToClient != null)
				{
					socketToClient.close();
				}
			}
			catch (final IOException e)
			{
				LOGGER.error("IOException occured by trying to close the socket.", e);
				throw e;
			}
		}
	}

	/**
	 * Writes the given Object to the given Socket who listen to the given port.
	 * 
	 * @param socket
	 *            The Socket to the receiver.
	 * @param port
	 *            The port who listen the receiver.
	 * @param objectToSend
	 *            The Object to send.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeObject(final Socket socket, final int port, final Object objectToSend)
		throws IOException
	{
		writeObject(socket.getInetAddress(), port, objectToSend);
	}

	/**
	 * Writes the given Object.
	 * 
	 * @param serverName
	 *            The Name from the receiver(Server).
	 * @param port
	 *            The port who listen the receiver.
	 * @param objectToSend
	 *            The Object to send.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeObject(final String serverName, final int port,
		final Object objectToSend) throws IOException
	{
		final InetAddress inetAddress = InetAddress.getByName(serverName);
		writeObject(inetAddress, port, objectToSend);
	}

}
