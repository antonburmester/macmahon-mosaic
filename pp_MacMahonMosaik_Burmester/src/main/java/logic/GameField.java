package logic;

/**
 * Klasse welche das Spielfeld als Zweidimensionales Array enthält
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
            for(int x = 0; x < this.gameField.length; x++){
                for(int y = 0; y < this.gameField[0].length; y++){
                    this.gameField[x][y] = new Tile(TileNames.NNNN);
                }
            }
    }

    /**
     * Konstruktor welcher ein bestehendes Spielfeld initialisiert
     * @param input eingelesenes Spielfeld
     */
    public GameField(String[][] input){
        int height = input.length;
        int width = input[0].length;
        //if(height > 0 && height <= 6 && width > 0 && width <= 6) {
            this.gameField = new Tile[height][width]; //Erste Dimension Hoehe, Zweite Dimension Breite
            for (int heigthIndex = 0; heigthIndex < height; heigthIndex++) { //durchläuft jede Hoehe des Felds
                for (int widthIndex = 0; widthIndex < width; widthIndex++) { //durchläuft jede Breite des Felds
                    //weist dem Feld das String Aequivalent des TileNames enum zu
                    this.gameField[heigthIndex][widthIndex] =
                            Tile.getTileClassFromTileName(input[heigthIndex][widthIndex]);
                }
            }
        //} else {
            //TODO removing Error Handling
        //    ErrorHandler.showError(new CustomException(CustomException.Error_Invalid_GameField_Size));
        //}
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
        return(this.gameField[x][y]);
    }

    /**
     * beim mittleren Spielfeld: prueft ob das Feld schon mit einem Spielstein oder Loch belegt ist
     * beim Rand: immer true weil beim Rand einfach die neuste Farbe zaehlt und es egal ist ob vorher eine Farbe da war
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @return ob das Feld Frei ist
     */
    public boolean isGameFieldFieldFree(int xIndex, int yIndex){
        boolean status = false;
        if(this.isFieldMiddleGamefield(xIndex, yIndex)) { //wenn das gewaehlte Feld Valide ist
            status = this.gameField[xIndex][yIndex].getTile().equals(TileNames.NNNN); //ob das Feld leer ist
        } else if(this.isFieldBorder(xIndex, yIndex)) { //wenn das gewaehlte Feld invalide ist
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
            if(this.isGameFieldFieldFree(xIndex, yIndex)){ //ob das Feld frei ist
                this.gameField[xIndex][yIndex] = tile;
                status = true;
            }
        } else if(this.isFieldBorder(xIndex, yIndex)){ //Rand Feld des Spielfelds
            if(tile.isTileBorderLayable()){ //ob der uebergebene Spielstein gueltig fuer den Rand ist
                this.gameField[xIndex][yIndex] = tile;
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
            this.gameField[xIndex][yIndex] = new Tile(TileNames.NNNN);
            status = true;
        }
/*
        if(!(this.gameField[xIndex][yIndex].getTile().equals(TileNames.HHHH))
                && this.isFieldMiddleGamefield(xIndex, yIndex)){
            this.gameField[xIndex][yIndex] = new Tile(TileNames.NNNN);
        } else {
            status = false;
        }
        TODO remove
 */
        return(status);
    }

    /**
     * legt einen kompatiblen Rand Mosaikstein Rand sofern das Feld ein Randfeld ist
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param tile Spielstein
     * @return ob der Spielstein gelegt werden konnte
     * TODO remove
     */
    public boolean colorBorder(int xIndex, int yIndex, Tile tile){
        boolean status = true;
        if(isFieldBorder(xIndex, yIndex)){
            this.gameField[xIndex][yIndex] = tile;
        } else {
            status = false;
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
        return(this.isFieldMiddleGamefield(xIndex, yIndex) || this.isFieldBorder(xIndex, yIndex));
    }

    /**
     * Methode welche prueft ob es sich bei dem Feld um ein mittleres Spielfeld handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein mittleres Spielfeldstueck handelt
     */
    public boolean isFieldMiddleGamefield(int xIndex, int yIndex){
        return(!this.isFieldBorder(xIndex, yIndex));
    }

    /**
     * Methode welche prueft ob es sich bei dem Feld um ein Randstueck handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Randstueck handelt
     */
    public boolean isFieldBorder(int xIndex, int yIndex){
        return(yIndex == 0 || yIndex == this.gameField[0].length - 1
                || xIndex == 0 || xIndex == this.gameField.length - 1);
    }

    /**
     * Methdoe welche prueft ob es sich bei dem Feld um ein Eckstueck handelt
     * @param xIndex die Breitenkoordinate
     * @param yIndex die Hoehenkoordinate
     * @return ob es sich um ein Eckstueck handelt
     */
    public boolean isFieldEdge(int xIndex, int yIndex){
        return(((xIndex == 0 && yIndex == 0) //linke obere Ecke
                || (xIndex == 0 && yIndex ==this.gameField[0].length - 1) //linke untere Ecke
                || (xIndex == this.gameField.length - 1 && yIndex == 0) //rechte obere Ecke
                || (xIndex == this.gameField.length - 1 && yIndex == this.gameField[0].length - 1))); //rechte untere Ecke
    }

    /**
     * Methode welche die toString Methode ueberschreibt und das Array in der Konsole ausgibt
     * @return das Array als String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int y = 0; y < this.gameField[0].length; y++) {
            for (int x = 0; x < this.gameField.length; x++) {
                sb.append(" ").append(this.gameField[x][y].getTileString()).append(" ");
            }
            sb.append("\n");
        }

        return(sb.toString());
    }
}
