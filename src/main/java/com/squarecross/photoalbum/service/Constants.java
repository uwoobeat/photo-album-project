package com.squarecross.photoalbum.service;

public class Constants {
    public static final String PATH_PREFIX;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            PATH_PREFIX = "C:\\codeslave\\photo-album-project\\photos";
        else PATH_PREFIX = "/Users/uwoobeat/codeslave/photo-album-project/photos";
    }
}
