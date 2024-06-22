package io.github.astrapi69.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecutor
{

	public static String execute(String shellPath, String executionPath, String command)
		throws IOException, InterruptedException
	{
		if(executionPath.contains("~")){
			executionPath = executionPath.replace("~", System.getProperty("user.home"));
		}
		File executionDirectory = new File(executionPath);
		if (!executionDirectory.exists())
		{
			throw new IllegalArgumentException("Execution directory does not exist");
		}
		String cdToExecutionPath = "cd " + executionPath;
		String commands = cdToExecutionPath + " && " + command;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(shellPath, "-c", commands);

		Process process = processBuilder.start();
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null)
		{
			stringBuilder.append(line + "\n");
		}
		int waitFor = process.waitFor();

		if (waitFor != 0)
			stringBuilder.append("Exit code: ").append(waitFor).append("\n");
		return stringBuilder.toString();
	}
}
