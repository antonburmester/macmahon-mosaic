package logic;

/**
 * Klasse welche einen Spielstein darstellt inklusive der Rotation und ob der Spielstein valide liegt (Farben richtig)
 *
 * @author Anton Burmester
 */
public class Tile {
    private TileNames tile; //Nutzlast des Motivs des Spielsteins
    private Rotation rotation; //Nutzlast der Rotation des Spielsteins

    /**
     * Konstruktor welcher einen bestimmten Spielstein ohne bestimmte Rotation initialisiert
     * @param tile der bestimmte Spielstein
     */
    public Tile(TileNames tile){
        this(tile, Rotation.R0);
    }

    /**
     * Konstruktor welcher einen bestimmten Spielstein inklusive Rotation initialisiert
     * @param tile der bestimmte Spielstein
     * @param rotation die Rotation des Spielsteins (R0 = 0°; R3 = 270°)
     */
    public Tile(TileNames tile, Rotation rotation){
        this.tile = tile;
        this.rotation = rotation;
    }

    /**
     * Konstruktor welcher einen bestimmten Spielstein durch seinen String initialisiert inklusive Rotation
     * @param tileName der Name des Spielsteins (kann auch rotiert sein)
     */
    public Tile(String tileName){
        for(TileNames currTileName : TileNames.values()){

            for(Rotation currRotation : Rotation.values()){
                if(simulateTileNameWithRotation(currTileName.name(), currRotation).equals(tileName)){
                    this.tile = currTileName;
                    this.rotation = currRotation;
                    return;
                }
            }
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
    public Rotation getRotation(){
        return(this.rotation);
    }

    /**
     * Methode welche den aktuellen Spielstein rotiert und durch die Methode getTileNameWithRotation
     */
    public String getTileNameWithRotation(){
        return(Tile.simulateTileNameWithRotation(this.tile.name(), this.rotation));
    }

    /**
     * Methode welche den Namen des Spielsteins nach der Drehung zurueckgibt
     * @param input der String welcher rotiert werden soll
     * @return der Name des Spielsteins unter Berucksichtigung der Drehung
     */
    public static String simulateTileNameWithRotation(String input, Rotation rotation){
        StringBuilder sb = new StringBuilder(input);

        for (Rotation currRotation : Rotation.values()) {
            if (currRotation.ordinal() >= rotation.ordinal()) break; //nur bis zur gewuenschten Rotation
            // uebergebenen Rotation
            sb.insert(0, sb.charAt(sb.length() - 1)); //fuegt das letzte Zeichen an den Anfang
            sb.deleteCharAt(sb.length() - 1); //entfernt das letzte Zeichen, da es wieder am Anfang ist
        }

        return(sb.toString());
    }

    /**
     * prueft ob des sich bei dem Spielstein um ein Loch (HHHH) oder einen Platzhalter handelt (NNNN)
     * @return ob der Spielstein nicht (NNNN oder HHHH)
     */
    public boolean isNormalGameTile(){
        return(!(this.tile.equals(TileNames.NNNN) || this.tile.equals(TileNames.HHHH)));
    }

    /**
     * prueft ob des sich bei dem Spielstein um einen Platzhalter (NNNN) handelt
     * @return ob der Spielstein ein Platzhalter (NNNN) ist
     */
    public boolean isPlaceHolderTile(){
        return(this.tile.equals(TileNames.NNNN));
    }

    /**
     * prueft ob des sich bei dem Spielstein um ein Loch handelt
     * @return ob der Spielstein ein Loch ist
     */
    public boolean isHoleTile(){
        return(this.tile.equals(TileNames.HHHH));
    }

    /**
     * prueft ob es sich bei dem Spielstein um einen Rand kompatiblen Spielstein handelt
     * @return ob RRRR, GGGG oder YYYY
     */
    public boolean isTileBorderLayable(){
        return(this.tile.equals(TileNames.RRRR) || this.tile.equals(TileNames.GGGG) ||
                this.tile.equals(TileNames.YYYY));
    }

    /**
     * prueft ob es sich bei dem Spielstein String um einen Rand kompatiblen Spielstein handelt
     * NNNN hier vorhanden, da das Spielfeld auch teils mit leerem Rand geladen werden kann
     * @return ob RRRR, GGGG, YYYY oder NNNN (leer)
     */
    public static boolean isTileStringBorderLayable(String input){
        return(input.equals(TileNames.RRRR.name()) || input.equals(TileNames.GGGG.name()) ||
                input.equals(TileNames.YYYY.name()) || input.equals(TileNames.NNNN.name()));
    }

    /**
     * prueft ob es sich bei dem Spielstein String um einen Ecken kompatiblen Spielstein handelt
     * @return ob NNNN (leer)
     */
    public static boolean isTileStringEdgeLayable(String input){
        return(input.equals(TileNames.NNNN.name()));
    }

    /**
     * Methode welche auf Grundlage eines Strings eine Tile Klasse zurueckgibt mit dem richtigen Spielstein
     * sowie seiner Drehung
     * @param inputTileName der String des Spielsteins (kann auch gedreht sein)
     * @return die Klasse des erstellten Spielsteins
     */
    public static Tile getTileClassFromTileName(String inputTileName){
        Tile currTile;
        for(TileNames currTileName : TileNames.values()) { //durchlaeuft jeden Spielstein
            currTile = new Tile(currTileName); //initialisiert mit diesem Motiv eine Klasse des Spielsteins
            for(Rotation currRotation : Rotation.values()){ //durchlauft jede Rotation
                if(currTile.getTileNameWithRotation().equals(inputTileName)){ //prueft ob das Motiv des aktuellen
                    // Spielsteins inklusive seiner Drehungen dem uebergebenen Spielstein gleicht
                    return(currTile);
                }
                currTile.rotateTile(); //rotiert den Spielstein um 90 Grad
            }
        }
        return(null);
    }

    /**
     * Methode welche den Spielstein um 90 Grad rechtsrum rotiert (den Wert modifiziert welcher die Rotation speichert)
     */
    public void rotateTile(){
        this.rotation = this.rotation == Rotation.R3 ? Rotation.R0
                : Rotation.values()[this.rotation.ordinal() + 1]; //ternaerer Operator: wenn 270 Grad + 90 = 360
        // also wieder am Anfang deshalb 0
    }

    /**
     * Metdhode welche die Rotation eines Spielsteins setzt
     * @param rotation die Rotation
     */
    public void setTileRotation(Rotation rotation){
        this.rotation = rotation;
    }

    /**
     * Methode welche die Rotation des Spielsteins zuruecksetzt
     */
    public void resetTileRotation(){
        this.rotation = Rotation.R0;
    }

    /**
     * Methode welche aus einem String den dazugehoerigen TileNames zurueckgibt
     * Hierfuer werden alle TileNames namen durchlaufen und jeder Name in jeder Rotation getestet.
     * @param tileName der Name des Spielsteins (kann auch rotiert sein)
     * @return der unrotierte standard Name des Spielsteins
     */
    public static String getTileNamesString(String tileName){
        for(TileNames currTileName : TileNames.values()){ //durchlaeuft jeden Spielstein
            for(Rotation currRotation : Rotation.values()){ //durchlauft jede Rotation
                if(simulateTileNameWithRotation(currTileName.name(), currRotation).equals(tileName)){
                    return(currTileName.name());
                }
            }
        }
        return(null);
    }

    /**
     * Methode welche den Index eines uebergebenen Spielsteins zurueckgibt
     * @return der Index des Spielsteins; wenn nichts gefunden -1
     */
    public int getTileIndex(){
        for(int i = 0; i < Game.TILE_AMOUNT_COMPLETE; i++){
            if(TileNames.values()[i].equals(this.tile))
                return(i);
        }
        return(-1);
    }

    /**
     * Methode welche die Instanz eines Spielstein dupliziert
     * @return die Instanz als neue unabhaengige Instanz
     */
    public Tile cloneTile() {
        return(new Tile(this.tile, this.rotation));
    }

    /**
     * Methode welche die toString Methode fuer die Tile Klasse ueberschreibt
     * @return alle Nutzlasten im String
     */
    @Override
    public String toString(){
        return("Normal Tile Name: " + this.getTileString() + "\n" +
                "Tile Name with Rotation: " + this.getTileNameWithRotation() + "\n" +
                "Tile Rotation: " + this.getRotation() + "\n");
    }
}
