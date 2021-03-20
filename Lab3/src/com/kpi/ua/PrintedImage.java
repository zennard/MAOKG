package com.kpi.ua;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PrintedImage extends Application{
    public static final String MAP_FILE_NAME = "map.txt";
    public static final String IMAGE_FILE_PATH = "img.bmp";
    public static final String PIXELS_FILE_PATH = "pixels.txt";
    private BitmapImageHeader imageHeader; // приватне поле, яке зберігає об'єкт з інформацією про заголовок зображення
    private int numberOfPixels; // приватне поле для збереження кількості пікселів з чорним кольором

    public PrintedImage(){}

    // перевизначений стандартний конструктор
    public PrintedImage(BitmapImageHeader image) {
        this.imageHeader = image;
    }

    public BitmapImageHeader getImageHeader() {
        return imageHeader;
    }

    public int getNumberOfPixels() {
        return numberOfPixels;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.imageHeader = ImageFileReader.loadBitmapImageHeader(IMAGE_FILE_PATH);
        int width = (int)this.imageHeader.getWidth();
        int height = (int)this.imageHeader.getHeight();
        int half = (int) imageHeader.getWidthHalf();

        Group root = new Group();
        Scene scene = new Scene (root, width, height);
        Circle circle;
        int let = 0;
        int let1 = 0;
        int let2 = 0;
        char[][] map = new char[width][height];

        // виконуємо зчитування даних про пікселі
        BufferedInputStream reader = new BufferedInputStream (new FileInputStream(PIXELS_FILE_PATH));

        for(int i=0;i<height;i++)  // поки не кінець зображення по висоті
        {
            for(int j=0;j<half;j++)  // поки не кінець зображення по довжині
            {
                let = reader.read();  // зчитуємо один символ з файлу
                let1 = let;
                let2 = let;
                let1 = let1&(0xf0);  // старший байт - перший піксель
                let1 = let1>>4;  // зсув на 4 розряди
                let2 = let2&(0x0f);  // молодший байт - другий піксель
                if(j*2<width) // так як 1 символ кодує 2 пікселі нам необхідно пройти до середини ширини зображення
                {
                    circle = new Circle ((j)*2,(height-1-i),1,Color.valueOf((returnPixelColor(let1)))); // за допомогою стандартного
                    // примітива Коло радіусом в 1 піксель та кольором визначеним за допомогою методу returnPixelColor малюємо піксель
                    root.getChildren().add(circle); //додаємо об'єкт в сцену
                    if (returnPixelColor(let1) == "BLACK") // якщо колір пікселя чорний, то ставимо в масиві 1
                    {
                        map[j*2][height-1-i] = '1';
                        numberOfPixels++; // збільшуємо кількість чорних пікселів
                    }
                    else
                    {
                        map[j*2][height-1-i] = '0';
                    }
                }

                if(j*2+1<width) // для другого пікселя
                {
                    circle = new Circle ((j)*2+1,(height-1-i),1,Color.valueOf((returnPixelColor(let2))));
                    root.getChildren().add(circle);
                    if (returnPixelColor(let2) == "BLACK")
                    {
                        map[j*2+1][height-1-i] = '1';
                        numberOfPixels++;
                    }
                    else
                    {
                        map[j*2+1][height-1-i] = '0';
                    }
                }
            }
        }
        primaryStage.setScene(scene); // ініціалізуємо сцену
        primaryStage.show(); // візуалізуємо сцену
        reader.close();

        // writing
        try(BufferedOutputStream writer = new BufferedOutputStream (new FileOutputStream(MAP_FILE_NAME))) { // записуємо карту для руху по траекторії в файл)
            for(int i=0;i<height;i++)  // поки не кінець зображення по висоті
            {
                for(int j=0;j<width;j++)  // поки не кінець зображення по довжині
                {
                    writer.write(map[j][i]);
                }
                writer.write(10);
            }
        }

        System.out.println("number of black color pixels = " + numberOfPixels);
    }

    // далі необхідно зробити рух об'єкту по заданій траеторії
    private String returnPixelColor (int color) // метод для співставлення кольорів 16-бітного зображення
    {
        String col = "BLACK";
        switch(color)
        {
            case 0: return "BLACK";
            case 1: return "LIGHTCORAL";
            case 2: return "GREEN";
            case 3: return "BROWN";
            case 4: return "BLUE";
            case 5: return "MAGENTA";
            case 6: return "CYAN";
            case 7: return "LIGHTGRAY";
            case 8: return "DARKGRAY";
            case 9: return "RED";
            case 10:return "LIGHTGREEN";
            case 11:return "YELLOW";
            case 12:return "LIGHTBLUE";
            case 13:return "LIGHTPINK";
            case 14:return "LIGHTCYAN";
            case 15:return "WHITE";
        }
        return col;
    }

    public static void main (String args[])
    {
        launch(args);
    }
}
