package com.example.xacaton.neuronet;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class CatImageDetector {
    MatOfRect faces = new MatOfRect();
    MatOfRect faces2 = new MatOfRect();

    public static int[] method(BufferedImage image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        final int[][] catCount = new int[1][1];

        // вывести в окне картинку
        // нарисовать рамки в области распознавания
        // запуск по ключу
        SwingUtilities.invokeLater(() -> {
            //BufferedImage resizedImage = resizeImage(img, 640, 480);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(5));
            CatImageDetector detecor = new CatImageDetector();
            catCount[0] = detecor.work_with_image(image);
            Rect[] array = detecor.faces.toArray();
            for (Rect rect : array) {
                g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(5));
            Rect[] array2 = detecor.faces2.toArray();
            for (Rect rect : array2) {
                g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
            g2d.dispose();
            ImageIcon icon = new ImageIcon(image);
            JFrame window = new JFrame("Результат");
            window.setLocationByPlatform(true);
            window.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel lbl = new JLabel();
            lbl.setIcon(icon);
            window.add(lbl);
            window.getContentPane().add(lbl, BorderLayout.CENTER);
            window.pack();
            window.setVisible(true);
        });
        return catCount[0];
    }

    private int[] work_with_image(BufferedImage img) {
        int[] rez = {0, 0};
        CascadeClassifier face_cascade = new CascadeClassifier("haarcascade_frontalcatface.xml");
        if (face_cascade.empty()) {
            System.out.println("Ошибка загрузки");
            return rez;
        } else {
            System.out.println("Загрузили \"haarcascade_frontalcatface\"");
        }

        byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        Mat inputFrame = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        inputFrame.put(0, 0, pixels);

        face_cascade.detectMultiScale(inputFrame, faces);
        rez[0] = faces.toArray().length;

        System.out.println("Обнаружено " + rez[0] + " котов");

        face_cascade = new CascadeClassifier("haarcascade_frontalcatface_extended.xml");
        if (face_cascade.empty()) {
            System.out.println("Ошибка загрузки");
            return rez;
        } else {
            System.out.println("Загрузили \"haarcascade_frontalcatface_extended\"");
        }

        face_cascade.detectMultiScale(inputFrame, faces2);
        rez[1] = faces.toArray().length;

        System.out.println("Обнаружено " + rez[1] + " котов");
        return rez;
    }
}