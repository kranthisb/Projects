package com.cs.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;


public class Utils {
	final static Logger log = Logger.getLogger(Utils.class);
	/**
	 * Print the message and then exit with the given exit code
	 *
	 * @param message The message to print
	 * @param exitCode The exit code
	 */
	public static void exitApp(String message, int exitCode) {
		log.error(message);
		System.exit(exitCode);
	}
	
	public static void logTopMemoryConsumers() throws Exception, IOException {
        if (new File("/bin/bash").exists() && new File("/bin/ps").exists()
                && new File("/usr/bin/head").exists()) {
          log.info("logging top memeory consumer");

          java.lang.ProcessBuilder processBuilder =
                  new java.lang.ProcessBuilder("/bin/bash", "-c", "/bin/ps aux --sort -rss | /usr/bin/head");
          Process p = processBuilder.start();
          p.waitFor();
  
          InputStream is = p.getInputStream();
          java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
          String line = null;
          while ((line = reader.readLine()) != null) {
            log.info(line);
          }
          is.close();
        }
      }
}
