package com.akis.logview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akis.logview.model.logVO;


public class FileLineWatcher implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(FileLineWatcher.class);

	private static final int DELAY_MILLIS = 1000;

	private boolean isRun;
	private final File file;
	logVO logVo;
	
	public FileLineWatcher(File file) {
		this.file = file;
	}
	


	public void setLog(String le) {
		logVo.setLog(le);
	}

	@Override
	public void run() {
		logger.info("Start to tail a file - " + file.getName());

		isRun = true;

		if (!file.exists()) {
			logger.error("Failed to find a file - " + file.getName());
		}

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

			while (isRun) {
				final String le = br.readLine();
				if (le != null) {
					logger.info(le);
					//setLog(le);
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
	public logVO getLog() {
		return logVo;
	}
}
