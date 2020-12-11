package com.example.CRUDAPICheckpoint;


import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Initialize {

    public Initialize() throws Exception {
    }

    public void setupDataBase () throws Exception {
        String json = getJSON("/init.json");
        System.out.println(json);
        List<User> users = new ArrayList<User>();



    }

    private String getJSON(String path) throws Exception{
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }


}
