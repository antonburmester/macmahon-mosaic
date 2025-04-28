package logic;

/**
 * Klasse welche das Spielfeld als Zweidimensionales Array enthaelt
 *
 * @author Anton Burmester
 */

public class GameField {
    private final Tile[][] gameField;

    /**
     * Konstruktor welcher ein Spielfeld ohne Steine initialisiert
     * @param height die Hoehe des neuen Spielfeldes
     * @param width die Breite des neuen Spielfeldes
     */
    public GameField(int height, int width){
        this.gameField = new Tile[height + 2][width + 2]; //Hoehe+2 und Breite+2 wegen der Raender
        for(int y = 0; y < this.gameField.length; y++){
            for(int x = 0; x < this.gameField[y].length; x++){
                this.gameField[y][x] = new Tile(TileNames.NNNN);
            }
        }
    }

    /**
     * Konstruktor welcher ein bestehendes Spielfeld initialisiert
     * @param stringGameField eingelesenes Spielfeld
     * @param gameTiles die tiles Klasse der Spielsteine mit welcher das Spielfeld gefuellt wird
     * @param holeTiles die tiles Klasse der Lochsteine mit welcher das Spielfeld gefuellt wird
     */
    public GameField(String[][] stringGameField, Tiles gameTiles, Tiles holeTiles){
        int height = stringGameField.length;
        int width = stringGameField[0].length;
        String[][] inputCompatible = this.translateSpielstandsdatei(stringGameField); //der Input aber Logik Kompatibel
        this.gameField = new Tile[height][width]; //Erste Dimension Hoehe, Zweite Dimension Breite
        for (int heigthIndex = 0; heigthIndex < height; heigthIndex++) { //durchlaeuft jede Hoehe des Felds
            for (int widthIndex = 0; widthIndex < width; widthIndex++) { //durchlaeuft jede Breite des Felds
                //weist dem Feld das String Aequivalent des TileNames enum zu
                this.gameField[heigthIndex][widthIndex] =
                        Tile.getTileClassFromTileName(inputCompatible[heigthIndex][widthIndex]);
            }
        }
    }

    /**
     * Methode welche die geforderten Spielstands Eingabe eines Spielfelds mit meiner Logik Kompatibel machen.
     * Hierbei geht es um die Raender da ich fuer die Raender RRRR, GGGG und YYYY nutze.
     * In den uebergebenen Spielstandsdateien wird stattdessen das Randstueck so gehandhabt als wuerde es ganz liegen
     * und deshalb ist nur die ans Spielfeld grenzende seite gefaerbt und der Rest nicht (N).
     * @param input das uebergebene String Array der Spielstandsdatei
     * @return das uebergebene String Array aber mit meiner Logik Kompatibel bezugelich Rand
     */
    private String[][] translateSpielstandsdatei(String[][] input){
        int height = input.length;
        int width = input[0].length;
        String[][] inputCopy = new String[input.length][input[0].length];

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
                tile.setIsLaid(true);
                status = true;
            }
        } else if(this.isFieldBorder(xIndex, yIndex)){ //Rand Feld des Spielfelds
            if(tile.isTileBorderLayable()){ //ob der uebergebene Spielstein gueltig fuer den Rand ist
                this.gameField[yIndex][xIndex] = tile;
                tile.setIsLaid(true);
                status = true;
            }
        }
        return(status);
    }

    /**
     * Methode welche ein Spielfeld zuruecksetzt
     * @param xIndex der Spaltenindex des Feldes
     * @param yIndex der Zeilenindex des Feldes
     * @return ob das Spielfeld korrekt zurueckgesetzt werden konnte
     */
    public boolean resetTile(int xIndex, int yIndex){
        boolean status = false;
        if(this.isFieldGamefield(xIndex, yIndex)){
            this.gameField[yIndex][xIndex] = new Tile(TileNames.NNNN);
            status = true;
        }
        return(status);
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
     * Methode welche prueft ob es sich bei dem Feld um ein Randstueck handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Randstueck handelt
     */
    public boolean isFieldBorder(int xIndex, int yIndex){
        return(xIndex == 0 || xIndex == this.gameField[0].length - 1
                || yIndex == 0 || yIndex == this.gameField.length - 1);
    }

    /**
     * Methdoe welche prueft ob es sich bei dem Feld um ein Eckstueck handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Eckstueck handelt
     */
    public boolean isFieldEdge(int xIndex, int yIndex){
        return(((xIndex == 0 && yIndex == 0) //linke obere Ecke
                || (xIndex == 0 && yIndex == this.gameField.length - 1) //linke untere Ecke
                || (xIndex == this.gameField[0].length - 1 && yIndex == 0) //rechte obere Ecke
                || (xIndex == this.gameField[0].length - 1 && yIndex == this.gameField.length - 1))); //rechte untere
                                                                                                      // Ecke
    }

    /**
     * Methode welche prueft ob der Rand des Spielfelds komplett konfiguriert wurde
     * @return ob der Rand des Spielfelds komplett ist
     */
    public boolean isGameFieldBorderSetted(){
        boolean status = true;
        for (int y = 0; y < this.gameField.length; y++) { //Zeilen des Spielfelds durchlaufen
            for (int x = 0; x < this.gameField[y].length; x++) { //Spalten des Spielfelds durchlaufen
                if (this.isFieldBorder(x, y)) { //ob das Feld ein Rand Feld ist
                    if (!this.getTile(x, y).isTileBorderLayable()) { //wenn es sich beim Spielstein der auf dem Rand Feld
                        // liegt nicht um ein Randstueck handelt
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
        String comparedTileNameWithRotation; //der Stein rundherum
        char comparedTileRelevantBorderLetter; //der jeweilige Buchstabe der Seite mit der Farbe des ueberprueften Stein
        if(this.isFieldGamefield(x, y) && this.isFieldMiddleGamefield(x, y)){
            //nach oben
            comparedTileNameWithRotation = this.getTile(x, y - 1).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(2);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(0);
            if(sourceTileRelevantBorderLetter != comparedTileRelevantBorderLetter && //wenn die Farben nicht gleich sind
                    !(comparedTileRelevantBorderLetter == 'H' || //und der Nachbar kein Loch ist
                    sourceTileRelevantBorderLetter == 'H') && //und der zu ueberpruefende Stein kein Loch ist
                    !((comparedTileRelevantBorderLetter == 'N' ||
                            sourceTileRelevantBorderLetter == 'N') && acceptN)) //wenn Nicht definiert(N) akzeptiert wird
                status = false; //und der pruefende Stein kein Loch


            //nach rechts
            comparedTileNameWithRotation = this.getTile(x + 1, y).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(3);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(1);
            if(sourceTileRelevantBorderLetter != comparedTileRelevantBorderLetter && //wenn die Farben nicht gleich sind
                    !(comparedTileRelevantBorderLetter == 'H' || //und der Nachbar kein Loch ist
                            sourceTileRelevantBorderLetter == 'H') && //und der zu ueberpruefende Stein kein Loch ist
                    !((comparedTileRelevantBorderLetter == 'N' ||
                            sourceTileRelevantBorderLetter == 'N') && acceptN)) //wenn Nicht definiert(N) akzeptiert wird
                status = false; //und der pruefende Stein kein Loch


            //nach unten
            comparedTileNameWithRotation = this.getTile(x, y + 1).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(0);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(2);
            if(sourceTileRelevantBorderLetter != comparedTileRelevantBorderLetter && //wenn die Farben nicht gleich sind
                    !(comparedTileRelevantBorderLetter == 'H' || //und der Nachbar kein Loch ist
                            sourceTileRelevantBorderLetter == 'H') && //und der zu ueberpruefende Stein kein Loch ist
                    !((comparedTileRelevantBorderLetter == 'N' ||
                            sourceTileRelevantBorderLetter == 'N') && acceptN)) //wenn Nicht definiert(N) akzeptiert wird
                status = false; //und der pruefende Stein kein Loch


            //nach links
            comparedTileNameWithRotation = this.getTile(x - 1, y).getTileNameWithRotation();
            comparedTileRelevantBorderLetter = comparedTileNameWithRotation.charAt(1);
            sourceTileRelevantBorderLetter = tileNameWithRotation.charAt(3);
            if(sourceTileRelevantBorderLetter != comparedTileRelevantBorderLetter && //wenn die Farben nicht gleich sind
                    !(comparedTileRelevantBorderLetter == 'H' || //und der Nachbar kein Loch ist
                            sourceTileRelevantBorderLetter == 'H') && //und der zu ueberpruefende Stein kein Loch ist
                    !((comparedTileRelevantBorderLetter == 'N' ||
                            sourceTileRelevantBorderLetter == 'N') && acceptN)) //wenn Nicht definiert(N) akzeptiert wird
                status = false; //und der pruefende Stein kein Loch

        }
        return(status);
    }

    /**
     * Methode welche prueft ob ein Spielfeld ganz geloest wurde bzw. richtig ist.
     * @param acceptN damit geprueft werden kann ob die bisherigen Steine korrekt liegen
     * @return ob das Spielfeld ganz korrekt fertig ist oder bisher korrekt fertig ist abgesehen von nichts gelegten
     * Felder
     */
    public boolean checkIfGameFieldSolved(boolean acceptN){
        boolean status = true;
        for(int y = 1; y < this.gameField.length -1; y++){
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
    public GameField cloneGameField(Tiles existingGameFieldTiles, Tiles existingHoleTiles,
                                    Tiles copyGameFieldTiles, Tiles copyHoleTiles){
        int width = this.gameField[0].length;
        int heigth = this.gameField.length;
        GameField copy = new GameField(heigth, width); //neue Instanz eines neuen Spielfelds

        Tile currNotCopyTile;
        Tile copyTile;
        for(int y = 0; y < heigth; y++){ //Hoehenindex
            for(int x = 0; x < width; x++){ //Breitenindex
                currNotCopyTile = this.getTile(x, y); //der aktuelle Stein welcher in das neue Spielfeld kopiert werden
                // soll
                if(currNotCopyTile.isNormalGameTile()){ //normaler Spielstein
                    System.out.println(currNotCopyTile.toString());
                    //die kopie von dem aktuell im Spielfeld liegenden Spielstein
                    copyTile = copyGameFieldTiles.getTile(existingGameFieldTiles.getTileIndex(currNotCopyTile));
                } else if(currNotCopyTile.isHoleTile()){ //Loch
                    copyTile = copyHoleTiles.getTile(existingHoleTiles.getTileIndex(currNotCopyTile));
                } else { //nichts gelegt
                    copyTile = new Tile(TileNames.NNNN);
                }
                copy.layTile(x, y, copyTile); //den Stein in das neue Spielfeld legen
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
