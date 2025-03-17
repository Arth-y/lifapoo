package modele;

public class niveau {
    private int Size_X ;
    private int Size_Y ;

    private String[][] Plateau = new String[Size_X][Size_Y];

    public niveau(int xArg, int yArg) {
        this.Size_X = xArg ;
        this.Size_Y = yArg ;
    }

    public String getPlateau(int x, int y){
        return Plateau[x][y] ;
    }

    public void setPlateau(int x, int y, String NewCase){
        Plateau[x][y] = NewCase ;
    }
}
