package de.alpharogroup.net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import lombok.Getter;

public class SimpleSocketServer implements Runnable {

	/** The log4j Logger */
	private Logger logger = Logger.getLogger(this.getClass());

	/** The server socket. */
	@Getter
	private final ServerSocket serverSocket;

	/** The client socket. */
	@Getter
	private Socket clientSocket;

	/** The server port. */
	@Getter
	private int port;

	public SimpleSocketServer(final int ports) throws IOException {
		super();
		port = ports;
		serverSocket = new ServerSocket(ports);
	}

	@Override
	public void run() {
		/* Wait for connections... */
		while (true) {
			// Accept requests from clients.
			try {
				clientSocket = serverSocket.accept();
				/* Create a process for the communication and start it */
				final AbstractClientHandler clientHandler = newClientHandler(clientSocket);
				final Thread thread = new Thread(clientHandler);
				thread.start();
			} catch (final IOException e) {
				/*
				 * Log the error of the server if IO fails. Something bad has
				 * happened
				 */
				logger.error("Could not accept " + e);
			}
		}
	}

	/**
	 * Factory method for creating the new {@link AbstractClientHandler} for
	 * handling requests. This method is invoked in the run method and can be
	 * overridden so users can provide their own version of a new new
	 * {@link AbstractClientHandler} for custom handling requests.
	 *
	 * @param socket
	 *            the socket
	 *
	 * @return the new {@link AbstractClientHandler} for handling requests.
	 */
	protected AbstractClientHandler newClientHandler(final Socket socket) {
		final AbstractClientHandler clientHandler = new AbstractClientHandler(socket) {
			@Override
			protected void process(final Object object) {
			}
		};
		return clientHandler;
	}

}
