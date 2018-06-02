package de.alpharogroup.net.url;

import lombok.Getter;

/**
 * The enum {@link Protocol} represents protocols from an url.
 */
public enum Protocol
{

	/** The urn protocol. */
	URN("urn"),
	/** The jar protocol. */
	MAILTO("mailto"),
	/** The jar protocol. */
	NEWS("mailto"),
	/** The jar protocol. */
	FILE("file"),
	/** The jar protocol. */
	JAR("jar"),
	/** The http protocol. */
	HTTP("http"),
	/** The https protocol. */
	HTTPS("https"),
	/** The nntp protocol. */
	NNTP("nntp"),
	/** The ftp protocol. */
	FTP("ftp"),
	/** The ear protocol. */
	EAR("ear"),
	/** The war protocol. */
	WAR("war");

	/** The protocol. */
	@Getter
	private final String protocol;

	/**
	 * Instantiates a new {@link Protocol}.
	 * 
	 * @param protocol
	 *            the protocol
	 */
	private Protocol(final String protocol)
	{
		this.protocol = protocol;
	}
	
}
