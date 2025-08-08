package logic;

import gui.ErrorMessageHandler;

/**
 * Klasse welche das Spielfeld als Zweidimensionales Array enthaelt
 *
 * @author Anton Burmester
 */

public class GameField {
    private Tile[][] gameField; //Nutzlast des Spielfelds als zweidimensionales Array vom Typ Tile (Spielstein)
    private final Tiles tiles; //Nutzlast der Spielstein Instanz

    /**
     * Konstruktor welcher ein Spielfeld ohne Steine initialisiert
     * @param height die Hoehe des neuen Spielfeldes
     * @param width die Breite des neuen Spielfeldes
     * @param placeHoles ob die Loecher automatisch im Spielfeld gesetzt werden sollen (nur false bei copy, da hier in
     *                   der Kopie schon die Standorte der Loecher vorhanden sind)
     */
    public GameField(int height, int width, boolean placeHoles){
        this.gameField = new Tile[height + 2][width + 2]; //Hoehe+2 und Breite+2 wegen der Raender
        this.tiles = new Tiles();
        this.placeGameFieldEmpty();
        if(placeHoles)
            this.placeGameFieldHoles();
    }

    /**
     * Konstruktor welcher ein bestehendes Spielfeld initialisiert
     * @param stringGameField eingelesenes Spielfeld
     */
    public GameField(String[][] stringGameField){
        int height = stringGameField.length;
        int width = stringGameField[0].length;

        this.tiles = new Tiles();

        String[][] inputCompatible = this.translateFromSpielstandsdatei(stringGameField); //der Input aber Logik Kompatibel
        this.gameField = new Tile[height][width]; //Erste Dimension Hoehe, Zweite Dimension Breite
        this.placeGameFieldEmpty(); //Spielfeld mit leeren feldern fuellen
        if(this.isInputStringGameFieldValid(stringGameField)) {

            for (int heigthIndex = 0; heigthIndex < height; heigthIndex++) { //durchlaeuft jede Hoehe des Felds
                for (int widthIndex = 0; widthIndex < width; widthIndex++) { //durchlaeuft jede Breite des Felds

                    String laidTileName = inputCompatible[heigthIndex][widthIndex];
                    Tile targetTile;
                    if (laidTileName.equals(TileNames.HHHH.name())) { //ein Loch gelegt
                        targetTile = new Tile(TileNames.HHHH);

                    } else if (laidTileName.equals(TileNames.NNNN.name())) { //nichts gelegt
                        targetTile = new Tile(TileNames.NNNN);

                    } else { //ein Spielstein gelegt

                        if (!this.isFieldBorder(widthIndex, heigthIndex)) { //wenn es sich um ein
                            // Spielfeldstueck handelt
                            targetTile = this.tiles.getTileByNameWithRotation(laidTileName);
                        } else { //wenn es sich um ein Randstueck handelt
                            targetTile = new Tile(laidTileName);
                        }
                    }
                    this.layTile(widthIndex, heigthIndex, targetTile); //Stein legen auf das Spielfeld
                    //wenn mittleres Spielfeld und nicht NNNN
                    if(!this.isFieldBorder(widthIndex, heigthIndex) && !targetTile.getTile().equals(TileNames.NNNN)){

                        this.tiles.removeTile(targetTile); //Spielstein aus Spielsteinauswahl loeschen, da dieser
                        // hiervor mit layTile() ins Spielfeld gelegt wird
                    }

                }
            }
        }
    }

    /**
     * Methode welche die Tiles Instanz zurueckgibt
     * @return die Tiles Instanz
     */
    Tiles getTiles(){
        return(this.tiles);
    }

    /**
     * Methode welche die Raender dieses Spielfeld nach dem Schema des uebergebenen einfaerbt
     * @param oldField das alte Spielfeld nach welchem das neue eingefaerbt wird
     */
    void setBorderFromGameField(GameField oldField){

        //alten Rand setzen links und oben
        int smallerWidth = Math.min(this.getGameFieldWidth() - 1, oldField.getGameFieldWidth()); //-1 damit die Ecke
        // rechte obere Ecke im neuen Spielfeld frei bleibt
        int smallerHeight = Math.min(this.getGameFieldHeight() - 1, oldField.getGameFieldHeight()); //-1 damit die Ecke
        // linke untere Ecke im neuen Spielfeld frei bleibt
        for(int y = 0; y < smallerHeight; y++){
            for(int x = 0; x < smallerWidth; x++){
                if(this.isFieldBorder(x, y)){
                    this.layTile(x, y, oldField.getTile(x, y));
                }
            }
        }

        //alten Rand setzen rechts
        for(int y = 1; y < smallerHeight; y++){ //bei 1 Starten da bei 0 immer NNNN liegt was schon im Spielfeld liegt
            Tile oldTile = oldField.getTile(oldField.getGameFieldWidth() - 1, y);
            this.layTile(this.getGameFieldWidth() - 1, y, oldTile);
        }

        //alten Rand setzen unten
        for(int x = 1; x < smallerWidth; x++){ //bei 1 Starten da bei 0 immer NNNN liegt was schon im Spielfeld liegt
            Tile oldTile = oldField.getTile(x, oldField.getGameFieldHeight() - 1);
            this.layTile(x, this.getGameFieldHeight() - 1, oldTile);
        }
    }

    /**
     * Methode welche prueft, das das uebergebene Spielfeld vom Typ String valide ist:
     * Rand ist nur mit Randsteinen oder NNNN gefuellt
     * Mittleres Spielfeld ist nur mit Spielfeldsteinen gefuellt die Maximal 1x vorkommen
     * Mittleres Spielfeld weist die richtige Anzahl an Lochsteinen auf
     * @param stringGameField das Spielfeld
     * @return ob das Spielfeld valide ist
     */
    private boolean isInputStringGameFieldValid(String[][] stringGameField){
        String[][] inputCompatible = this.translateFromSpielstandsdatei(stringGameField); //der Input Logik Kompatibel
        int[] tileCountArray = new int[Game.TILE_AMOUNT_COMPLETE];
        int height = inputCompatible.length;
        int width = inputCompatible[0].length;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                String tileName = Tile.getTileNamesString(inputCompatible[y][x]);
                if(tileName == null){ //Spielstein konnte nicht gefunden werden (falsch)
                    ErrorMessageHandler.showError(new CustomException(CustomException.ERROR_INVALID_TILENAMES));
                    return(false);
                } else {
                    if(GameField.isFieldBorder(x, y, width, height)){ //Rand Position
                        if(!Tile.isTileStringBorderLayable(tileName)) { //kein Randkompatibler Stein oder leer
                            ErrorMessageHandler.showError(new CustomException(
                                    CustomException.ERROR_INVALID_TILENAMES_BORDER));
                            return(false);
                        }
                    } else if(GameField.isFieldEdge(x, y, width, height)){ //Ecken
                        if(!Tile.isTileStringEdgeLayable(tileName)) { //kein Randkompatibler Stein
                            ErrorMessageHandler.showError(new CustomException(
                                    CustomException.ERROR_INVALID_TILENAMES_EDGE));
                            return(false);
                        }
                    } else {
                        tileCountArray[TileNames.valueOf(tileName).ordinal()]++; // Element an der Stelle +1 zaehlen
                    }
                }
            }
        }
        //die gezaehlte Anzahl ueberpruefen
        for(int i = 0; i < tileCountArray.length; i++){
            if(i < Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY){ //Spielsteine ohne Loecher und Nichts gelegt auf Anzahl pruefen
                // 0-1
                if(tileCountArray[i] > 1){ //mindestens ein mittlerer Spielfeld Stein liegt mehr als einmal
                    ErrorMessageHandler.showError(new CustomException(
                            CustomException.ERROR_MIDDLEGAMEFIELD_TILE_TOO_OFTEN));
                    return(false);
                }
            } else if(i == TileNames.HHHH.ordinal()){ //Loecher auf Anzahl pruefen
                if(tileCountArray[i] != GameField.calcNeededHoles(width, height)){ //falsche Anzahl an Loechern
                    ErrorMessageHandler.showError(new CustomException(
                            CustomException.ERROR_MIDDLEGAMEFIELD_HOLE));
                    return(false);
                }
            } //Leer (NNNN) muss nicht geprueft werden
        }
        return(true);
    }

    /**
     * Methode welche die Breite des Spielfelds zurueckgibt
     * @return die Breite des Spielfelds
     */
    public int getGameFieldWidth(){
        return(this.gameField[0].length);
    }

    /**
     * Methode welche die Hoehe des Spielfelds zurueckgibt
     * @return die Breite des Spielfelds
     */
    public int getGameFieldHeight(){
        return(this.gameField.length);
    }

    /**
     * Methode welche das Spielfeld mit nicht platzierten Feldern (NNNN) fuellt und den Rand mit Rot gefaerbten
     * Spielsteinen, damit der Rand schonmal gefaerbt ist
     * //TODO MAYBE wieder nur NNNN
     */
    private void placeGameFieldEmpty(){
        Tile tile;
        for(int y = 0; y < this.gameField.length; y++){
            for(int x = 0; x < this.gameField[y].length; x++){
                if(this.isFieldBorder(x, y) && ! this.isFieldEdge(x, y)){ //Rand aber nicht Ecke
                    tile = new Tile(TileNames.RRRR);
                } else { //mittleres Spielfeld und Ecke
                    tile = new Tile(TileNames.NNNN);
                }
                this.gameField[y][x] = tile;
            }
        }
    }

    /**
     * Methode welche prueft, ob der uebergebene Spielstein gelegt ist also im Spielfeld vorkommt.
     * @param tile der gepruefte Spielstein
     * @return ob der Spielstein vorkommt
     */
    public boolean isTileLaid(Tile tile){
        for(int y = 1; y < this.gameField.length - 1; y++) { //jede Reihe des mittleren Spielfelds durchlaufen
            for (int x = 1; x < this.gameField[y].length - 1; x++) { //jede Spalte des mittleren Spielfelds durchlaufen
                if(this.getTile(x, y).equals(tile)) return(true); //wenn Spielstein vorkommt
            }
        }
        return(false); //Spielstein ist nicht im Spielfeld -> nicht gelegt
    }

    /**
     * Methode welche das Spielfeld mit der benoetigten Anzahl an Loechern automatisch fuellt.
     * Angefangen oben links
     */
    private void placeGameFieldHoles(){
        int holesCounter = 0;
        int neededHoles = GameField.calcNeededHoles(this.gameField[0].length, this.gameField.length);
        for(int y = 0; y < this.gameField.length; y++){
            for(int x = 0; x < this.gameField[y].length; x++){
                if(!this.isFieldBorder(x, y)) { //mittleres Spielfeld
                    if (holesCounter < neededHoles) {
                        this.gameField[y][x] = new Tile(TileNames.HHHH);
                        holesCounter++;
                    }
                }
            }
        }
    }

    /**
     * Methode welche die benoetigte Anzahl an Loechern berechnet
     * ((Spielfelder ohne Rand) - (Anzahl Spielsteine ohne Loch und ohne Leer)
     * @param xSize die Spielfeldbreite
     * @param ySize die Spielfeldhoehe
     * @return die Anzahl der benoetigten Felder (min 0)
     */
    public static int calcNeededHoles(int xSize, int ySize){
        int neededHoles = (xSize - 2) * (ySize - 2) - (TileNames.values().length - 2); //-2 da der Rand nicht mitzaehlt
        // und -2 bei TileNames da NNNN und HHHH nicht mitzaehlen
        return(Math.max(neededHoles, 0));
    }

    /**
     * Methode welche die geforderten Spielstands Eingabe eines Spielfelds mit meiner Logik Kompatibel machen.
     * Hierbei geht es um die Raender da ich fuer die Raender RRRR, GGGG und YYYY nutze.
     * In den uebergebenen Spielstandsdateien wird stattdessen das Randstueck so gehandhabt als wuerde es ganz liegen
     * und deshalb ist nur die ans Spielfeld grenzende seite gefaerbt und der Rest nicht (N).
     * @param input das uebergebene String Array der Spielstandsdatei
     * @return das uebergebene String Array aber mit meiner Logik Kompatibel bezugelich Rand
     */
    private String[][] translateFromSpielstandsdatei(String[][] input){
        int height = input.length;
        int width = input[0].length;
        String[][] inputCopy = new String[input.length][input[0].length];

        //Deepcopy der Input Stringdatei
        for (int i = 0; i < height; i++) {
            System.arraycopy(input[i], 0, inputCopy[i], 0, width);
        }

        for (int y = 0; y < inputCopy.length; y++) {  //jedes Element in die Hoehe durchlaufen
            for (int x = 0; x < inputCopy[y].length; x++) {  //jedes Element in die Breite durchlaufen
                String currentTile = inputCopy[y][x]; //das aktuelle Element
                if(currentTile.contains("N")){ //wenn das aktuelle Element "N" enthaelt
                    if(currentTile.contains("R")){ //und "R"
                        inputCopy[y][x] = "RRRR"; //das Element ersetzen
                    } else if(currentTile.contains("G")){ //und "G"
                        inputCopy[y][x] = "GGGG"; //das Element ersetzen
                    } else if(currentTile.contains("Y")){ // und "Y"
                        inputCopy[y][x] = "YYYY"; //das Element ersetzen
                    }
                }
            }
        }
        return(inputCopy);
    }

    /**
     * Methode welche das aktuelle Spielfeld als String Array mit den vorgegebenen Randbezeichnungen zurueckgibt
     * In meiner gebe ich dem Rand vollstaendige Farben: rrrr, gggg, yyyy
     * Laut Aufgabenstellung soll es aber so sein: der Farbhinweis am Rand liegt an der Stelle welche an das
     * mittlere Spielfeld grenzt
     * @return das Spielfeld als String Array nach Aufgabenstellungsform
     */
    public String[][] translateToSpielstandsdatei(){
        String[][] gameFieldSpielstandsdatei = new String[this.getGameField().length][this.getGameField()[0].length];

        for(int y = 0; y < this.getGameField().length; y++){
            for(int x = 0; x < this.getGameField()[y].length; x++){
                String currentTile = this.getTile(x, y).getTileNameWithRotation();
                if(!this.isFieldBorder(x, y) || this.isFieldEdge(x, y)) {
                    gameFieldSpielstandsdatei[y][x] = currentTile;
                } else { //Spielstein liegt am Rand
                    char color = currentTile.charAt(0); //da in meiner Implementierung ein Randstueck immer
                    // voll alle Farben hat, kann man repraesentativ das erste nehmen um die Farbe zu bekommen
                    if(x == 0){ //linker Rand
                        currentTile = "N" + color + "NN";
                    } else if(y == 0){ //oberer Rand
                        currentTile = "NN" + color + "N";
                    } else if(x == this.getGameField()[y].length - 1){ //rechter Rand
                        currentTile = "NNN" + color;
                    } else { //unterer Rand
                        currentTile = color + "NNN";
                    }
                    gameFieldSpielstandsdatei[y][x] = currentTile;
                }
            }
        }

        return(gameFieldSpielstandsdatei);
    }

    /**
     * oeffentlicher Getter um das Private gameFiled an außerhalb dieser Klasse geben zu koennen
     * @return das Spielfeld
     */
    public Tile[][] getGameField() {
        return this.gameField;
    }

    /**
     * Methode welche die Klasse eines Spielsteins zurueckgibt
     * @param x die Breitenkoordinate des Spielsteins
     * @param y die Hoehenkoordinate des Spielsteins
     * @return die Instanz des Spielsteins
     */
    public Tile getTile(int x, int y){
        return(this.gameField[y][x]);
    }

    /**
     * beim mittleren Spielfeld: prueft ob das Feld schon mit einem Spielstein oder Loch belegt ist
     * beim Rand: immer true weil beim Rand einfach die neuste Farbe zaehlt und es egal ist ob vorher eine Farbe da war
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @return ob das Feld Frei ist
     */
    public boolean isFieldFieldFree(int xIndex, int yIndex){
        boolean status = false;
        if(this.isFieldMiddleGamefield(xIndex, yIndex)) { //wenn das gewaehlte Feld Valide ist
            status = this.gameField[yIndex][xIndex].getTile().equals(TileNames.NNNN); //ob das Feld leer ist
        } else if(this.isFieldBorder(xIndex, yIndex)) { //wenn das gewaehlte Feld ein Randstueck ist
            status = true;
        }
        return(status);
    }

    /**
     * mittleren Spielfeld: legt ein Mosaikstein sofern das Feld kein Rand ist und leer ist
     * Rand Spielfeld: legt ein Mosaikstein sofern das Feld Rand ist und der Spielstein einfarbig ist
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param tile Spielstein
     * @return ob der Spielstein wieder zurueckgelegt werden konnte
     */
    public boolean layTile(int xIndex, int yIndex, Tile tile){
        boolean status = false;
        if(this.isFieldMiddleGamefield(xIndex, yIndex)){ //mittleres Feld des Spielfelds
            if(this.isFieldFieldFree(xIndex, yIndex)){ //ob das Feld frei ist
                this.gameField[yIndex][xIndex] = tile; //Spielstein legen
                this.tiles.removeTile(tile); //Stein aus Spielsteinauswahl loeschen, da gelegt
                status = true;
            }
        } else if(this.isFieldBorder(xIndex, yIndex)){ //Rand Feld des Spielfelds
            if(tile.isTileBorderLayable()){ //ob der uebergebene Spielstein gueltig fuer den Rand ist
                this.gameField[yIndex][xIndex] = tile;
                //Stein aus Spielsteinauswahl nicht loeschen, da dieser kein Unikat ist und die Refferenz egal ist
                status = true;
            }
        }
        return(status);
    }

    /**
     * Methode welche ein Spielfeld Feld zuruecksetzt und den Status des Spielsteins des zurueckgesetzten Feldes auf
     * not Laid gesetzt wird
     *
     * @param xIndex der Spaltenindex des Feldes
     * @param yIndex der Zeilenindex des Feldes
     */
    public void resetTile(int xIndex, int yIndex){
        if(this.isFieldGamefield(xIndex, yIndex)){
            Tile tile = this.getTile(xIndex, yIndex);
            this.gameField[yIndex][xIndex] = new Tile(TileNames.NNNN);
            this.tiles.addTile(tile); //Stein der Spielsteinauswahl hinzufuegen, da nicht mehr im Spielfeld gelegt
        }
    }

    /**
     * Methode welche prueft ob es sich bei dem Feld um ein auf dem Spielfeld liegendes handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich das Feld auf dem Spielfeld befindet
     */
    public boolean isFieldGamefield(int xIndex, int yIndex){
        return(xIndex >= 0 && xIndex < this.gameField[0].length //Breitenindex in der Abmessung des Spielfelds
                && yIndex >= 0 && yIndex < this.gameField.length); //Hoehenindex in der Abmessung des Spielfelds
    }

    /**
     * Methode welche prueft ob es sich bei dem Feld um ein mittleres Spielfeld handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein mittleres Spielfeldstueck handelt
     */
    public boolean isFieldMiddleGamefield(int xIndex, int yIndex){
        return(xIndex >= 1 && yIndex >= 1 //Breitenindex in der Abmessung des inneren Spielfelds (ohne Rand)
                && xIndex < this.gameField[0].length - 1 && yIndex < this.gameField.length - 1); //Hoehenindex in der
        // Abmessung des inneren Spielfelds (ohne Rand)
    }

    /**
     * Statische Methode welche prueft ob es sich bei dem Feld um ein Randstueck handelt bei der Spielfeldgroeße
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @param xSize die Laenge des Spielfelds
     * @param ySize die Breite des Spielfelds
     * @return ob es sich um ein Randstueck handelt
     */
    public static boolean isFieldBorder(int xIndex, int yIndex, int xSize, int ySize){
        return(xIndex == 0 || xIndex == xSize - 1
                || yIndex == 0 || yIndex == ySize - 1);
    }

    /**
     * Methode welche prueft ob es sich bei dem Feld um ein Randstueck handelt
     * Nutzt hierfuer die Statische isFieldBorder Methode
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Randstueck handelt
     */
    public boolean isFieldBorder(int xIndex, int yIndex){
        return(GameField.isFieldBorder(xIndex, yIndex, this.gameField[0].length, this.gameField.length));
    }

    /**
     * Methdoe welche prueft ob es sich bei dem Feld um ein Eckstueck handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Eckstueck handelt
     */
    public static boolean isFieldEdge(int xIndex, int yIndex, int xSize, int ySize){
        return(((xIndex == 0 && yIndex == 0) //linke obere Ecke
                || (xIndex == 0 && yIndex == ySize - 1) //linke untere Ecke
                || (xIndex == xSize - 1 && yIndex == 0) //rechte obere Ecke
                || (xIndex == xSize - 1 && yIndex == ySize - 1))); //rechte untere Ecke
    }

    /**
     * Methdoe welche prueft ob es sich bei dem Feld um ein Eckstueck handelt
     * Nutzt hierfuer die Statische isFieldEdge Methode
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Eckstueck handelt
     */
    public boolean isFieldEdge(int xIndex, int yIndex){
        return(GameField.isFieldEdge(xIndex, yIndex, this.gameField[0].length, this.gameField.length));
    }

    /**
     * Methode welche prueft ob der Rand des Spielfelds komplett konfiguriert wurde
     * @return ob der Rand des Spielfelds komplett ist
     */
    public boolean isGameFieldBorderSetted(){
        boolean status = true;
        for (int y = 0; y < this.gameField.length; y++) { //Zeilen des Spielfelds durchlaufen
            for (int x = 0; x < this.gameField[y].length; x++) { //Spalten des Spielfelds durchlaufen
                if (this.isFieldBorder(x, y) && !this.isFieldEdge(x, y)) { //ob das Feld ein Rand Feld ist
                    if (!this.getTile(x, y).isTileBorderLayable()) { //wenn es sich beim Spielstein der auf dem Rand
                        // Feld liegt nicht um ein Randstueck handelt (also ein NNNN)
                        status = false;
                    }
                }
            }
        }
        return (status);
    }

    /**
     * Methode welche prueft ob ein bestimmtes Feld farblich valide
     * Spielstein: ist valide wenn alle Seiten passen
     * Loch (HHHH): ist immer valide
     * Nichts gelegt (NNNN): nie valide
     * @param x der Breitenindex des zu ueberpruefenden Spielsteins
     * @param y der Hoehenindex des zu ueberpruefenden Spielsteins
     * @param acceptN ob N also nicht definiert als RandPartner zaehlt oder nicht
     * @return ob das bestimmte Feld valide ist
     */
    public boolean isGameFieldTileMatching(int x, int y, boolean acceptN){
        boolean status = true;
        String tileNameWithRotation = this.getTile(x, y).getTileNameWithRotation(); //der Stein der ueberprueft wird
        char sourceTileRelevantBorderLetter; //der jeweilige Buchstabe der Seite mit der Farbe des ueberprueften Stein
        String comparedTileNameWithRotation; //der jeweilige Stein rundherum
        char comparedTileRelevantBorderLetter; //der jeweilige Buchstabe der Seite mit der Farbe des ueberprueften Stein
        if(this.isFieldGamefield(x, y) && this.isFieldMiddleGamefield(x, y)){
            //nach oben
            comparedTileNameWithRotation = this.getTile(x, y - 1).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(2);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(0);
            if(this.isTwoFieldsOfTileNotMatching(sourceTileRelevantBorderLetter, comparedTileRelevantBorderLetter,
                    acceptN))
                status = false; //beide Raender passen nicht


            //nach rechts
            comparedTileNameWithRotation = this.getTile(x + 1, y).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(3);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(1);
            if(this.isTwoFieldsOfTileNotMatching(sourceTileRelevantBorderLetter, comparedTileRelevantBorderLetter,
                    acceptN))
                status = false; //beide Raender passen nicht


            //nach unten
            comparedTileNameWithRotation = this.getTile(x, y + 1).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(0);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(2);
            if(this.isTwoFieldsOfTileNotMatching(sourceTileRelevantBorderLetter, comparedTileRelevantBorderLetter,
                    acceptN)) //beide Raender passen nicht
                status = false;


            //nach links
            comparedTileNameWithRotation = this.getTile(x - 1, y).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(1);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(3);
            if(this.isTwoFieldsOfTileNotMatching(sourceTileRelevantBorderLetter, comparedTileRelevantBorderLetter,
                    acceptN)) //beide Raender passen nicht
                status = false;

        }
        return(status);
    }

    /**
     * ob zwei Raender nicht aneinanderpassen
     * @param sourceTileBorderLetter die Randfarbe des einen Spielsteins
     * @param comparedTileBorderLetter die Randfarbe des anderen Spielsteins (Nachbar)
     * @param acceptN ob falls N also nichts gelegt an diesen Spielstein grenzt, dies akzeptiert wird
     * @return ob die beiden Felder passen
     */
    private boolean isTwoFieldsOfTileNotMatching(char sourceTileBorderLetter, char comparedTileBorderLetter,
                                                 boolean acceptN){
        boolean bothColorsSame = sourceTileBorderLetter == comparedTileBorderLetter; //ob die Farben gleich sind
        boolean bothColorsN = sourceTileBorderLetter == 'N' && comparedTileBorderLetter == 'N'; //ob beide Farben N
        boolean minOneN = sourceTileBorderLetter == 'N' || comparedTileBorderLetter == 'N'; //ob minimum eine Farbe N
        boolean minOneHole = comparedTileBorderLetter == 'H' || sourceTileBorderLetter == 'H'; //ob minimun ein Loch


        return ((!bothColorsSame || (bothColorsN && !acceptN)) && !minOneHole && (!minOneN || !acceptN));
    }

    /**
     * Methode welche prueft ob ein Spielfeld ganz geloest wurde bzw. richtig ist.
     * @param acceptN damit geprueft werden kann ob die bisherigen Steine korrekt liegen
     * @return ob das Spielfeld ganz korrekt fertig ist oder bisher korrekt fertig ist abgesehen von nichts gelegten
     * Felder
     */
    public boolean checkIfGameFieldSolved(boolean acceptN){
        boolean status = true;
        for(int y = 1; y < this.gameField.length - 1; y++){
            for(int x = 1; x < this.gameField[y].length - 1; x++){
                if(!this.isGameFieldTileMatching(x, y, acceptN)){
                    status = false;
                }
            }
        }
        return(status);
    }

    /**
     * Methode welche eine Kopie des Spielfelds erstellt
     * @return die Kopie des Spielfelds
     */
    public GameField cloneGameField(){
        int width = this.gameField[0].length;
        int heigth = this.gameField.length;
        GameField copy = new GameField(heigth - 2, width - 2, false); //neue Instanz eines neuen Spielfelds
        // -2 da beim Spielfeld die groesse ohne Rand angegeben wird

        Tile currNotCopyTile;
        Tile copyTile;
        for(int y = 0; y < heigth; y++){ //Hoehenindex
            for(int x = 0; x < width; x++){ //Breitenindex
                currNotCopyTile = this.getTile(x, y); //der aktuelle Stein welcher in das neue Spielfeld kopiert werden
                // soll

                //aktuellen Stein klonen
                copyTile = currNotCopyTile.cloneTile();
                if(copyTile != null) {
                    copy.layTile(x, y, copyTile); //den Stein in das neue Spielfeld legen
                }
            }
        }
        return (copy);
    }

    /**
     * Methode welche versucht das Spielfeld zu loesen.
     * Hierfuer wird gesucht bis ein neuer Stein gefunden wurde, der auf ein leeres Feld passt.
     * Wenn an einen Punkt gekommen wird wo es keine weiteren Steine mehr gibt wird durch Backtracking zurueckgegangen
     * und eine andere Kombination versucht
     * Das geht solange bis das Spielfeld loesbar ist oder jede Kombination versucht wurde
     * @return null falls das Spielfeld nicht geloest wurde, sonst die Kopie des Spielfelds welche geloest ist
     */
    GameField solveGameFieldAsCopy(){
        GameField gameField = this;

        Tiles clonedTiles = this.tiles.cloneGameTiles();
        GameField clonedGameField = gameField.cloneGameField();

        if(gameField.checkIfGameFieldSolved(false)) return(clonedGameField); //Spielfeld schon geloest

        if(!gameField.checkIfGameFieldSolved(true)) return(null); //nicht loesbar bezugelich der schon liegenden
        // Spielsteine bei Aufruf dieser Methode

        Position pos = new Position(0, 0); //die aktuelle Position in einer Klasse, damit diese als Refferenz
        // uebergeben werden kann

        this.resetAllNotLaidTileRotation(clonedTiles); //initial die Rotation aller nicht gelegten Spielsteine
        // zuruecksetzen damit jede Rotation versucht wird und keine uebersprungen wird,
        // da findNextMatchingTile bei der aktuellen Rotation des Spielsteins beginnt

        Tile lastTileBeforeGoingBack = null; //speichert immer wenn einen Schritt zurueckgegangen wurde den Spielstein
        // damit beim naechsten Durchlauf dieser nicht nochmal versucht wird (Backtracking)

        boolean searchActive = true;
        while(searchActive){ //solange die Suche noch anläuft und kein Spielstein gefunden wurde
            boolean nextFieldThere;
            if(lastTileBeforeGoingBack == null) { //es gab keine Schritt zurueck (kein Backtracking)
                nextFieldThere = this.goToNextFreeMiddleField(gameField, pos); //das naechste freie Feld suchen
            } else { //es gab einen Schritt zurueck (Backtracking)
                nextFieldThere = false;
            }

            if(nextFieldThere || lastTileBeforeGoingBack != null){ //es gibt ein naechstes Spielfeld
                Tile nextMatchingTile = this.findNextMatchingTile(clonedGameField, clonedTiles, pos,
                        lastTileBeforeGoingBack);
                if(nextMatchingTile != null){ //es gibt einen passenden Spielstein

                    if(!clonedGameField.getTile(pos.getX(), pos.getY()).isPlaceHolderTile()) { //kein Platzhalter
                        // sondern richtiger einmaliger Spielstein
                        clonedTiles.prependTile(clonedGameField.getTile(pos.getX(), pos.getY())); //Spielstein wieder
                        // zuruecklegen in Spielsteinauswahl da hiernach dieser aus dem Spielfeld geloescht wird
                    }
                    clonedGameField.resetTile(pos.getX(), pos.getY());

                    clonedGameField.layTile(pos.getX(), pos.getY(), nextMatchingTile); //Spielstein legen
                    clonedTiles.removeTile(nextMatchingTile);//Spielstein aus Spielsteinauswahl loeschen, da dieser
                    // hiervor auf das Spielfeld gelegt wird

                    lastTileBeforeGoingBack = null; //da naechster Spielstein gefunden wurde
                } else { //es gibt keinen passenden Spielstein
                    boolean previousFieldThere = this.goToPreviousFreeMiddleField(gameField, pos); //das vorherige
                    // freie Feld suchen
                    if(previousFieldThere) { //es gibt ein vorheriges freies Feld, deshalb dieses zurueckgehen

                        lastTileBeforeGoingBack = clonedGameField.getTile(pos.getX(), pos.getY()); //fuer Backtracking

                        if(!clonedGameField.getTile(pos.getX(), pos.getY()).isPlaceHolderTile()) { //kein Platzhalt
                            // sondern richtiger einmaliger Spielstein
                            clonedTiles.prependTile(clonedGameField.getTile(pos.getX(), pos.getY())); //Spielstein
                            // wieder zuruecklegen in Spielsteinauswahl da hiernach dieser aus dem Spielfeld geloescht
                            // wird
                        }
                        clonedGameField.resetTile(pos.getX(), pos.getY()); //Spielfeld zuruecksetzen indem der
                        // zuletzt gelegte Stein geloescht wird

                    } else { //es gibt kein weiteres vorheriges Spielfeld
                        searchActive = false; //Spielfeld nicht loesbar
                    }
                }
            } else { //kein weiteres Feld gefunden
                searchActive = false; //Spielfeld sollte geloest sein
            }
        }
        System.out.println("Solved as far GameField: \n" + clonedGameField);

        return(clonedGameField.checkIfGameFieldSolved(false) ? clonedGameField : null); //wenn das Spielfeld
        // geloest wurde das geloeste Spielfeld, sonst null
    }

    /**
     * Methode welche die Rotation aller nicht gelegten Spielsteine zuruecksetzt
     * @param tiles alle nicht gelegten Spielsteine
     */
    private void resetAllNotLaidTileRotation(Tiles tiles){
        for(Tile currTile : tiles.getTiles()){
            System.out.println(currTile.toString());
            currTile.resetTileRotation();
        }
    }


    /**
     * Methode welche das naechste freie mittlere Spielfeld sucht
     * @param inputGameField das Spielfeld
     * @param pos die aktuelle Position als Refferenz, damit die x und y Werte auch wieder aus dieser Methode rauskommen
     * @return ob es ein naechstes Feld gibt
     */
    boolean goToNextFreeMiddleField(GameField inputGameField, Position pos){
        int xStart; //initial x auf den Wert der aktuellen Position setzen
        int yStart;
        if(pos.getX() < inputGameField.getGameFieldWidth() - 2){ //- 2 damit man 1. Spalte vorgehen kann
            // (letzte Spalte Spielfeld, Rand)
            xStart = pos.getX() + 1; //1. Spalte vor damit nicht dasselbe Feld zurueckgegeben wird
            yStart = pos.getY() == 0 ? 1 : pos.getY();
        } else if(pos.getY() < inputGameField.getGameFieldHeight() - 2){ //- 2 damit man 1. Zeile vorgehen kann
            // (letztes Spielfeld, darunter Rand)
            xStart = 1;
            yStart = pos.getY() + 1; //1. Zeile zurueck damit nicht dasselbe Feld zurueckgegeben wird
        } else { //man kann kein Feld mehr vorgehen
            return(false);
        }
        for(int y = yStart; y < inputGameField.getGameFieldHeight() - 1; y++){
            for(int x = xStart; x < inputGameField.getGameFieldWidth() - 1; x++){
                if((inputGameField.isFieldFieldFree(x, y) || inputGameField.getTile(x, y).isPlaceHolderTile())
                        && inputGameField.isFieldMiddleGamefield(x, y)){
                    pos.setX(x);
                    pos.setY(y);
                    return(true);
                }
            }
            xStart = 1; //1 da 0 der linke Rand ist
        }
        return(false); //kein Feld gefunden
    }

    /**
     * Methode welche das naechste vorherige freie mittlere Spielfeld sucht
     * @param inputGameField das Spielfeld
     * @param pos die aktuelle Position als Refferenz, damit die x und y Werte auch wieder aus dieser Methode rauskommen
     * @return ob es ein naechstes Feld gibt
     */
    private boolean goToPreviousFreeMiddleField(GameField inputGameField, Position pos){
        int xStart;
        int yStart;
        if(pos.getX() >= 2){ //>= 2 damit man 1. Spalte zurueckgehen kann (Rand, erstes Spielfeld)
            xStart = pos.getX() - 1; //1. Spalte zurueck damit nicht dasselbe Feld zurueckgegeben wird
            //damit nicht die unterste Randspalte
            yStart = pos.getY() == inputGameField.getGameFieldHeight() - 1 ? pos.getY() - 1: pos.getY();
        } else if(pos.getY() >= 2){ //>= 2 damit man 1. Zeile zurueckgehen kann (Rand, darunter erstes Spielfeld)
            xStart = inputGameField.getGameFieldWidth() - 2; //die auesserte Spalte von inneren Spielfeld
            yStart = pos.getY() - 1; //1. Zeile zurueck damit nicht dasselbe Feld zurueckgegeben wird
        } else { //man kann kein Feld mehr zurueckgehen
            return(false);
        }

        for(int y = yStart; y >= 1; y--){
            for(int x = xStart; x >= 1; x--){
                if((inputGameField.isFieldFieldFree(x, y) || inputGameField.getTile(x, y).isPlaceHolderTile()) &&
                        inputGameField.isFieldMiddleGamefield(x, y)){
                    pos.setX(x);
                    pos.setY(y);
                    return(true);
                }
            }
            xStart = inputGameField.getGameFieldWidth() - 2; //wieder ganze Zeile, -2 da sonst Rand und 0 Indexiert
        }
        return(false); //es wurde kein freies Feld gefunden
    }

    /**
     * Methode welche fuer ein Spielfeld an einer bestimmten Position das passende Spielteil sucht.
     * @param inputGameField das Spielfeld
     * @param inputTiles die Spielstein Klasse
     * @param pos die aktuelle Position als Refferenz
     * @param lastTileBeforeGoingBack der Spielstein des Felds von dem es nicht weiter geht und deshalb Backtracking
     *  genutzt wird, der Stein wird uebergeben, damit diese Methode weiß von wo sie nach neuen Spielsteinen suchen
     *  muss; null falls es kein Aufruf nach Backtracking war sondern ein ganz normaler
     * @return der gefundene Spielstein oder null falls keiner gefunden wurde
     */
    private Tile findNextMatchingTile(GameField inputGameField, Tiles inputTiles, Position pos,
                                      Tile lastTileBeforeGoingBack){
        Tile tile = lastTileBeforeGoingBack;
        int startIndex = tile != null  && !tile.getTile().equals(TileNames.NNNN) ?
                TileNames.valueOf(tile.getTileString()).ordinal(): 0; //der Index zum Start des aktuell dort liegenden
        // Spielsteins nach der TileNames Reihenfolge oder 0 falls es ein Aufruf fuer ein leeres Feld ist

        Rotation startRotation = tile != null && !tile.getTile().equals(TileNames.NNNN) ?
                tile.getRotation() : Rotation.R0; //die Rotation des übergebenen Spielsteins zum Start oder R0
        // falls es ein Aufruf fuer ein leeres Feld ist

        boolean firstIteration = true;
        for(int tileIndex = startIndex; tileIndex < Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY; tileIndex++){ //jeden Spielstein
            // in TileNames Reihenfole

            tile = inputTiles.getTileByTileNamesIndex(tileIndex); //der Spielstein an der TileNames Index stelle

            if (tile == null) continue; //wenn Spielstein nicht verfuegbar da gelegt zum naechsten gehen

            // Spielstein vom startIndex bis zum ende
            if(!this.isTileLaid(tile)) { //nur nicht gelegte Spielsteine nutzen

                if(startRotation != Rotation.R3) { //nur Rotationen durchlaufen sofern der Spielstein nicht schon die
                    // letze Rotation aufweist bei Aufruf dieser Methode
                    for (Rotation currRotation : Rotation.values()) {

                        //nur Rotation versuchen, wenn es sich um eine neue noch nicht probierte handelt; nur relevant
                        // in der ersten Iteration, da diese bereits mit einem Index ungleich 0 starten koennte
                        if (startRotation.ordinal() < currRotation.ordinal() //erster Durchlauf muss neue Rotation sein
                                || tile.getTile().equals(TileNames.NNNN) //oder egal falls mit NNNN gestartet wird, da
                                // hier garantiert eine neue Kombination versucht wird
                                || !firstIteration //oder nicht erster durchlauf wodurch es auch garantiert eine neue
                            // Kombination ist
                        ) {

                            tile.rotateTile(); //den Spielstein rotieren
                            inputGameField.layTile(pos.getX(), pos.getY(), tile);

                            if (inputGameField.isGameFieldTileMatching(pos.getX(), pos.getY(), true)) { //
                                // pruefen ob der neue Spielstein passt
                                inputGameField.resetTile(pos.getX(), pos.getY());

                                return (tile);
                            }
                            inputGameField.resetTile(pos.getX(), pos.getY());
                        }
                    }
                }
                startRotation = Rotation.R0; //StartRotation wieder auf 0 Anfang setzen,
                // da jetzt nicht mehr der uebergebene Spielstein getestet wird
                tile.resetTileRotation();
            }
            firstIteration = false;
        }
        return(null);
    }

    /**
     * Methode welche jedes Spielfeld Feld ausser Rand absucht nach Spielsteinen und den richtigen Spielstein dessen
     * index passt zurueckgegeben wird
     * @param index der Index nach TileNames Reihenfolge
     * @return der gesuchte Spielstein oder null falls nicht vorhanden
     */
    Tile getTileByTileNamesIndex(int index){
        for(int y = 1; y < this.getGameFieldHeight() - 1; y++){ //ohne oberen und unteren Rand
            for(int x = 1; x < this.getGameFieldHeight() - 1; x++){ //ohne linken und rechten Rand
                Tile currTile = this.getTile(x, y);
                if(currTile.getTileIndex() == index){
                    return(currTile);
                }
            }
        }
        return(null);
    }

    /**
     * Methode welche prueft, ob ein Spielfeld vom Rand her und den Loechern loesbar ist
     * @return ob das Spielfeld loesbar ist
     */
    boolean checkIfPlainGameFieldSolvable(){
        GameField gameFieldCopy = this.cloneGameField();
        gameFieldCopy.removeGameFieldTiles();
        return(gameFieldCopy.solveGameFieldAsCopy() != null);
    }

    /**
     * Methode welche prueft ob das Spielfeld im aktuellen Zustand loesbar ist
     * Diese Methode greift auf die solveGameFieldAsCopy Methode zurueck
     * @return ob eine passende Kombination gefunden wurde und somit das Spielfeld geloest wurde
     */
    boolean isGameFieldSolvable(){
        GameField solvedGameFieldCopy = this.solveGameFieldAsCopy();
        return(solvedGameFieldCopy != null && solvedGameFieldCopy.checkIfGameFieldSolved(false));
    }

    /**
     * Methode welche alle Spielfeldsteine vom Spielfeld entfernt
     */
    void removeGameFieldTiles() {
        for (int y = 0; y < this.getGameFieldHeight(); y++) {
            for (int x = 0; x < this.getGameFieldWidth(); x++) {
                if (!this.isFieldBorder(x, y)) { //kein Randstueck
                    Tile currTile = this.getTile(x, y);
                    if (currTile.isNormalGameTile()) { //kein Loch und nicht leer
                        this.resetTile(x, y);
                    }
                }
            }
        }
    }

    /**
     * Methode welche die toString Methode ueberschreibt und das Array in der Konsole ausgibt
     * @return das Array als String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.gameField.length; y++) { //Zeilen des Spielfelds durchlaufen
            for (int x = 0; x < this.gameField[y].length; x++) { //Spalten des Spielfelds durchlaufen
                sb.append(" ").append(this.gameField[y][x].getTileNameWithRotation()).append(" ");
            }
            sb.append("\n");
        }

        return(sb.toString());
    }
}