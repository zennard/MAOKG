package com.kpi.ua;

public class BitmapImageHeader {
    private short type;
    private long size;
    private short reserveField1;
    private short reserveField2;
    private long offset;
    private long headerSize;
    private long width;
    private long height;
    private short colorPlanesNum;
    private short bitsCount;
    private long compressionCoefficient;
    private long compressedImageSize;
    private long horizontalResolution;
    private long verticalResolution;
    private long usedColorsNum;
    private long importantColorsNum;
    private long widthHalf;

    public BitmapImageHeader() {}

    public BitmapImageHeader(short type, long size, short reserveField1, short reserveField2,
                             long offset, long headerSize, long width, long height,
                             short colorPlanesNum, short bitsCount, long compression,
                             long compressedImageSize, long horizontalResolution, long verticalResolution,
                             long usedColorsNum, long importantColorsNum, long widthHalf) {
        this.type = type;
        this.size = size;
        this.reserveField1 = reserveField1;
        this.reserveField2 = reserveField2;
        this.offset = offset;
        this.headerSize = headerSize;
        this.width = width;
        this.height = height;
        this.colorPlanesNum = colorPlanesNum;
        this.bitsCount = bitsCount;
        this.compressionCoefficient = compression;
        this.compressedImageSize = compressedImageSize;
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;
        this.usedColorsNum = usedColorsNum;
        this.importantColorsNum = importantColorsNum;
        this.widthHalf = widthHalf;
    }

    public void setType (short type) { this.type = type; }

    public short getType () { return type; }

    public void setSize (long size)
    {
        this.size = size;
    }

    public long getSize ()
    {
        return size;
    }

    public void setReserveField1 (short reserveField1)
    {
        this.reserveField1 = reserveField1;
    }

    public short getReserveField1 ()
    {
        return reserveField1;
    }

    public void setReserveField2 (short reserveField2)
    {
        this.reserveField2 = reserveField2;
    }

    public short getReserveField2 ()
    {
        return reserveField2;
    }

    public void setOffset (long offset)
    {
        this.offset = offset;
    }

    public long getOffset ()
    {
        return offset;
    }

    public void setHeaderSize(long headerSize)
    {
        this.headerSize = headerSize;
    }

    public long getHeaderSize()
    {
        return headerSize;
    }

    public void setWidth (long width)
    {
        this.width = width;
    }

    public long getWidth ()
    {
        return width;
    }

    public void setHeight (long height)
    {
        this.height = height;
    }

    public long getHeight ()
    {
        return height;
    }

    public void setColorPlanesNum(short colorPlanesNum)
    {
        this.colorPlanesNum = colorPlanesNum;
    }

    public short getColorPlanesNum()
    {
        return colorPlanesNum;
    }

    public void setBitsCount (short bitsCount)
    {
        this.bitsCount = bitsCount;
    }

    public short getBitsCount ()
    {
        return bitsCount;
    }

    public void setCompressionType(long compressionType)
    {
        this.compressionCoefficient = compressionType;
    }

    public long getCompressionCoefficient()
    {
        return compressionCoefficient;
    }

    public void setCompressedImageSize(long compressedImageSize)
    {
        this.compressedImageSize = compressedImageSize;
    }

    public long getCompressedImageSize()
    {
        return compressedImageSize;
    }

    public void setHorizontalResolution (long horizontalResolution) { this.horizontalResolution = horizontalResolution; }

    public long getHorizontalResolution ()
    {
        return horizontalResolution;
    }

    public void setVerticalResolution (long verticalResolution)
    {
        this.verticalResolution = verticalResolution;
    }

    public long getVerticalResolution ()
    {
        return verticalResolution;
    }

    public void setUsedColorsNum(long usedColorsNum)
    {
        this.usedColorsNum = usedColorsNum;
    }

    public long getUsedColorsNum()
    {
        return usedColorsNum;
    }

    public void setImportantColorsNum(long importantColorsNum) { this.importantColorsNum = importantColorsNum; }

    public long getImportantColorsNum()
    {
        return importantColorsNum;
    }

    public void setWidthHalf(long widthHalf)
    {
        this.widthHalf = widthHalf;
    }

    public long getWidthHalf()
    {
        return widthHalf;
    }

    @Override
    public String toString() {
        return "BitmapImageHeader{" +
                "type=" + type +
                ", size=" + size +
                ", reserveField1=" + reserveField1 +
                ", reserveField2=" + reserveField2 +
                ", offset=" + offset +
                ", headerSize=" + headerSize +
                ", width=" + width +
                ", height=" + height +
                ", colorPlanesNum=" + colorPlanesNum +
                ", bitsCount=" + bitsCount +
                ", compressionCoefficient=" + compressionCoefficient +
                ", compressedImageSize=" + compressedImageSize +
                ", horizontalResolution=" + horizontalResolution +
                ", verticalResolution=" + verticalResolution +
                ", usedColorsNum=" + usedColorsNum +
                ", importantColorsNum=" + importantColorsNum +
                ", widthHalf=" + widthHalf +
                '}';
    }
}
