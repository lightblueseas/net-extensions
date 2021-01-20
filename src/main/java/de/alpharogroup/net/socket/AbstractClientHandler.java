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
package de.alpharogroup.net.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import lombok.Getter;
import lombok.extern.java.Log;
import de.alpharogroup.throwable.ExceptionExtensions;

/**
 * The class {@link AbstractClientHandler}.
 */
@Getter
@Log
public abstract class AbstractClientHandler implements Runnable
{

	/** The client address. */
	private String clientAddress;

	/** The inet address from client. */
	private InetAddress inetAddressFromClient;

	/** The socket. */
	private Socket socket;

	/**
	 * Instantiates a new {@link AbstractClientHandler}
	 *
	 * @param s
	 *            the socket
	 */
	public AbstractClientHandler(final Socket s)
	{
		socket = s;
	}

	/**
	 * Process the given object.
	 *
	 * @param object
	 *            the object
	 */
	protected abstract void process(Object object);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run()
	{
		ObjectInputStream ois = null;
		try
		{
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			clientAddress = socket.getInetAddress().getHostAddress();
			inetAddressFromClient = socket.getInetAddress();
			while (true)
			{
				final Object object = ois.readObject();
				process(object);
			}
		}
		catch (final IOException e)
		{
			log.log(Level.SEVERE, ExceptionExtensions.getStackTrace(e));
		}
		catch (final ClassNotFoundException cnfe)
		{
			log.log(Level.SEVERE, ExceptionExtensions.getStackTrace(cnfe));
		}
		finally
		{
			try
			{
				if (ois != null)
				{
					ois.close();
				}

				if (socket != null)
				{
					socket.close();
				}
			}
			catch (final IOException e)
			{
				log.log(Level.SEVERE, ExceptionExtensions.getStackTrace(e));
			}
		}
		log.info("Socket finished: " + socket);
	}

}
