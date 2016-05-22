/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaurl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaffs
 */
public class JavaUrl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException {
        //  Create storage directory if not exists.
        File storageDirectory = new File("storageDirectory");

        if (storageDirectory.exists()) {
            System.out.println("Storage directory exists");
        } else {
            storageDirectory.mkdir();
            System.out.println("Storage directory created");
        }

        // Print storage capacity.
        File[] files = storageDirectory.listFiles();

        long capacity = 0;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                capacity = capacity + files[i].length();
            }
        }

        System.out.println("Storage capacity : " + capacity + " bytes");

        // Print already downloaded file names and sizes. "music.mp3 (12345 bytes); \n music.wav (41212 bytes)"
        if (storageDirectory.exists()) {

            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName() + " (" + files[i].length() + " bytes)");
            }
            //System.out.println("Storage directory is empty");
        } else {
            System.out.println("Storage directory is empty");
        }

        List<URL> musicList = new ArrayList<>();

        List<URL> userUrlList = new ArrayList<>();

        // Request user to enter url's. (Stop when enter empty line)
        try {
            while (true) {
                System.out.println("Please, enter URL's");
                Scanner scanner1 = new Scanner(System.in);
                String urlDown = scanner1.nextLine();

                if (urlDown.isEmpty()) {
                    System.out.println("OK. Finished to add");
                    break;
                }

                URL url = new URL(urlDown);
                //List<URL> userUrlList = new ArrayList<>();
                // String url = scanner1.nextLine();
                userUrlList.add(url);
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaUrl.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < userUrlList.size(); i++) {
            System.out.println(userUrlList);
        }

//        if (true) {
//            return;
//        }
        // Uniform Resource Locator
//        URL url = new URL("http", "www.ex.ua", 80, "/playlist/148363.m3u");
//        URL url = new URL("http", "www.ex.ua", "/playlist/148363.m3u");
        URL playlistUrl = new URL("http://www.ex.ua/playlist/148363.m3u");

        try (Scanner scanner = new Scanner(playlistUrl.openStream())) {

            while (scanner.hasNextLine()) {
                URL musicUrl = new URL(scanner.nextLine());
                musicList.add(musicUrl);
            }

        } catch (IOException ex) {
            Logger.getLogger(JavaUrl.class.getName()).log(Level.SEVERE, null, ex);
        }

        //  Object str = "Line";
        // assert str instanceof String;
        //assert str instanceof Integer;
        //            System.out.println(musicUrl);
        // TODO: Download linked file to storage directory.
        //for (int i = 0; i < userUrlList.size(); i++){
        // File file = new File("C://storageDirectory");
        // проверка закачки файла по одной ссылке (тестовый пример)
        URL urlTest = new URL("http://www.ex.ua/get/535653");
        File fileDownload = new File(storageDirectory, String.format("%d.mp3", System.currentTimeMillis()));

        try (FileOutputStream fos = new FileOutputStream(fileDownload); BufferedInputStream bis = new BufferedInputStream(urlTest.openStream())) {
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaUrl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaUrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //}

        // TODO: If file already exists, ask user for action (skip, rewrite).
        for (URL musicUrl : musicList) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(JavaUrl.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.printf("\r%25s", musicUrl);
        }

        // Example for one-line printing.
//        System.out.printf("\rDownloading: [%3d%]", 45);
//        System.out.printf("\n\r[%-25s]", "=======>");
        System.out.println();

    }

}
