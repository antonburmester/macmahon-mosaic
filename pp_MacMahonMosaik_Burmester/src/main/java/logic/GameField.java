package logic;

import gui.ErrorHandler;

/**
 * Klasse welche das Spielfeld als Zweidimensionales Array enthält
 *
 * @author Anton Burmester
 */

public class GameField {
    private final TileNames[][] gameField;

    /**
     * Konstruktor welcher ein Spielfeld ohne Steine initialisiert
     * @param height die Hoehe des neuen Spielfeldes
     * @param width die Breite des neuen Spielfeldes
     */
    public GameField(int height, int width){
        //if(height > 0 && height <= 6 && width > 0 && width <= 6) {
            this.gameField = new TileNames[height + 2][width + 2]; //Hoehe+2 und Breite+2 wegen der Raender
        //} else {
            //TODO removing Error Handling removing
        //    ErrorHandler.showError(new CustomException(CustomException.Error_Invalid_GameField_Size));
        //}
    }

    /**
     * Konstruktor welcher ein bestehendes Spielfeld initialisiert
     * @param input eingelesenes Spielfeld
     */
    public GameField(String[][] input){
        int height = input.length;
        int width = input[0].length;
        //if(height > 0 && height <= 6 && width > 0 && width <= 6) {
            this.gameField = new TileNames[height][width]; //Erste Dimension Hoehe, Zweite Dimension Breite
            for (int heigthIndex = 0; heigthIndex < height; heigthIndex++) { //durchläuft jede Hoehe des Felds
                for (int widthIndex = 0; widthIndex < width; widthIndex++) { //durchläuft jede Breite des Felds
                    //weist dem Feld das String Aequivalent des TileNames enum zu
                    this.gameField[heigthIndex][widthIndex] = TileNames.valueOf(input[heigthIndex][widthIndex]);
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
    public TileNames[][] getGameField() {
        return this.gameField;
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
            return (this.gameField[xIndex][yIndex].equals(TileNames.NNNN)); //ob das Feld leer ist
        } else { //wenn das gewaehlte Feld invalide ist
            return(false);
        }
    }

    /**
     * legt ein Mosaikstein sofern das Feld valid und leer ist
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param tile Spielstein
     */
    public void layTile(int xIndex, int yIndex, TileNames tile){
        if(isFieldFree(xIndex, yIndex)){
            this.gameField[xIndex][yIndex] = tile;
        }
    }
}
