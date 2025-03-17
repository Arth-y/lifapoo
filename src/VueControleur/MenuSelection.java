package VueControleur;

import modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class MenuSelection extends JFrame{

    public MenuSelection() {

        setTitle("Menu Selection");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea jselect = new JTextArea("Entrez le numero du niveau"); //
        JButton jlaunch = new JButton("Lancer le niveau");


        jlaunch.addActionListener(e -> {

            if(Integer.parseInt(jselect.getText()) <= 4) { //si le niveau existe
                Jeu jeu = new Jeu("niveau" + jselect.getText() + ".txt"); //on défini un niveau à partir de son chemin
                VueControleur vc = new VueControleur(jeu);
                vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                vc.setVisible(true);
                setVisible(false);
            }
        });

        JPanel jp = new JPanel(new GridLayout(3, 1, 0,70));
        jp.add(jselect);
        jp.add(jlaunch);

        add(jp);
    }
}
