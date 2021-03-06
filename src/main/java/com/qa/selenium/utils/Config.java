package com.qa.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Config extends Properties {
    private static Logger logger = LoggerFactory.getLogger (Config.class);

    private static Config instance = null;

    public static Config getInstance () {
        return instance;
    }

    private Config (Map<String, String> settings) {
        try {
            logger.debug ("Creating Config instance.");
            // Load default properties.
            load (Config.class.getResourceAsStream ("/default.properties"));
            logger.debug("Check if default property has url specified if running individual test.");
            logger.debug("property value should not be specified in the double quote.");
            // Load override properties.
            // load (Config.class.getResourceAsStream ("/test.properties"));
            // Load property file in settings ("properties.file=property file").
            String pFile = settings.get ("properties.file");
            if (pFile != null) {
                logger.debug ("Loading properties file {}.", pFile);
                FileReader pFileReader = new FileReader (pFile);
                load (pFileReader);
                pFileReader.close ();
            }
            // Override with values in TestNG XML file (or any Map).
            putAll (settings);
            // Now override system properties.
            Properties systemProperties = System.getProperties ();
            for (Object key : systemProperties.keySet ())
                put (key, systemProperties.getProperty ((String) key));
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public synchronized static void init (Map<String, String> settings) {
        if (instance == null) {
            logger.debug("Initializing Config object.");
            instance = new Config(settings);
        } else
            logger.debug("Config object was already initialized.");
    }

    public synchronized static void destroy() {
        logger.debug("Destroying Config object.");
        instance = null;
    }

    public int getInt (String name, int def) {
        String val = getProperty (name);
        if (val != null)
            def = Integer.parseInt(val);

        return def;
    }

    public long getLong (String name, long def) {
        String val = getProperty (name);
        if (val != null)
            def = Long.parseLong (val);

        return def;
    }

    public boolean getBoolean (String name, boolean def) {
        String val = getProperty (name);
        if (val != null)
            def = Boolean.parseBoolean(val);

        return def;
    }

    public String get (String name, String def) {
        return getProperty (name, def);
    }
}
