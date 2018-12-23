package de.alpharogroup.net.throwables;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.experimental.UtilityClass;

/**
 * The class {@link ExceptionExtensions}.
 *
 * @author Asterios Raptis
 * @version 1.0
 */
@UtilityClass
public final class ExceptionExtensions
{

	/**
	 * Gets the stacktrace as string.
	 *
	 * @param throwable
	 *            the throwable
	 * @return the stacktrace as string.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getStackTrace(final Throwable throwable) throws IOException
	{
		StringBuilder stacktrace = new StringBuilder();
		if (null == throwable)
		{
			stacktrace.append("throwable is null...");
			return stacktrace.toString();
		}
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);)
		{
			throwable.printStackTrace(pw);
			stacktrace.append(sw.toString());
		}
		return stacktrace.toString();
	}

	/**
	 * Gets the stack trace elements from the given Throwable and returns a {@link String} object
	 * from it.
	 *
	 * @param throwable
	 *            the throwable
	 * @return the stack trace elements
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getStackTraceElements(Throwable throwable) throws IOException
	{
		StringBuilder stacktrace = new StringBuilder();
		if (null == throwable)
		{
			stacktrace.append("throwable is null...");
			return stacktrace.toString();
		}
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);)
		{
			pw.println(throwable.getClass().toString());
			while (throwable != null)
			{
				pw.println(throwable);
				final StackTraceElement[] stackTraceElements = throwable.getStackTrace();
				for (final StackTraceElement stackTraceElement : stackTraceElements)
				{
					pw.println("\tat " + stackTraceElement);
				}

				throwable = throwable.getCause();
				if (throwable != null)
				{
					pw.println("Caused by:\r\n");
				}
			}
			stacktrace.append(sw.toString());
		}
		return stacktrace.toString();
	}

	/**
	 * Gets the stacktrace as string
	 *
	 * @param throwable
	 *            the throwable
	 * @return the stacktrace as string
	 */
	public static String getStackTraceQueitly(final Throwable throwable)
	{
		StringBuilder stacktrace = new StringBuilder();
		try
		{
			stacktrace.append(getStackTrace(throwable));
		}
		catch (IOException e)
		{
			stacktrace.append("getStackTraceQueitly throwed an IOException:" + e.getMessage());
		}
		return stacktrace.toString();
	}

}
