package logic;

/**
 * Klasse welche das Spiel koodiniert
 * //TODO
 *
 * @author Anton Burmester
 */
public class Game {
    private final GUIConnector gui;
    GameField gameField;
    Tiles tiles;



    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gameField = new GameField(heigth, width);
        this.tiles = new Tiles();
        this.gui = gui;

        this.gui.updateGridPaneFormat(this.gameField);
        this.gui.displayGameFieldTiles(this.gameField);
        //this.gui.displayNotUsedTiles(this.tiles);
    }
}
