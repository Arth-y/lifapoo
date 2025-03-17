package modele ;

import java.io.*;
import java.util.Scanner;

public class utils {
    public void read() {

        String test ;

        try {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream("file.txt");
            Scanner scanner = new Scanner(file);

            //renvoie true s'il y a une autre ligne à lire
            while (scanner.hasNext()) {
                test = scanner.next();
                System.out.print("OKKK3");
           }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         ;
    }
}