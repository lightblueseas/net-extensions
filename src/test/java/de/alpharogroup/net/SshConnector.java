package de.alpharogroup.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshConnector {

	private static final String PROMPT = ">";

	public static void main(final String[] args) throws Exception {
	    Session session = null;
	    ChannelShell channel = null;
	    try {

	    	session = getSshSession("root", "admin", "192.168.0.1", 22);
		    channel = (ChannelShell) session.openChannel("shell");

	        final PipedOutputStream reply = new PipedOutputStream();
	        final PipedInputStream input = new PipedInputStream(reply);
	        final ByteArrayOutputStream output = new ByteArrayOutputStream();

	        channel.setInputStream(input, true);
	        channel.setOutputStream(output, true);

	        channel.connect();

	        getPrompt(channel, output);
	        writeCommand(reply, "reset");
	        getPrompt(channel, output);

	    } finally {

	        if (channel != null) {
	            channel.disconnect();
	        }

	        if (session != null) {
	            session.disconnect();
	        }
	    }
	}

	public static Session getSshSession(final String username, final String password, final String host, final int port) throws JSchException {
		Session session;
		final JSch jsch = new JSch();
		session = jsch.getSession(username, host, port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		session.connect();
		return session;
	}

	public static void writeCommand(final PipedOutputStream reply, final String command) throws IOException {
	    System.out.println("Command: " + command);
	    reply.write(command.getBytes());
	    reply.write("\n".getBytes());
	}

	public static void getPrompt(final ChannelShell channel, final ByteArrayOutputStream output)
	        throws UnsupportedEncodingException, InterruptedException {

	    while (!channel.isClosed()) {

	        final String response = new String(output.toByteArray(), "UTF-8");
	        System.out.println(response);
	        if (response.trim().endsWith(PROMPT)) {
	            output.reset();
	            return;
	        }

	        Thread.sleep(100);
	    }
	}
}