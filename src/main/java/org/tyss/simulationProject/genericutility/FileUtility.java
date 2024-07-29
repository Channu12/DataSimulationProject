package org.tyss.simulationProject.genericutility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 
 * This class contains the methods to fetch the data from Property File
 *
 */

public class FileUtility {
	/**
	 * This method is used to fetch the data from Property File
	 * @param Path
	 * @param key
	 * @return
	 */
	public String getDataFromPropertyFile(String filePath, String key) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties p = new Properties();
		try {
			p.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = p.getProperty(key).trim();
		return value;
	}

	public String readTextFile(String txtFilePath) {
		StringBuilder content = new StringBuilder();
		try {
			InputStream inputStream = new FileInputStream(txtFilePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			if (reader != null) {
				reader.close();
			}
		}catch (Exception e) {
		}
		return content.toString();
	}

	public String writeTextToFile(String data) {
		BufferedWriter writer = null;
		String directoryPath = ".\\Generated_JSON_Files";

		// Create the directory if it doesn't exist
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs(); // creates parent directories if necessary
		}

		// Generate fileName with time stamp
		String fileName = "Transaction_JsonFile_"+new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date())+".txt";

		// Create the file in the specified directory
		File file = new File(directory, fileName);

		try {
			// Write data to the file
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(data);
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
}
