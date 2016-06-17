package de.alpharogroup.net.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import lombok.Getter;

public abstract class AbstractClientHandler implements Runnable {

	private static Logger logger = Logger.getLogger(AbstractClientHandler.class.getName());
	@Getter
	private InetAddress inetAddressFromClient = null;
	@Getter
	private String clientAddress = null;
	@Getter
	private Socket socket = null;

	public AbstractClientHandler(final Socket s) {
		socket = s;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			clientAddress = socket.getInetAddress().getHostAddress();
			inetAddressFromClient = socket.getInetAddress();
			while (true) {
				final Object object = ois.readObject();
				process(object);
			}
		} catch (final IOException e) {
			System.out.println("IO Fehler bei socket: " + e.toString());
			logger.error("IO Fehler bei socket: " + e.toString(), e);
		} catch (final ClassNotFoundException cnfe) {
			logger.error("Eine ClassNotFoundException wurde erzeugt:\n " + cnfe.getMessage(), cnfe);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}

				if (socket != null) {
					socket.close();
				}
			} catch (final IOException e) {
				logger.error("IO Fehler bei socket: " + e.toString(), e);
			}
		}
		logger.info("Socket Beended: " + socket);
	}// end of run()

	protected abstract void process(Object object);

}