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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import lombok.Getter;

/**
 * The class {@link SimpleSocketServer}.
 */
@Getter
public class SimpleSocketServer implements Runnable
{

	/** The log4j Logger */
	private Logger logger = Logger.getLogger(this.getClass());

	/** The server socket. */
	private final ServerSocket serverSocket;

	/** The client socket. */
	private Socket clientSocket;

	/** The server port. */
	private int port;

	public SimpleSocketServer(final int ports) throws IOException
	{
		port = ports;
		serverSocket = new ServerSocket(ports);
	}

	/**
	 * Factory method for creating the new {@link AbstractClientHandler} for handling requests. This
	 * method is invoked in the run method and can be overridden so users can provide their own
	 * version of a new new {@link AbstractClientHandler} for custom handling requests.
	 *
	 * @param socket
	 *            the socket
	 *
	 * @return the new {@link AbstractClientHandler} for handling requests.
	 */
	protected AbstractClientHandler newClientHandler(final Socket socket)
	{
		final AbstractClientHandler clientHandler = new AbstractClientHandler(socket)
		{
			@Override
			protected void process(final Object object)
			{
			}
		};
		return clientHandler;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run()
	{
		/* Wait for connections... */
		while (true)
		{
			// Accept requests from clients.
			try
			{
				clientSocket = serverSocket.accept();
				/* Create a process for the communication and start it */
				final AbstractClientHandler clientHandler = newClientHandler(clientSocket);
				final Thread thread = new Thread(clientHandler);
				thread.start();
			}
			catch (final IOException e)
			{
				/*
				 * Log the error of the server if IO fails. Something bad has happened
				 */
				logger.error("Could not accept " + e);
			}
		}
	}

}
