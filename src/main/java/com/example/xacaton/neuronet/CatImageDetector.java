package com.example.xacaton.neuronet;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.lang.Thread.sleep;

@Log4j2
public class CatImageDetector {
    MatOfRect faces = new MatOfRect();
    MatOfRect faces2 = new MatOfRect();

    public static int[] countCatsAndMarkIt(BufferedImage image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        final int[][] catCount = {new int[0]};

        // вывести в окне картинку
        // нарисовать рамки в области распознавания
        // запуск по ключу
        SwingUtilities.invokeLater(() -> {
            //BufferedImage resizedImage = resizeImage(img, 640, 480);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(5));
            CatImageDetector detecor = new CatImageDetector();
            catCount[0] = detecor.countCatsFromImage(image);
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
        });
        try {
            sleep(5_000);
            // Заглушка дабы поток с распознованием успел завершиться
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return catCount[0];
    }

    private int[] countCatsFromImage(BufferedImage img) {
        int[] rez = {0, 0};
        CascadeClassifier face_cascade = new CascadeClassifier("haarcascade_frontalcatface.xml");
        if (face_cascade.empty()) {
            log.error("Load Error");
            return rez;
        } else {
            log.info("Load \"haarcascade_frontalcatface\"");
        }
//
        byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        Mat inputFrame = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        inputFrame.put(0, 0, pixels);
//
        face_cascade.detectMultiScale(inputFrame, faces);
        rez[0] = faces.toArray().length;

        log.info(rez[0] + "cats detected");

        face_cascade = new CascadeClassifier("haarcascade_frontalcatface_extended.xml");
        if (face_cascade.empty()) {
            log.error("Load Error");
            return rez;
        } else {
            log.info("Load \"haarcascade_frontalcatface_extended\"");
        }

        face_cascade.detectMultiScale(inputFrame, faces2);
        rez[1] = faces.toArray().length;

        log.info(rez[1] + "cats detected");
        return rez;
    }
}
