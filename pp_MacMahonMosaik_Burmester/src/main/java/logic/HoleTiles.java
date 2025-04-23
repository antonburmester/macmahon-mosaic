package logic;

/**
 * Klasse welche Loch Spielsteine enthaelt
 * Diese Klasse erweitert/ erbt die GameTiles klasse
 *
 * @author Anton Burmester
 */
public class HoleTiles extends GameTiles {

    /**
     * Konstukrot welcher diese Subklasse welche Loecher darstellt initialisiert
     * @param holesCount wieviele Loecher es geben soll
     */
    public HoleTiles(int holesCount){
        super(createHoleTiles(holesCount)); //initialisiert die Elternklasse
    }

    /**
     * Methode welche soviele LochSteine initialisiert wie gefordert
     * @param holesCount die Anzahl der Lochsteine
     * @return ein Array mit allen Lochsteinen
     */
    private static Tile[] createHoleTiles(int holesCount) {
        Tile[] holeTiles = new Tile[holesCount];
        for(int i = 0; i < holesCount; i++){
            holeTiles[i] = new Tile(TileNames.HHHH);
        }
        return(holeTiles);
    }

    /**
     * Methode welche prueft ob alle Loecher gelegt wurden
     * @return ob alle Loecher gelegt wurden
     */
    public boolean allHolesUsed(){
        boolean status = true;
        for(int i = 0; i < super.getTiles().length; i++){
            if(super.getTile(i) == null){
                status = false;
            }
        }
        return(status);
    }
}
