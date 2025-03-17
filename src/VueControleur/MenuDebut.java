package VueControleur;

import modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class MenuDebut extends JFrame {
    public MenuDebut() {

        setTitle("Menu Sokoban");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton jselect = new JButton("Selectionner un niveau"); //Bouton qui envoie vers un menu de selection
        JButton jquitter = new JButton("Quitter"); //Bouton qui ferme la fenetre

        jselect.addActionListener(e -> { //Quan le bouton de selection sera préssé

            MenuSelection menuS = new MenuSelection(); //on crée un menu de selection et on l'affiche
            menuS.setVisible(true);
            setVisible(false); //on oublie pas de fermer ce menu
        });

        jquitter.addActionListener(e -> System.exit(0)); //Bouton quitter

        JPanel jp = new JPanel(new GridLayout(3, 1, 0,70)); //on ordonne nos boutons
        jp.add(jselect);
        jp.add(jquitter); //on ajoute nos deux boutons

        add(jp);
    }

}


