/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.*;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import java.io.FileInputStream ;


public class Jeu extends Observable {

    public static final int SIZE_X = 40; //taille maximale d'une grille
    public static final int SIZE_Y = 40;
    public String niveau_actuel = "niveau1.txt";
    public static int SizeX; //taille réelle de notre grille
    public static int SizeY;
    private Heros heros;
    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    private Case[][] grilleEntites = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées


    public Jeu() {
        initialisationNiveau("niveau1.txt");
    }

    public Jeu(String path) {
        initialisationNiveau(path);
    }
    
    public Case[][] getGrille() {
        return grilleEntites;
    }

    public void deplacerHeros(Direction d) {
        heros.avancerDirectionChoisie(d);
        setChanged();
        notifyObservers();
    }

    
    /*private void initialisationNiveau() {




        // murs extérieurs horizontaux
        for (int x = 0; x < SIZE_X; x++) {
            addCase(new Mur(this), x, 0);
            addCase(new Mur(this), x, SIZE_Y - 1);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < SIZE_Y ; y++) {
            addCase(new Mur(this), 0, y);
            addCase(new Mur(this), SIZE_X - 1, y);
        }

        for (int x = 1; x < SIZE_X -1 ; x++) {
            for (int y = 1; y < SIZE_Y - 1; y++) {
                addCase(new Vide(this), x, y);
            }

        }

        heros = new Heros(this, grilleEntites[4][4]);
        Bloc b = new Bloc(this, grilleEntites[8][7]);
    }*/

    public void initialisationNiveau(String niveau) { //crée un jeu à partir d'un fichier txt
        int Mur = 9;
        niveau_actuel = niveau;
        try {
            FileInputStream file = new FileInputStream(niveau);
            Scanner scanner = new Scanner(file); //Scanner qui va nous permettre de lire
            //Voir niveau1.txt pour l'architecture du fichier texte
            SizeX = Integer.parseInt(scanner.next());
            System.out.println("size x : " + SizeX); //on récupere la taille

            SizeY = Integer.parseInt(scanner.next());
            System.out.println("size y : " + SizeY);

            for (int y = 0; y < SizeY; y++) { //premier tableau : case
                for (int x = 0; x < SizeX; x++) {
                    switch(Integer.parseInt(scanner.next())){
                        case 9: // MUR
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"9");
                            addCase(new Mur(this), x, y);
                            break ;
                        case 0: // SOL
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"0");
                            addCase(new Vide(this), x, y);
                            break ;
                        case 1: // OBJECTIF ROUGE
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"1");
                            Vide myCase1 = new Vide(this);
                            myCase1.bloc_type = 1;
                            addCase(myCase1, x, y);
                            break ;

                        case 2: // OBJECTIF VERT
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"2");
                            Vide myCase2 = new Vide(this);
                            myCase2.bloc_type = 2;
                            addCase(myCase2, x, y);
                            break ;

                        case 3: // HERBE
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"4");
                            Vide myCase3 = new Vide(this);
                            myCase3.bloc_type = 3;
                            addCase(myCase3, x, y);
                            break ;

                        case 4: // GLACE
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"4");
                            Vide myCase4 = new Vide(this);
                            myCase4.bloc_type = 4;
                            addCase(myCase4, x, y);
                            break ;

                        case 5: // PORTAL A
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"4");
                            Vide myCase5 = new Vide(this);
                            myCase5.bloc_type = 5;
                            addCase(myCase5, x, y);
                            break ;

                        case 6: // PORTAL B
                            //System.out.println("case n°" + x +" "+ y +" valeur : " +"4");
                            Vide myCase6 = new Vide(this);
                            myCase6.bloc_type = 6;
                            addCase(myCase6, x, y);
                            break ;
                    }
            }}

            for (int y = 0; y < SizeY; y++) { //pour les entités
                for (int x = 0; x < SizeX; x++) {
                    switch(Integer.parseInt(scanner.next())){
                        case 1: // HERO
                            //System.out.println("GRILLE 2 case n°" + x +" "+ y +" HERO");
                            heros = new Heros(this, grilleEntites[x][y]);
                            break ;
                        case 2: // BLOC ROUGE
                            //System.out.println("GRILLE 2 case n°" + x +" "+ y +" BLOC");
                            Bloc b1 = new Bloc(this, grilleEntites[x][y]);
                            b1.bloc_type = 1 ;
                            break ;

                        case 3: // BLOC VERT
                            //System.out.println("GRILLE 2 case n°" + x +" "+ y +" BLOC");
                            Bloc b2 = new Bloc(this, grilleEntites[x][y]);
                            b2.bloc_type = 2 ;
                            break ;

                        case 4: // BLOC NEUTRE
                            //System.out.println("GRILLE 2 case n°" + x +" "+ y +" BLOC");
                            Bloc b3 = new Bloc(this, grilleEntites[x][y]);
                            b3.bloc_type = 0 ;
                            break ;

                    }
                }}
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers();
        }

    public int getSizeX(String niveau) {
        return SizeX;
    }

    public int getSizeY(String niveau) {
        return SizeY;
    }
    private void addCase(Case e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }
    

    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = true;

        Point pCourant = map.get(e.getCase());

        Point pCible = calculerPointCible(pCourant, d);

        if (contenuDansGrille(pCible)) {
            Entite eCible = caseALaPosition(pCible).getEntite();
            if (eCible != null) {
                eCible.pousser(d);
            }
        }

        // si la case est libérée
        if (caseALaPosition(pCible).peutEtreParcouru()) {
            e.getCase().quitterLaCase();
            caseALaPosition(pCible).entrerSurLaCase(e);

            if(getGrille()[pCible.x][pCible.y] instanceof Vide &&
                    ((Vide) getGrille()[pCible.x][pCible.y]).bloc_type == 4){

                Point NextPcible = calculerPointCible(pCible, d);

                if (contenuDansGrille(NextPcible)) {
                    Entite eCible2 = caseALaPosition(NextPcible).getEntite();
                    if (eCible2 == null) {
                        deplacerEntite(e,d);
                    }
                }
            }

            if(getGrille()[pCible.x][pCible.y] instanceof Vide &&
                    ((Vide) getGrille()[pCible.x][pCible.y]).bloc_type == 5){

                int xPortalB = 0 ;
                int yPortalB = 0 ;

                for (int y = 0; y < SizeY; y++) {
                    for (int x = 0; x < SizeX; x++) {
                        if(getGrille()[x][y] instanceof Vide &&
                                ((Vide) getGrille()[x][y]).bloc_type == 6) {
                            xPortalB = x;
                            yPortalB = y;
                        }
                    }
                }

                Point PortalB = new Point();
                PortalB.x = xPortalB ;
                PortalB.y = yPortalB ;

                e.getCase().quitterLaCase();
                caseALaPosition(PortalB).entrerSurLaCase(e);
            }

            if(getGrille()[pCible.x][pCible.y] instanceof Vide &&
                    ((Vide) getGrille()[pCible.x][pCible.y]).bloc_type == 6){

                int xPortalB = 0 ;
                int yPortalB = 0 ;

                for (int y = 0; y < SizeY; y++) {
                    for (int x = 0; x < SizeX; x++) {
                        if(getGrille()[x][y] instanceof Vide &&
                                ((Vide) getGrille()[x][y]).bloc_type == 5) {
                            xPortalB = x;
                            yPortalB = y;
                        }
                    }
                }

                Point PortalB = new Point();
                PortalB.x = xPortalB ;
                PortalB.y = yPortalB ;

                e.getCase().quitterLaCase();
                caseALaPosition(PortalB).entrerSurLaCase(e);
            }


        } else {
            retour = false;
        }
        return retour ;
    }

    private Point getpCible(Direction d, Point pCourant) {
        return calculerPointCible(pCourant, d);
    }


    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }
    

    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < getSizeX(niveau_actuel) && p.y >= 0 && p.y < getSizeX(niveau_actuel);
    }
    
    private Case caseALaPosition(Point p) {
        Case retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

}
