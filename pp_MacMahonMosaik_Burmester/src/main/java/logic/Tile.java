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
    private boolean isLaid;

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
     * Konstruktor welcher einen bestimmten Spielstein durch seinen String initialisiert inklusive Rotation
     * @param tileName der Name des Spielsteins (kann auch rotiert sein)
     */
    public Tile(String tileName){
        for(int i = 0; i < TileNames.values().length; i++){
            TileNames currTileName = TileNames.values()[i];

            for(int r = 0; r < 360; r+=90){
                if(getTileNameWithRotation(currTileName.name(), r).equals(tileName)){
                    this.tile = currTileName;
                    this.rotation = r;
                    return;
                }
            }
        }
    }

    /**
     * Konstruktor welcher einen bestimmten Spielstein durch seinen String initialisiert inklusive Rotation
     * @param tileName der Name des Spielsteins (kann auch rotiert sein)
     */
    public Tile(String tileName, int c){
        //for(int i = 0; i < TileNames.values().length; i++){
        for(TileNames currTileName : TileNames.values()){
            Tile currTile = new Tile(currTileName);

            for(int r = 0; r <= 4; r++){
                if(currTile.getTileNameWithRotation().equals(tileName)){
                    //this = currTile;
                    return;
                } else {
                    currTile.rotateTile();
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
     * Getter welcher zurueckgibt ob der Spielstein gelegt wurde
     * @return ob der Spielstein gelegt wurde
     */
    public boolean getIsLaid(){
        return(this.isLaid);
    }

    /**
     * Methode welche den aktuellen Spielstein rotiert und durch die Methode getTileNameWithRotation
     */
    public String getTileNameWithRotation(){
        return(this.getTileNameWithRotation(this.tile.name(), this.rotation));
    }

    /**
     * Methode welche den Namen des Spielsteins nach der Drehung zurueckgibt
     * @param input der String welcher rotiert werden soll
     * @return der Name des Spielsteins unter Berucksichtigung der Drehung
     */
    public String getTileNameWithRotation(String input, int rotation){
        StringBuilder sb = new StringBuilder(input);

        for(int i = 0; i < rotation; i+=90){ // durchlaeuft 90 Grad schritte
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
     * Setter welcher Setzt dass ein Spielstein gelegt wurde oder nicht
     * @param input ob der Spielstein gelegt wurde
     */
    public void setIsLaid(boolean input){
        this.isLaid = input;
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
     * prueft ob des sich bei dem Spielstein ein Rand kompatiblen Spielstein handelt
     * @return ob RRRR, GGGG oder YYYY
     */
    public boolean isTileBorderLayable(){
        return(this.tile.equals(TileNames.RRRR) || this.tile.equals(TileNames.GGGG) || this.tile.equals(TileNames.YYYY));
    }

    /**
     * Methode welche prueft, ob es sich bei dem Spielstein um einen handelt welcher als Randstueck genutzt werden kann
     * @return ob das Spielstueck als Randstueck genutzt werden kann (GGGG oder RRRR oder YYYY)
     */
    public boolean isTileBorderCompatible(){
        return(this.tile.equals(TileNames.GGGG) || this.tile.equals(TileNames.RRRR) || this.tile.equals(TileNames.YYYY));
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

    /**
     * Methode welche die Rotation des Spielsteins zuruecksetzt
     */
    public void resetTileRotation(){
        this.rotation = 0;
    }

    /**
     * Methode welche die toString Methode fuer die Tile Klasse ueberschreibt
     * @return alle Nutzlasten im String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Normal Tile Name: ").append(this.getTileString()).append("\n");
        sb.append("Tile Name with Rotation: ").append(this.getTileNameWithRotation()).append("\n");
        sb.append("Tile Rotation: ").append(this.getRotation()).append("\n");
        sb.append("Tile isLaid: ").append(this.getIsLaid()).append("\n");
        return(sb.toString());
    }
}
