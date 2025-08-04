package logic;

import java.util.ArrayList;

/**
 * Klasse welche mehrere Positionen also Instanzen von Position enthalten
 */
class Positions {
    private final ArrayList<Position> positions; //alle Positionen
    private int index; //Index der aktuellen Instanz der Position

    /**
     * Konstruktor welcher die Positions Klasse initialisiert und alle leeren Felder deren Positionen speichert im Array
     * @param gameField das uebergebene Spielfeld aus welchem die Position der leeren Felder gespeichert werden sollen
     */
    public Positions(GameField gameField){
        this.positions = new ArrayList<>();
        this.index = -1; //-1 da noch keine Positionen

        this.fillPositionsWithAllGameFieldEmptyFields(gameField);
    }

    /**
     * geht zu der naechsten Position und speichert den neuen Index
     * @return die naechste Position oder null falls es keine gibt
     */
    public Position getAndLogNextPosition(){
        if(this.index < this.positions.size() - 1) { //es gibt eine naechste Position
            this.index++;
            return (this.positions.get(this.index));
        } else { //es gibt keine naechste Position
            return(null);
        }
    }

    /**
     * geht zu der vorherigen Position und speichert den neuen Index
     * @return die vorherige Position oder null falls es keine gibt
     */
    public Position getAndLogPreviousPosition(){
        if(this.index > 0) { //es gibt eine vorherige Position
            this.index--;
            return (this.positions.get(this.index));
        } else { //es gibt keine vorherige Position
            return(null);
        }
    }

    /**
     * Methode welche das uebergebene Spielfeld abgesehen vom Rand durchlaeuft und von allen Felder welche leer sind die
     * Position speichert.
     * Die Reihenfolge ist ausschlaggebend weshalb bei 1,1 angefangen wird.
     * @param gameField das uebergebene Spielfeld aus welchem von allen leeren Feldern die Position gespeichert werden
     */
    private void fillPositionsWithAllGameFieldEmptyFields(GameField gameField){
        for(int y = 1; y < gameField.getGameFieldHeight() - 1; y++){ //jede Spielfeld Zeile durchlaufen ausser Rand
            for(int x = 1; x < gameField.getGameFieldWidth() - 1; x++){ //jede Spielfeld Spalte durchlaufen ausser Rand
                if(gameField.getTile(x, y).isPlaceHolderTile()){
                    this.positions.add(new Position(x, y));
                }
            }
        }
    }

    /**
     * Methode welche die toString Methode ueberschreibt und jede Position in einer eigenen Zeile ausgibt
     * @return alle Positionen in einem formatierten String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Position currPos : positions){
            sb.append(currPos.toString()).append("\n");
        }
        return(sb.toString());
    }
}
