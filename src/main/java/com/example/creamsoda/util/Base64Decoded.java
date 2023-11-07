package com.example.creamsoda.util;

import com.example.creamsoda.exception.Exception400;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

public class Base64Decoded implements MultipartFile {

    public static MultipartFile convertBase64ToMultipartFile(String base64)
            throws IOException {
        byte[] decodedData;
        try {
            decodedData = Base64.getDecoder().decode(base64);

        } catch (Exception e) {
            throw new Exception400("코트 프로필 사진이 올바른 base64 문자열이 아닙니다.");
        }
        return new Base64Decoded(decodedData);


    }
    private final byte[] imgContent;

    public Base64Decoded(byte[] imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public String getName() {
        return "base64.jpg";
    }

    @Override
    public String getOriginalFilename() {
        return "base64.jpg";
    }

    @Override
    public String getContentType() {
        return "image/jpeg";
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(imgContent);
    }
}