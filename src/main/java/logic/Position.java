package logic;

/**
 * Klasse welche eine Koordinate darstellt
 *
 * @author Anton Burmester
 */
class Position {
    private int x; //Nutzlast der Breitenkoordinate
    private int y; //Nutzlast der Hoehenkoordinate

    /**
     * Public Konstruktor welcher x und y initialisiert
     * @param x die Breitenkoordinate
     * @param y die Hoehenkoordinate
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * getter welcher x zurueckgibt
     * @return die Breitenkoordinate
     */
    public int getX(){
        return(this.x);
    }

    /**
     * getter welcher y zurueckgibt
     * @return die Hoehenkoordinate
     */
    public int getY(){
        return(this.y);
    }

    /**
     * setter welcher die Breitenkoordinate setzt
     * @param x die Breitenkoordinate
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * setter welcher die Hoehenkoordinate setzt
     * @param y die Hoehenkoordinate
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * toString Methode ueberschrieben mit den x und y Attributen
     * @return x und y als String
     */
    @Override
    public String toString() {
        return("x: " + this.x + " y: " + this.y);
    }
}
