package logic;

/**
 * Klasse welche einen Spielstein darstellt inklusive der Rotation und ob der Spielstein valide liegt (Farben richtig)
 *
 * @author Anton Burmester
 */
public class Tile {
    private TileNames tile;
    private int rotation;
    private boolean isValid;

    /**
     * Konstruktor welcher einen bestimmten Spielstein ohne bestimmte Rotation initialisiert
     * @param tile der bestimmte Spielstein
     */
    public Tile(TileNames tile){
        this(tile, 0);
    }

    /**
     * Konstruktor welcher einen bestimmten Spielstein inklusive Rotation initialisiert
     * @param tile der bestimmte Spielstein
     * @param rotations die Rotation des Spielsteins
     */
    public Tile(TileNames tile, int rotations){
        this.tile = tile;
        this.rotation = 0;
        for(int i = 0; i < rotations; i++){
            this.rotateTile();
        }
    }

    /**
     * getter welcher den Spielstein bzw. das Motiv zurueckgibt
     * @return das Motiv des Spielsteins bzw. Mosaiksteins
     */
    public TileNames getTile(){
        return(this.tile);
    }

    /**
     * getter welcher den Spielstein bzw. das Motiv zurueckgibt als String (nicht gedreht -> Enum Wert)
     * @return das Motiv des Spielsteins bzw. Mosaiksteins als String
     */
    public String getTileString(){
        return(this.tile.toString());
    }

    /**
     * die Rotation des Spielsteins
     * @return die Rotation (0, 90, 180, 270)
     */
    public int getRotation(){
        return(this.rotation);
    }

    /**
     * Getter welcher zurueckgibt, ob das Mosaikstueck valide ist
     * @return ob das Mosaikstueck an mindestens einer Seite an eine andere Farbe grenzt
     */
    public boolean isValid(){
        return(this.isValid);
    }

    /**
     * Methode welche den Namen des Spielsteins nach der Drehung zurueckgibt
     * @return der Name des Spielsteins unter Berucksichtigung der Drehung
     */
    public String getTileNameWithRotation(){
        StringBuilder sb = new StringBuilder(this.tile.name());

        for(int i = 0; i < this.rotation; i+=90){ // durchlaeuft 90 Grad schritte
            sb.insert(0, sb.charAt(sb.length() - 1)); //fuegt das letzte Zeichen an den Anfang
            sb.deleteCharAt(sb.length() - 1); //entfernt das letzte Zeichen, da es wieder am Anfang ist
        }

        return(sb.toString());
    }

    /**
     * Setter welcher die isValid Variable setzt
     * @param input ob True oder False
     */
    public void setIsValid(boolean input){
        this.isValid = input;
    }

    /**
     * prueft ob des sich bei dem Spielstein um ein Loch (HHHH) oder einen Platzhalter handelt (NNNN)
     * @return
     */
    public boolean isTileLayable(){
        return(!(this.tile.equals(TileNames.NNNN) || this.tile.equals(TileNames.HHHH)));
    }

    /**
     * Methode welche auf Grundlage eines Strings eine Tile Klasse zurueckgibt mit dem richtigen Spielstein
     * sowie seiner Drehung
     * @param inputTileName der String des Spielsteins (kann auch gedreht sein)
     * @return die Klasse des erstellten Spielsteins
     */
    public static Tile getTileClassFromTileName(String inputTileName){
        Tile result = null;
        Tile currTile;
        for(TileNames currTileName : TileNames.values()) { //durchlaeuft jeden Spielstein
            currTile = new Tile(currTileName); //initialisiert mit diesem Motiv eine Klasse des Spielsteins
            for (int i = 0; i < 4; i++) { //durchlauft jede Rotation des Spielsteins
                if(currTile.getTileNameWithRotation().equals(inputTileName)){ //prueft ob das Motiv des aktuellen Spielsteins
                    // inklusive seiner Drehungen dem uebergebenen Spielstein gleicht
                    result = currTile;
                    return(result);
                }
                currTile.rotateTile(); //rotiert den Spielstein um 90 Grad
            }
        }
        return(result);
    }

    /**
     * Methode welche den Spielstein um 90 Grad rechtsrum rotiert (den Wert modifiziert welcher die Rotation speichert)
     */
    public void rotateTile(){
        this.rotation = this.rotation == 270 ? 0 : this.rotation + 90; //ternaerer Operator: wenn 270 Grad + 90 = 360
        // also wieder am Anfang deshalb 0
    }
}
