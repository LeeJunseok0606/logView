package com.akis.logview;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
	
		File file = new File("/Users/jun/logs/aaa.log");
		FileLineWatcher watcher = new FileLineWatcher(file);
		
		Thread thread = new Thread((Runnable) watcher);
		thread.setDaemon(true);
		thread.start();
		
		model.addAttribute("logview", watcher.getLog() );
		
		logger.error(watcher.getLog());
		
		return "home";
	}
	
}
