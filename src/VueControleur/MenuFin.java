package VueControleur;

import modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class MenuFin extends JFrame{

    public MenuFin() {

        setTitle("Menu Fin");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea jselect = new JTextArea("Vous Avez gagné !!!");
        JButton jreturn = new JButton("Revenir à l'écran d'acceuil");
        JButton jquitter = new JButton("Quitter");


        jreturn.addActionListener(e -> {

            MenuDebut menu = new MenuDebut();
            menu.setVisible(true);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setVisible(true);
            setVisible(false);
        });

        jquitter.addActionListener(e -> System.exit(0));

        JPanel jp = new JPanel(new GridLayout(3, 1, 0,70));
        jp.add(jselect);
        jp.add(jreturn);
        jp.add(jquitter);

        add(jp);
    }
}
