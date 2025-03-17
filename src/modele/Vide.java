package modele;

public class Vide extends Case {

    public int bloc_type = 0 ;
    public Vide(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }



}
