package com.akis.logview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileLineWatcher implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(FileLineWatcher.class);

	private static final int DELAY_MILLIS = 1000;

	private boolean isRun;
	private final File file;

	private String totalLog;
	
	public FileLineWatcher(File file) {
		this.file = file;
	}

	public String getLog() {
		return this.totalLog;
	}

	@Override
	public void run() {
		logger.info("Start to tail a file - " + file.getName());

		isRun = true;

		if (file.exists()) {
			logger.error("Failed to find a file - " + file.getName());
		}

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

			while (isRun) {
				final String le = br.readLine();
				if (le != null) {
					logger.info("new line added - " + le);
					this.totalLog += le + " ";
				} else {
					Thread.sleep(DELAY_MILLIS);
				}
			}
		} catch (Exception e) {
			logger.error("Failed to tail a file - " + file.getPath());
			logger.error(e.getMessage());
		}

		logger.info("Stop to tail a file - " + file.getName());
	}

	public void stop() {
		isRun = false;
	}
	
}
