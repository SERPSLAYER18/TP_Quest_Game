package server;

import java.io.*;
import java.util.Properties;

public class PropertiesImporter{

    Properties property = new Properties();

    public String getProperty(String name) {

        String prop = null;

        try(InputStream fis = this.getClass().getClassLoader().getResourceAsStream("db.properties")){
            property.load(fis);

            prop = property.getProperty(name);

        } catch (IOException e) {
            System.err.println("Error "+e.getMessage());
        }

        return prop;
    }

}
