package com.nobbyknox.absa.utils;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestHelper {
    public static byte[] getMT101Bytes() {
        try {
            return new String(Files.readAllBytes(Paths.get(ResourceUtils.getFile("classpath:mt101.txt").getPath())), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
