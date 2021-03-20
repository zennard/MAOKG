package com.kpi.ua;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapImageHeaderReader {
    public static final String OUTPUT_FILE_NAME = "pixels.txt";
    public static final int EOF = -1;
    public static final int MAXIMUM_COLOR_VALUE = 0x100;
    public static final int SIGNATURE_BYTE = 1;
    public static final int FILE_SIZE_BYTE = 2;
    public static final int SIZE_FIELD_VOLUME = 4;
    public static final int FIRST_RESERVED_FIELD_BYTE = 6;
    public static final int RESERVE_FIELD_VOLUME = 2;
    public static final int SECOND_RESERVED_FIELD_BYTE = 8;
    public static final int OFFSET_FIELD_BYTE = 10;
    public static final int OFFSET_FIELD_VOLUME = 4;
    public static final int HEADER_SIZE_FIELD_BYTE = 14;
    public static final int HEADER_SIZE_FIELD_VOLUME = 4;
    public static final int WIDTH_FIELD_BYTE = 18;
    public static final int WIDTH_FIELD_VOLUME = 4;
    public static final int HEIGHT_FIELD_VOLUME = 4;
    public static final int PLANES_NUMBER_FIELD_BYTE = 26;
    public static final int PLANES_NUMBER_FIELD_VOLUME = 2;
    public static final int BITS_NUMBER_FIELD_BYTE = 28;
    public static final int COMPRESSION_TYPE_FIELD_BYTE = 30;
    public static final int COMPRESSION_TYPE_FIELD_VOLUME = 4;
    public static final int COMPRESSED_IMAGE_SIZE_FIELD_BYTE = 34;
    public static final int COMPRESSED_IMAGE_SIZE_FIELD_VOLUME = 4;
    public static final int HORIZONTAL_RESOLUTION_FIELD_BYTE = 38;
    public static final int HORIZONTAL_RESOLUTION_FIELD_VOLUME = 4;
    public static final int VERTICAL_RESOLUTION_FIELD_VOLUME = 4;
    public static final int BITS_NUMBER_FIELD_VOLUME = 2;
    public static final int VERTICAL_RESOLUTION_FIELD_BYTE = 42;
    public static final int USED_COLORS_NUMBER_FIELD_BYTE = 46;
    public static final int USED_COLORS_NUMBER_FIELD_VOLUME = 4;
    public static final int IMPORTANT_COLORS_NUMBER_FIELD_BYTE = 50;
    public static final int IMPORTANT_COLORS_NUMBER_FIELD_VOLUME = 4;
    public PrintedImage printedImage;

    public BitmapImageHeaderReader(){ }

    // метод, який приймає потік даних зчитаних із файлу із зображенням
    // та повертатиме об’єкт типу HeaderBitmapImage, з інформацією про зображення
    public BitmapImageHeader readHeader(BufferedInputStream reader) throws IOException
    {
        BitmapImageHeader bitmapImageHeader = new BitmapImageHeader();
        int currentLine;
        int imageBytesCount = 0;
        long tempByte;
        boolean isOffsetReached = false;
        while ((currentLine = reader.read()) != EOF) {
            imageBytesCount++;
            if (imageBytesCount == SIGNATURE_BYTE) // зчитуємо сигнатуру
            {
                tempByte = reader.read();
                bitmapImageHeader.setType((short) (bitmapImageHeader.getType() + (tempByte * MAXIMUM_COLOR_VALUE) + currentLine));
                imageBytesCount++;
            }
            if (imageBytesCount == FILE_SIZE_BYTE) // якщо зміщення відносно початку файлу = 2, то зчитуємо розмір файлу
            {
                bitmapImageHeader.setSize(readLong(reader));
                imageBytesCount += SIZE_FIELD_VOLUME; // додаємо 4 до кількості зчитаних байт з файлу, так як розмір поля size 4 байти
            }
            if (imageBytesCount == FIRST_RESERVED_FIELD_BYTE) //зчитуємо резервоване поле №1
            {
                bitmapImageHeader.setReserveField1(readShort(reader)); // у змінну res1 записуємо результат роботи методу readShort
                imageBytesCount += RESERVE_FIELD_VOLUME; // додаємо 2 до кількості зчитаних байт з файлу, так як розмір поля res1 2 байти
            }
            if (imageBytesCount == SECOND_RESERVED_FIELD_BYTE) //зчитуємо резервоване поле №2
            {
                bitmapImageHeader.setReserveField2(readShort(reader));
                imageBytesCount += RESERVE_FIELD_VOLUME;
            }
            if (imageBytesCount == OFFSET_FIELD_BYTE) //зчитуємо зміщення
            {
                bitmapImageHeader.setOffset(readLong(reader));
                imageBytesCount += OFFSET_FIELD_VOLUME;
            }
            if (imageBytesCount == HEADER_SIZE_FIELD_BYTE) //зчитуємо розмір заголовку
            {
                bitmapImageHeader.setHeaderSize(readLong(reader));
                imageBytesCount += HEADER_SIZE_FIELD_VOLUME;
            }
            // зчитуємо з 18ої та 22ої позиції ширину і довжину зображення
            if (imageBytesCount == WIDTH_FIELD_BYTE)
            {
                long width = readLong(reader);
                bitmapImageHeader.setWidth(width);
                bitmapImageHeader.setWidthHalf(calculateWidthHalf(width));
                imageBytesCount += WIDTH_FIELD_VOLUME;

                long height = readLong(reader);
                bitmapImageHeader.setHeight(height);
                imageBytesCount += HEIGHT_FIELD_VOLUME;

            }
            if (imageBytesCount == PLANES_NUMBER_FIELD_BYTE) //зчитуємо кількість площин
            {
                bitmapImageHeader.setColorPlanesNum(readShort(reader));
                imageBytesCount += PLANES_NUMBER_FIELD_VOLUME;
            }
            if (imageBytesCount == BITS_NUMBER_FIELD_BYTE) //зчитуємо кількість біт
            {
                bitmapImageHeader.setBitsCount(readShort(reader));
                imageBytesCount += BITS_NUMBER_FIELD_VOLUME;
            }
            if (imageBytesCount == COMPRESSION_TYPE_FIELD_BYTE) //зчитуємо тип ущільнення
            {
                bitmapImageHeader.setCompressionType(readLong(reader));
                imageBytesCount += COMPRESSION_TYPE_FIELD_VOLUME;
            }
            if (imageBytesCount == COMPRESSED_IMAGE_SIZE_FIELD_BYTE) //зчитуємо розмір ущільненого зображення
            {
                bitmapImageHeader.setCompressedImageSize(readLong(reader));
                imageBytesCount += COMPRESSED_IMAGE_SIZE_FIELD_VOLUME;
            }
            if (imageBytesCount == HORIZONTAL_RESOLUTION_FIELD_BYTE) // горизонтальна роздільна здатність
            {
                bitmapImageHeader.setHorizontalResolution(readLong(reader));
                imageBytesCount += HORIZONTAL_RESOLUTION_FIELD_VOLUME;
            }
            if (imageBytesCount == VERTICAL_RESOLUTION_FIELD_BYTE) // вертикальна роздільна здатність
            {
                bitmapImageHeader.setVerticalResolution(readLong(reader));
                imageBytesCount += VERTICAL_RESOLUTION_FIELD_VOLUME;
            }
            if (imageBytesCount == USED_COLORS_NUMBER_FIELD_BYTE) // кількість кольорів палітри
            {
                bitmapImageHeader.setUsedColorsNum(readLong(reader));
                imageBytesCount += USED_COLORS_NUMBER_FIELD_VOLUME;
            }
            if (imageBytesCount == IMPORTANT_COLORS_NUMBER_FIELD_BYTE) // кількість важливих кольорів
            {
                bitmapImageHeader.setImportantColorsNum(readLong(reader));
                imageBytesCount += IMPORTANT_COLORS_NUMBER_FIELD_VOLUME;
            }

            isOffsetReached = imageBytesCount == bitmapImageHeader.getOffset();
            if (isOffsetReached) {
                reader.mark(1);
                break;
            }
        }
        if(isOffsetReached) {
            reader.reset();
        }

        writeToFile(reader);
        this.printedImage = new PrintedImage(bitmapImageHeader);
        return bitmapImageHeader;
    }

    private void writeToFile(BufferedInputStream reader1) throws IOException {
        int currentLine;
        try(BufferedOutputStream writer = new BufferedOutputStream (new FileOutputStream(OUTPUT_FILE_NAME))) {
            while ((currentLine = reader1.read()) != EOF)
            {
                writer.write(currentLine);
            }
        }
    }

    private long calculateWidthHalf(long width) {
        long widthHalf = width;
        if(width % 2 != 0) {
            widthHalf++;
        }
        widthHalf/=2;
        if(widthHalf % 4 != 0) {
            widthHalf = (widthHalf/4)*4+4;
        }
        return widthHalf;
    }

    // метод для коректного читання полів розміром 2 байти у форматі запису little-endian
    // та переводу в десяткову систему числення
    private short readShort(BufferedInputStream reader1) throws IOException
    {
        long temp = 0;
        short valueToreturn = 0;
        for(long j=0x1;j<=0x1000;j*=0x100) // цикл від 1 до 8 з кроком j*4 -відповідно кількість виконань циклу = 2
        {
            temp=reader1.read();
            valueToreturn+=(temp*j); // додаємо до поточного числа значення нового розряду записаного у 10-вій системі числення
        }
        return valueToreturn;
    }

    // метод для коректного читання полів розміром 4 байти у форматі запису little-endian
    // та переводу в десяткову систему числення
    private long readLong(BufferedInputStream reader1) throws IOException
    {
        long temp = 0;
        long valueToreturn = 0;
        for(long j=0x1;j<=0x1000000;j*=0x100) // цикл від 1 до 64 з кроком j*4 - відповідно кількість виконань циклу = 4
        {
            temp=reader1.read();
            valueToreturn+=(temp*j); // додаємо до поточного числа значення нового розряду записаного у 10-вій системі числення
        }
        return valueToreturn;
    }
}
