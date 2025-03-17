package VueControleur;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.* ;

import modele.*;



/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon[] icoHeros; //4 Icon pour les 4 direction
    private int direction = 1; //le presonnage regarde à droite au début
    private ImageIcon[] icoVides; //pour les cases
    private ImageIcon[] icoBlocs; //pour les blocs
    private ImageIcon[] icoHerosGlace; //diffrentes images de notre héro pour s'adapter au fond
    private ImageIcon[] icoHeroPortalA;
    private ImageIcon[] icoHeroPortalB;
    private ImageIcon icoMur;
    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SizeX; //on récupere la taille du jeu
        sizeY = jeu.SizeY;
        jeu = _jeu;
        icoHeros = new ImageIcon[4]; //on définit tout nos tableaux
        icoHerosGlace = new ImageIcon[4];
        icoVides = new ImageIcon[7];
        icoBlocs = new ImageIcon[3];
        icoHeroPortalA = new ImageIcon[4];
        icoHeroPortalB = new ImageIcon[4];
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();

        jeu.addObserver(this);

        mettreAJourAffichage();

    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée

                    case KeyEvent.VK_LEFT : direction = 3; jeu.deplacerHeros(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : direction = 1; jeu.deplacerHeros(Direction.droite); break;
                    case KeyEvent.VK_DOWN : direction = 2; jeu.deplacerHeros(Direction.bas); break;
                    case KeyEvent.VK_UP : direction = 0; jeu.deplacerHeros(Direction.haut); break;
                    //on a ajouter la variable direction qui nous permet d'afficher notre héros
                }
            }
        });
    }


    private void chargerLesIcones() {
        //on charge toutes nos images
        icoHeros[0] = chargerIcone("Images/HeroUp.png");
        icoHeros[1] = chargerIcone("Images/HeroRight.png");
        icoHeros[2] = chargerIcone("Images/HeroDown.png");
        icoHeros[3] = chargerIcone("Images/HeroLeft.png");

        icoHerosGlace[0] = chargerIcone("Images/HeroUpGlace.png");
        icoHerosGlace[1] = chargerIcone("Images/HeroRightGlace.png");
        icoHerosGlace[2] = chargerIcone("Images/HeroDownGlace.png");
        icoHerosGlace[3] = chargerIcone("Images/HeroLeftGlace.png");

        icoHeroPortalA[0] = chargerIcone("Images/HeroUpPortalA.png");
        icoHeroPortalA[1] = chargerIcone("Images/HeroRightPortalA.png");
        icoHeroPortalA[2] = chargerIcone("Images/HeroDownPortalA.png");
        icoHeroPortalA[3] = chargerIcone("Images/HeroLeftPortalA.png");

        icoHeroPortalB[0] = chargerIcone("Images/HeroUpPortalB.png");
        icoHeroPortalB[1] = chargerIcone("Images/HeroRightPortalB.png");
        icoHeroPortalB[2] = chargerIcone("Images/HeroDownPortalB.png");
        icoHeroPortalB[3] = chargerIcone("Images/HeroLeftPortalB.png");

        icoVides[0] = chargerIcone("Images/Vide.png");
        icoVides[1] = chargerIcone("Images/Rouge.png");
        icoVides[2] = chargerIcone("Images/Vert.png");
        icoVides[3] = chargerIcone("Images/Herbe.png");
        icoVides[4] = chargerIcone("Images/ice.png");

        icoBlocs[0] = chargerIcone("Images/Crate_Beige.png");
        icoBlocs[2] = chargerIcone("Images/BlocVert.png");
        icoBlocs[1] = chargerIcone("Images/BlocRouge.png");

        icoVides[5] = chargerIcone("Images/PortalA.jpg");
        icoVides[6] = chargerIcone("Images/PortalB.jpg");

        icoMur = chargerIcone("Images/Mur.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Sokoban");
        int width = 800;
        int height = 800;
        ReSizeImg(jeu.getNiveau());
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    private void ReSizeImg(String niveau){ //ofnction qui va redimensionner nos images
        int max ;
        if(jeu.getSizeX(niveau) > jeu.getSizeY(niveau)){ //gere les grilles non carrées
            max = jeu.getSizeX(niveau);
        }else{
            max = jeu.getSizeY(niveau);
        }
        //System.out.println("RESIZE EN COURS : " + max);

        for(int i = 0; i <4 ; i ++) { //on fait un boucle par tableau
            Image imgHero = icoHeros[i].getImage();
            Image temp = imgHero.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
            icoHeros[i] = new ImageIcon(temp);
        }

        for(int i = 0; i <4 ; i ++) {
            Image imgHeroGlace = icoHerosGlace[i].getImage();
            Image temp = imgHeroGlace.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
            icoHerosGlace[i] = new ImageIcon(temp);
        }

        for(int i = 0; i <7 ; i ++) {
            Image imgVide = icoVides[i].getImage();
            Image temp = imgVide.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
            icoVides[i] = new ImageIcon(temp);
        }

        for(int i = 0; i <3 ; i ++) {
            Image imgbloc = icoBlocs[i].getImage();
            Image temp = imgbloc.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
            icoBlocs[i] = new ImageIcon(temp);
        }

        for(int i = 0; i <4 ; i ++) {
            Image imgPortalA = icoHeroPortalA[i].getImage();
            Image temp = imgPortalA.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
            icoHeroPortalA[i] = new ImageIcon(temp);
        }

        for(int i = 0; i <4 ; i ++) {
            Image imgPortalB = icoHeroPortalB[i].getImage();
            Image temp = imgPortalB.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
            icoHeroPortalB[i] = new ImageIcon(temp);
        }

        Image imgMur = icoMur.getImage();
        Image temp = imgMur.getScaledInstance(800 / max, 800 / max, Image.SCALE_SMOOTH);
        icoMur = new ImageIcon(temp);


        System.out.println("RESIZE FINIT");
    }
    private boolean aTermine() //fonction qui détermine si le joueur a gagné ou non
    {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if(((jeu.getGrille()[x][y] instanceof Vide && ((Vide) jeu.getGrille()[x][y]).bloc_type == 1) || (jeu.getGrille()[x][y] instanceof Vide && ((Vide) jeu.getGrille()[x][y]).bloc_type == 2)) // Si une case est n'est pas un mur
                        // Et que cette case necessite un bloc pour que le niveau soit termine
                        && !(jeu.getGrille()[x][y].getEntite() instanceof Bloc && //et que soit il n'y a pas de bloc
                        ((Bloc) jeu.getGrille()[x][y].getEntite()).bloc_type == ((Vide) jeu.getGrille()[x][y]).bloc_type)) //soit le bloc n'est pas de la bonne couleur
                {
                    return false; //Alors toutes les cases ne sont pas satisfaites, donc le jeu n'est pas termine
                }
            }
        }
        //System.out.println("GG");
        return true; //Si aucune case ne return false, alors le jeu est termine
    }

    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) { //pour toutes les cases
            for (int y = 0; y < sizeY; y++) {
                Case c = jeu.getGrille()[x][y];

                if (c != null) {

                    Entite e = c.getEntite();

                    if (e != null) {
                        if (c.getEntite() instanceof Heros) {
                            if(jeu.getGrille()[x][y] instanceof Vide && ((Vide) jeu.getGrille()[x][y]).bloc_type == 4){
                                tabJLabel[x][y].setIcon(icoHerosGlace[direction]);
                            }else{
                                if(jeu.getGrille()[x][y] instanceof Vide && ((Vide) jeu.getGrille()[x][y]).bloc_type == 5) {
                                    tabJLabel[x][y].setIcon(icoHeroPortalA[direction]);
                                }else{
                                    if(jeu.getGrille()[x][y] instanceof Vide && ((Vide) jeu.getGrille()[x][y]).bloc_type == 6) {
                                        tabJLabel[x][y].setIcon(icoHeroPortalB[direction]);
                                    }else{
                                        tabJLabel[x][y].setIcon(icoHeros[direction]);}}
                            }
                        } else if (c.getEntite() instanceof Bloc) {
                            tabJLabel[x][y].setIcon(icoBlocs[((Bloc) c.getEntite()).bloc_type]);
                        }
                    } else {
                        if (jeu.getGrille()[x][y] instanceof Mur) {
                            tabJLabel[x][y].setIcon(icoMur);
                        } else if (jeu.getGrille()[x][y] instanceof Vide) {
                            tabJLabel[x][y].setIcon(icoVides[((Vide) jeu.getGrille()[x][y]).bloc_type]);
                        }
                    }
                }
            }
        }



        if(this.aTermine()){ //si le joueur a gagné, on le dirige vers un écran de fin
            MenuFin menuF = new MenuFin();
            menuF.setVisible(true);
            setVisible(false);
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
    }
}
