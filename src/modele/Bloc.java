package modele;

public class Bloc extends Entite {

    /* 
    Constructeur par défaut
    */

    public int bloc_type = 0 ;
    public Bloc(Jeu _jeu, Case c) {
        super(_jeu, c);
    }

    /*
    Fonction qui déplace le bloc dans une direction d
    elle renvoie le jeu après le changement de case du bloc 
    */
    public boolean pousser(Direction d) {
        return jeu.deplacerEntite(this, d);
    }

}
