package com.example.CRUDAPICheckpoint;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Initialize {

    // intialize the database
    String json = getJSON("/init.json");

    public Initialize() throws Exception {
    }

    public String getJSON(String path) throws Exception{
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }

}
