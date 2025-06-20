package logic;

import java.util.Objects;

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
     * @param rotation die Rotation des Spielsteins
     */
    public Tile(TileNames tile, int rotation){
        this.tile = tile;
        this.rotation = 0;
        for(int i = 0; i < rotation; i++){
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
     * Konstruktor welcher einen Spielstein mit allen Tile Werten initialisiert
     * @param tile der bestimmte Spielstein
     * @param rotation die Rotation des Spielsteins
     * @param isValid ob der Spielstein an seiner Position valide ist
     * @param isLaid ob der Spielstein auf dem Spielfeld liegt
     */
    public Tile(TileNames tile, int rotation, boolean isValid, boolean isLaid){
        this.tile = tile;
        this.rotation = rotation;
        this.isValid = isValid;
        this.isLaid = isLaid;
    }

    /**
     * Methode welche die Instanz eines Spielstein dupliziert
     * @return die Instanz als neue unabhaengige Instanz
     */
    public Tile cloneTile() {
        return(new Tile(this.tile, this.rotation, this.isValid, this.isLaid));
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
        return(Tile.getTileNameWithRotation(this.tile.name(), this.rotation));
    }

    /**
     * Methode welche den Namen des Spielsteins nach der Drehung zurueckgibt
     * @param input der String welcher rotiert werden soll
     * @return der Name des Spielsteins unter Berucksichtigung der Drehung
     */
    public static String getTileNameWithRotation(String input, int rotation){
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
     * prueft ob es sich bei dem Spielstein um einen Rand kompatiblen Spielstein handelt
     * @return ob RRRR, GGGG oder YYYY
     */
    public boolean isTileBorderLayable(){
        return(this.tile.equals(TileNames.RRRR) || this.tile.equals(TileNames.GGGG) || this.tile.equals(TileNames.YYYY));
    }

    /**
     * prueft ob es sich bei dem Spielstein String um einen Rand kompatiblen Spielstein handelt
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
     * Methode welche zurueckgibt, ob ein String einem TileNames gleicht.
     * Beim Rand gibt es die Besonderheit, das das Randstueck in meinem Code 4Buchstaben lang die Farbe hat aber in der
     * Aufgabenstellung die Randstuecke nur 1 Stelle im String mit einer Farbe (G oder R oder Y) und 3 Stellen mit N
     * @param tileString der String von welchem geprueft werden soll, ob es sich um einen Tile Spielstein handelt
     * @param isBorder ob es sich bei dem geprueften String welcher ein Spielstein sein soll als Randstueck liegts
     */
    public void isStringTileValid(String tileString, boolean isBorder){
        boolean isValid = false;
        for(TileNames currTile : TileNames.values()){
            if(currTile.toString().equals(tileString) ||
                    (isBorder && (tileString.contains("G") || tileString.contains("Y") || tileString.contains("R")))){ //TODO pruefen das die Position stimmts
                isValid = true;
            }
        }
    }

    /**
     * Methode welche aus einem String den dazugehoerigen TileNames zurueckgibt
     * Hierfuer werden alle TileNames namen durchlaufen und jeder Name in jeder Rotation getestet.
     * @param tileName der Name des Spielsteins (kann auch rotiert sein)
     * @return der unrotierte standard Name des Spielsteins
     */
    public static String getTileNamesString(String tileName){
        for(int i = 0; i < TileNames.values().length; i++){
            TileNames currTileName = TileNames.values()[i];

            for(int r = 0; r < 360; r+=90){
                if(getTileNameWithRotation(currTileName.name(), r).equals(tileName)){
                    System.out.println("EQUALS: " + currTileName.name());
                    return(currTileName.name());
                }
            }
        }
        return(null);
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
