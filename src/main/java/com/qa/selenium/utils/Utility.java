package com.qa.selenium.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class Utility {
	
	private static Logger logger = LoggerFactory.getLogger (Utility.class);
	
	public static void printRow(final List<WebElement> columns,
			final FileWriter csvWriter) throws IOException {
		boolean first = true;
		for (final WebElement col : columns) {
			if (!first)
				csvWriter.write("\t");
			final String text = col.getText().trim();
			csvWriter.write(text);
			first = false;
		}
		// If the column list was not empty, then add a newline
		if (!first)
			csvWriter.write("\r\n");
	}
	
   
}
