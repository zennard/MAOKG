package com.kpi.ua;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileReader {
    public static final String FILE_NAME = "example_bmp.txt";

    private ImageFileReader() {
    }

    // метод для читання файлу з зображенням та виведення інформації про зчитаний файл,
    // який приймає на вхід назву файлу з зображенням у форматі BMP
    public static BitmapImageHeader loadBitmapImageHeader(String filename) throws IOException
    {
        int line;
        try(BufferedInputStream reader = new BufferedInputStream (new FileInputStream(filename));
            BufferedOutputStream writer = new BufferedOutputStream (new FileOutputStream(FILE_NAME))
        ) {
            while ((line = reader.read())!=-1) {
                writer.write(line);
            }
        }

        BitmapImageHeader bitmapImageHeader;

        PrintedImage pr;
        try(BufferedInputStream reader = new BufferedInputStream (new
                FileInputStream(FILE_NAME))) {
            BitmapImageHeaderReader headerReader = new BitmapImageHeaderReader();
            bitmapImageHeader = headerReader.readHeader(reader);
            pr = headerReader.printedImage;
        }

        System.out.println(bitmapImageHeader);

        return pr.getImageHeader();
    }
}
