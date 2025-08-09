package logic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Tiles Klasse testet
 * @author Anton Burmester
 */

public class TilesTest {

    //1 Tiles()

    /**
     * Tiles()
     * 1.1
     * ob Tiles Instanz mit allen Spielsteinen initialisiert wird
     */
    @Test
    public void Test_1_1_Constructor1_AllGameTiles(){
        Tiles tiles = new Tiles();

        ArrayList<Integer> errorTileIndexes = new ArrayList<>();

        for(int i = 0; i < Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY; i++){ //jeden Spielstein durchlaufen abgesehen von
            // HHHH und NNNN

            if(tiles.getTiles().get(i) == null){
                errorTileIndexes.add(i);
            }
        }
        assertTrue(errorTileIndexes.isEmpty(), "Missing Tiles at Indexes: " + errorTileIndexes);
    }

    /**
     * Tiles()
     * 1.2
     * ob Tiles Instanz ohne NNNN (nichts gelegt) und HHHH (Loch) Stein initialisiert wird
     */
    @Test
    public void Test_1_2_Constructor1_NoHHHHandNNNN(){
        Tiles tiles = new Tiles();

        assertNull(tiles.getTileByTileNamesIndex(TileNames.NNNN.ordinal())); //enthaelt nicht NNNN
        assertNull(tiles.getTileByTileNamesIndex(TileNames.HHHH.ordinal())); //enthaelt nicht nicht HHHH
    }


    //Tiles(ArrayList<Tile>)

    /**
     * Tiles(ArrayList<Tile>)
     * 2.1
     * ob Tiles Instanz mit allen Spielsteinen initialisiert wird
     */
    @Test
    public void Test_2_1_Constructor2_AllTilesInputAndTilesTileSame(){
        //ArrayList manuell mit allen Spielsteinen inklusive HHHH und NNNN fuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        for(int i = 0; i < Game.TILE_AMOUNT_COMPLETE; i++){ //jeden Spielstein durchlaufen inklusive von
            // HHHH und NNNN
            Tile currTile = new Tile(TileNames.values()[i]);
            tileArrayList.add(currTile);
        }

        //Tiles Instanz welche mit der Arraylist vom Typ Tile gefuellt wird
        Tiles tiles = new Tiles(tileArrayList);

        //ueberpruefen, ob die erzeugte Tiles Instanz alle uebergebenen Tile enthaelt
        ArrayList<Tile> missingTiles = new ArrayList<>();
        for(int i = 0; i < tileArrayList.size(); i++){
            Tile currArrayListTile = tileArrayList.get(i);
            Tile currTilesTile = tiles.getTiles().get(i);

            if(currArrayListTile != currTilesTile){
                missingTiles.add(currArrayListTile);
            }
        }

        assertTrue(missingTiles.isEmpty(), "Missing Tiles: " + missingTiles);
    }

    /**
     * Tiles(ArrayList<Tile>)
     * 2.2
     * ob Tiles Instanz mit allen Spielsteinen initialisiert wird
     */
    @Test
    public void Test_2_2_Constructor2_AllTilesInputAndTilesTileSame(){
        //ArrayList manuell mit allen Spielsteinen inklusive HHHH und NNNN fuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(new Tile(TileNames.GRGR));
        tileArrayList.add(new Tile(TileNames.RRRR));

        //Tiles Instanz welche mit der Arraylist vom Typ Tile gefuellt wird
        Tiles tiles = new Tiles(tileArrayList);

        //ueberpruefen, ob die erzeugte Tiles Instanz alle uebergebenen Tile enthaelt
        ArrayList<Tile> missingTiles = new ArrayList<>();
        for(int i = 0; i < tileArrayList.size(); i++){
            Tile currArrayListTile = tileArrayList.get(i);
            Tile currTilesTile = tiles.getTiles().get(i);

            if(currArrayListTile != currTilesTile){
                missingTiles.add(currArrayListTile);
            }
        }

        assertTrue(missingTiles.isEmpty(), "Missing Tiles: " + missingTiles);
    }


    //3 addTile(Tile) void

    /**
     * addTile(Tile) void
     * 3.1
     * zwei Spielsteine der leeren Tiles Klasse hinzufuegen
     */
    @Test
    public void Test_3_1_addTile_EmtpyTilesClass(){
        //ArrayList manuell leer befuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Tiles tiles = new Tiles(tileArrayList);

        //via addTile befuellen
        Tile firstAddedTile = new Tile(TileNames.RRRR);
        Tile secondAddedTile = new Tile(TileNames.GRYR);
        tiles.addTile(firstAddedTile);
        tiles.addTile(secondAddedTile);

        assertTrue(tiles.getTiles().contains(firstAddedTile));
        assertTrue(tiles.getTiles().contains(secondAddedTile));
    }

    /**
     * addTile(Tile) void
     * 3.2
     * zwei Spielstein der nicht leeren Tiles Klasse hinzufuegen
     */
    @Test
    public void Test_3_2_addTile_NotEmptyTilesClass(){
        //ArrayList manuell mit GGGG Tile befuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Tile intialTilesTile = new Tile(TileNames.GGGG);
        tileArrayList.add(intialTilesTile);

        Tiles tiles = new Tiles(tileArrayList);

        //via addTile befuellen
        Tile firstAddedTile = new Tile(TileNames.RRRR);
        Tile secondAddedTile = new Tile(TileNames.GRYR);
        tiles.addTile(firstAddedTile);
        tiles.addTile(secondAddedTile);

        assertTrue(tiles.getTiles().contains(intialTilesTile));
        assertTrue(tiles.getTiles().contains(firstAddedTile));
        assertTrue(tiles.getTiles().contains(secondAddedTile));
    }

    /**
     * addTile(Tile) void
     * 3.3
     * denselben Spielstein der Tiles Klasse mehrfach hinzufuegen
     */
    @Test
    public void Test_3_3_addTile_SameTileMultipleTimesAdded(){
        //ArrayList manuell mit GGGG befuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Tile intialTilesTile = new Tile(TileNames.GGGG);
        tileArrayList.add(intialTilesTile);

        Tiles tiles = new Tiles(tileArrayList);

        //via addTile befuellen obwohl hinzugefuegtes schon beim Init vorhanden war und zweimal hier hinzugefuegt wird
        tiles.addTile(intialTilesTile);
        tiles.addTile(intialTilesTile);

        assertTrue(tiles.getTiles().contains(intialTilesTile));
    }


    //4 removeTile(Tile) void

    /**
     * removeTile(Tile) void
     * 4.1
     * einen Spielstein der leeren Tiles Klasse loeschen
     */
    @Test
    public void Test_4_1_removeTile_EmtpyTilesClass(){
        //ArrayList manuell leer befuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Tiles tiles = new Tiles(tileArrayList);

        //via addTile loeschen
        Tile removedTile = new Tile(TileNames.RRRR);
        tiles.removeTile(removedTile);

        assertFalse(tiles.getTiles().contains(removedTile));
    }

    /**
     * removeTile(Tile) void
     * 4.2
     * einen Spielstein der Tiles Klasse loeschen, schauen ob der anderen Spielstein noch vorhanden ist
     */
    @Test
    public void Test_4_2_removeTile_NotEmptyTilesClassTileContained(){
        //ArrayList manuell mit GGGG und GRYR Tile befuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Tile firstTile = new Tile(TileNames.GGGG);
        Tile secondTile = new Tile(TileNames.GRYR);
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        //via removeTile loeschen
        tiles.removeTile(firstTile);

        assertFalse(tiles.containsTile(firstTile));
        assertTrue(tiles.containsTile(secondTile));
    }

    /**
     * removeTile(Tile) void
     * 4.3
     * einen Spielstein der Tiles Klasse mehrfach loeschen
     */
    @Test
    public void Test_4_3_removeTile_SameTileMultipleTimes(){
        //ArrayList manuell mit GGGG und GRYR Tile befuellen
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        Tile firstTile = new Tile(TileNames.GGGG);
        Tile secondTile = new Tile(TileNames.GRYR);
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        //via removeTile dasselbe mehrfach loeschen
        tiles.removeTile(firstTile);
        tiles.removeTile(firstTile);

        assertFalse(tiles.containsTile(firstTile));
        assertTrue(tiles.containsTile(secondTile));
    }

}
