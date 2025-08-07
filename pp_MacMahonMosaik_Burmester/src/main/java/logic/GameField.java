package logic;

import gui.ErrorMessageHandler;

/**
 * Klasse welche das Spielfeld als Zweidimensionales Array enthaelt
 *
 * @author Anton Burmester
 */

public class GameField {
    private Tile[][] gameField; //Nutzlast des Spielfelds als zweidimensionales Array vom Typ Tile (Spielstein)

    /**
     * Konstruktor welcher ein Spielfeld ohne Steine initialisiert
     * @param height die Hoehe des neuen Spielfeldes
     * @param width die Breite des neuen Spielfeldes
     * @param placeHoles ob die Loecher automatisch im Spielfeld gesetzt werden sollen (nur false bei copy, da hier in
     *                   der Kopie schon die Standorte der Loecher vorhanden sind)
     */
    public GameField(int height, int width, boolean placeHoles){
        this.gameField = new Tile[height + 2][width + 2]; //Hoehe+2 und Breite+2 wegen der Raender
        this.placeGameFieldEmpty();
        if(placeHoles)
            this.placeGameFieldHoles();
    }

    /**
     * Konstruktor welcher ein bestehendes Spielfeld initialisiert
     * @param stringGameField eingelesenes Spielfeld
     * @param gameTiles die tiles Instanz der Spielsteine mit welcher das Spielfeld gefuellt wird
     */
    public GameField(String[][] stringGameField, Tiles gameTiles){
        int height = stringGameField.length;
        int width = stringGameField[0].length;

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
                            targetTile = gameTiles.getTileByNameWithRotation(laidTileName);
                        } else { //wenn es sich um ein Randstueck handelt
                            targetTile = new Tile(laidTileName);
                        }
                    }
                    this.layTile(widthIndex, heigthIndex, targetTile); //Stein legen auf das Spielfeld
                    //wenn mittleres Spielfeld und nicht NNNN
                    if(!this.isFieldBorder(widthIndex, heigthIndex) && !targetTile.getTile().equals(TileNames.NNNN)){

                        gameTiles.removeTile(targetTile); //Spielstein aus Spielsteinauswahl loeschen, da dieser
                        // hiernach mit layTile() gelegt wird
                    }

                }
            }
        } else { //falls kein valides Spiel geladen werden kann ein leeres erzeugen
            this.placeGameFieldHoles(); //TODO lieber zurueckgeben, das Spielfeld nicht valide
        }
    }

    /**
     * Methode welche die groesse des Spielfelds aktualisiert
     * @param newColumnAmount neue Anzahl der Spalten des Spielfelds
     * @param newRowAmount neue Anzahl der Reihen des Spielfelds
     */
    void updateGameFieldSize2(int newColumnAmount, int newRowAmount){
        int oldWidth = this.getGameFieldWidth();
        int oldHeigth = this.getGameFieldHeight();

        //die Differenz der bestehenden GridSize Breite zur neuen
        int widthGrowLoss = newColumnAmount - oldWidth;
        //die Differenz der bestehenden GridSize Hoehe zur neuen
        int heigthGrowLoss = newRowAmount - oldHeigth;

        if(widthGrowLoss != 0 || heigthGrowLoss != 0) {
            Tile[][] newGameField = new Tile[newRowAmount][newColumnAmount];


            int rowIteratorCopyStop = heigthGrowLoss > 0 ? oldHeigth - 1 : newRowAmount - 1; //wenn vergroessert dann
            // bis zur vorletzten Reihe kopiern damit der Rand noch nicht kopiert wurde; wenn verkleinert bis zur
            // vorletzten Reihe der neuen groesse kopieren damit der Rand nicht mitkopiert wird
            int columnIteratorCopyStop = widthGrowLoss > 0 ? oldWidth - 1 : newColumnAmount - 1; //wenn vergroessert
            // dann bis zur vorletzten Spalte kopiern damit der Rand noch nicht kopiert wurde; wenn verkleinert bis zur
            // vorletzten Spalte der neuen groesse kopieren damit der Rand nicht mitkopiert wird

            //das Spielfeld in die neue groesse kopieren ohne rechten und unteren Rand
            for(int y = 0; y < rowIteratorCopyStop; y++){
                for(int x = 0; x < columnIteratorCopyStop; x++){
                    newGameField[y][x] = this.gameField[y][x];
                }
            }

            if(widthGrowLoss > 0 || heigthGrowLoss > 0) { //Spielfeld soll vergroessert werden -> auffuellen mit
                // leeren Feldern
                int newWidthWithoutBorder = newColumnAmount - 1; //neue groesse ohne Rand
                int newHeigthWithoutBorder = newRowAmount - 1; //neue groesse ohne Rand
                for (int y = rowIteratorCopyStop; y < newHeigthWithoutBorder; y++) {
                    for (int x = columnIteratorCopyStop; x < newWidthWithoutBorder; x++) {
                        newGameField[y][x] = new Tile(TileNames.NNNN);
                    }
                }
            }

            //rechten Rand hinzufuegen
            for (int y = 0; y < newRowAmount; y++) {
                if(newColumnAmount > oldWidth){ //more column
                    //newGameField[y][newColumnAmount - 1] = this.gameField[y][];
                } else{ //less column

                }
            }


            this.gameField = newGameField;
        }
    }

    void updateGameFieldSize(int newColumnAmount, int newRowAmount){
        int oldWidth = this.getGameFieldWidth();
        int oldHeight = this.getGameFieldHeight();

        int widthDiff = newColumnAmount - oldWidth;
        int heightDiff = newRowAmount - oldHeight;

        if(widthDiff != 0 || heightDiff != 0) {
            Tile[][] newGameField = new Tile[newRowAmount][newColumnAmount];

            int rowCopyLimit = heightDiff > 0 ? oldHeight - 1 : newRowAmount - 1;
            int colCopyLimit = widthDiff > 0 ? oldWidth - 1 : newColumnAmount - 1;

            // Kopiere inneres Spielfeld (ohne rechten/unten Rand)
            for(int y = 0; y < rowCopyLimit; y++){
                for(int x = 0; x < colCopyLimit; x++){
                    newGameField[y][x] = this.gameField[y][x];
                }
            }

            // Fülle neue Zellen bei Vergrößerung mit leeren Tiles
            if(widthDiff > 0 || heightDiff > 0) {
                int maxY = newRowAmount - 1;
                int maxX = newColumnAmount - 1;

                for (int y = 0; y < maxY; y++) {
                    for (int x = colCopyLimit; x < maxX; x++) {
                        if (newGameField[y][x] == null)
                            newGameField[y][x] = new Tile(TileNames.NNNN);
                    }
                }
                for (int y = rowCopyLimit; y < maxY; y++) {
                    for (int x = 0; x < maxX; x++) {
                        if (newGameField[y][x] == null)
                            newGameField[y][x] = new Tile(TileNames.NNNN);
                    }
                }
            }

            //rechten Rand setzen
            for (int y = 0; y < newRowAmount; y++) {
                if (y < oldHeight) { //Hoehe muss im Bereich des neuen Spielfelds bleiben damit die Ecke leer ist
                        newGameField[y][newColumnAmount - 1] = this.gameField[y][oldWidth - 1];
                } else {
                    newGameField[y][newColumnAmount - 1] = new Tile(TileNames.NNNN);
                }
            }


            //unteren Rand setzen
            for (int x = 0; x < newColumnAmount; x++) {
                if (x < oldWidth) {
                    newGameField[newRowAmount - 1][x] = this.gameField[oldHeight - 1][x];
                } else {
                    newGameField[newRowAmount - 1][x] = new Tile(TileNames.NNNN);
                }
            }

            //Rechte untere Ecke immer neu setzen
            newGameField[newRowAmount - 1][newColumnAmount - 1] = new Tile(TileNames.NNNN);

            this.gameField = newGameField;
            System.out.println(this.toString());
        }
    }


    /**
     * Methode welche eine neue Reihe erzeugt
     * @param size die Anzahl der Spalten in der Reihe
     * @return die neue Reihe
     */
    public Tile[] createRow(int size){
        Tile[] newRowOrColumn = new Tile[size];
        for(int i = 0; i < size; i++){
            newRowOrColumn[i] = new Tile(TileNames.NNNN);
        }
        return(newRowOrColumn);
    }

    /**
     * Methode welche alle null Felder mit einem NNNN Spielstein fuellt
     */
    private void gameFieldFillEmptyFields(){
        for(int y = 0; y < this.getGameFieldHeight(); y++){
            for(int x = 0; x < this.getGameFieldWidth(); x++){
                if(this.gameField[y][x] == null){
                    this.gameField[y][x] = new Tile(TileNames.NNNN);
                }
            }
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
                this.gameField[yIndex][xIndex] = tile;
                status = true;
            }
        } else if(this.isFieldBorder(xIndex, yIndex)){ //Rand Feld des Spielfelds
            if(tile.isTileBorderLayable()){ //ob der uebergebene Spielstein gueltig fuer den Rand ist
                this.gameField[yIndex][xIndex] = tile;
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
            this.gameField[yIndex][xIndex] = new Tile(TileNames.NNNN);
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
