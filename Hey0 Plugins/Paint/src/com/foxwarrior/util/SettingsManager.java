package com.foxwarrior.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class SettingsManager {
	private Properties properties;
	private String fileName;
	private String Name;

	public SettingsManager(String fileName, String Name) {
		this.fileName = fileName;
		this.properties = new Properties();
		this.Name = Name;
		File file = new File(fileName);
		
		if (file.exists()){
			load();
		} else {
			save();
		}
	}

	public void load() {
		try {
			this.properties.load(new FileInputStream(this.fileName));
		} catch (IOException ex) {
		}
	}

	public void save() {
		try {
			this.properties.store(new FileOutputStream(this.fileName),
					Name);
		} catch (IOException ex) {
		}
	}

	public String getString(String key, String value) {
		if (this.properties.containsKey(key)) {
			return this.properties.getProperty(key);
		}
		setString(key, value);
		return value;
	}

	public void setString(String key, String value) {
		this.properties.setProperty(key, value);
		save();
	}

	public int getInt(String key, int value) {
		if (this.properties.containsKey(key)) {
			return Integer.parseInt(this.properties.getProperty(key));
		}
		setInt(key, value);
		return value;
	}

	public void setInt(String key, int value) {
		this.properties.setProperty(key, String.valueOf(value));
		save();
	}

	public long getLong(String key, long value) {
		if (this.properties.containsKey(key)) {
			return Long.parseLong(this.properties.getProperty(key));
		}
		setLong(key, value);
		return value;
	}

	public void setLong(String key, long value) {
		this.properties.setProperty(key, String.valueOf(value));
		save();
	}

	public boolean getBoolean(String key, boolean value) {
		if (this.properties.containsKey(key)) {
			return Boolean.parseBoolean(this.properties.getProperty(key));
		}
		setBoolean(key, value);
		return value;
	}

	public void setBoolean(String key, boolean value) {
		this.properties.setProperty(key, String.valueOf(value));
		save();
	}

}