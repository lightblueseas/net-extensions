package io.github.astrapi69.net;

import org.testng.annotations.Test;

import java.io.IOException;

public class ShellExecutorTest {

    @Test
    public void testExecute() throws IOException, InterruptedException {

        String shellPath = "/usr/bin/zsh";
        String executionPath = "~/dev/tmp";
        String command = "touch foo.txt";

        String shellOutput = ShellExecutor.execute(shellPath, executionPath, command);
        System.out.println(shellOutput);

    }
}