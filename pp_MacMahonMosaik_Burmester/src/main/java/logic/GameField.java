package logic;

import gui.ErrorHandler;

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
                    this.gameField[x][y] = new Tile(TileNames.NNNN); //TODO do NNNN
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
     * prüft ob das vom Spieler gewaehlte Feld valide ist
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @return ob das Feld valide ist (muss im Bereich des Spielfelds sein und darf kein Randfeld sein)
     */
    public boolean isFieldSelectionValid(int xIndex, int yIndex){
        int heigth = this.gameField.length;
        int width = this.gameField[0].length;
        return(heigth > xIndex
                && 0 < xIndex
                && width > yIndex
                && 0 < yIndex);
    }

    /**
     * prueft ob das Feld schon mit einem Spielstein belegt ist und kein Loch ist
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @return ob das Feld Frei ist und kein Loch ist
     */
    public boolean isFieldFree(int xIndex, int yIndex){
        if(isFieldSelectionValid(xIndex, yIndex)) { //wenn das gewaehlte Feld Valide ist
            return (this.gameField[xIndex][yIndex].getTile().equals(TileNames.NNNN)); //ob das Feld leer ist
        } else { //wenn das gewaehlte Feld invalide ist
            return(false);
        }
    }

    /**
     * legt ein Mosaikstein sofern das Feld valid und leer ist
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param tile Spielstein
     * @return ob der Spielstein wieder zurueckgelegt werden konnte
     */
    public boolean layTile(int xIndex, int yIndex, Tile tile){
        System.out.println("Tile: " + tile.getTileString()); //TODO
        boolean status = true;
        if(isFieldFree(xIndex, yIndex)){
            this.gameField[xIndex][yIndex] = tile;
        } else {
            status = false;
        }
        return(status);
    }

    /**
     * Methode welche ein Spielfeld zuruecksetzt solange es sich um kein Loch handelt
     * @param xIndex der Spaltenindex des Feldes
     * @param yIndex der Zeilenindex des Feldes
     * @return ob das Spielfeld korrekt zurueckgesetzt werden konnte
     */
    public boolean resetTile(int xIndex, int yIndex){
        boolean status = true;
        if(!(this.gameField[xIndex][yIndex].getTile().equals(TileNames.HHHH))){
            this.gameField[xIndex][yIndex] = new Tile(TileNames.NNNN);
        } else {
            status = false;
        }
        return(status);
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
