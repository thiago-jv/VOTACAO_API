package com.votacao.util;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static String readFileContent(String arquivo) throws IOException {
        InputStream is = new FileInputStream("src/test/resources/" + arquivo);
        String jsonTxt = IOUtils.toString(is, "UTF-8");
        return jsonTxt;
    }
}
