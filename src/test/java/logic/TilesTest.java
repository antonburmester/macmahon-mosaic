package logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Tiles Klasse testet
 * @author Anton Burmester
 */

public class TilesTest {

    //Tiles()

    /**
     * Tiles()
     * 1
     * ob Tiles Instanz mit allen Spielsteinen initialisiert wird
     */
    @Test
    public void Test_Constructor1_1_AllGameTiles(){
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
     * 2
     * ob Tiles Instanz ohne NNNN (nichts gelegt) und HHHH (Loch) Stein initialisiert wird
     */
    @Test
    public void Test_Constructor1_2_NoHHHHandNNNN(){
        Tiles tiles = new Tiles();

        assertNull(tiles.getTileByTileNamesIndex(TileNames.NNNN.ordinal())); //enthaelt nicht NNNN
        assertNull(tiles.getTileByTileNamesIndex(TileNames.HHHH.ordinal())); //enthaelt nicht nicht HHHH
    }


    //Tiles(ArrayList<Tile>)

    /**
     * Tiles(ArrayList<Tile>)
     * 1
     * ob Tiles Instanz mit allen Spielsteinen initialisiert wird
     */
    @Test
    public void Test_Constructor2_1_AllTilesInputAndTilesTileSame(){
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
     * 2
     * ob Tiles Instanz mit allen Spielsteinen initialisiert wird
     */
    @Test
    public void Test_Constructor2_2_AllTilesInputAndTilesTileSame(){
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


    //getTiles() ArrayList<Tile>
        //keine Tests, da einfacher Getter


    //getTileCount() int
        //keine Tests, da einfacher Getter


    //getTileByTileNamesIndex(int) Tile

    /**
     * getTileByTileNamesIndex(int) Tile
     * 1
     * ob der richtige Spielstein zurueckgegeben wird
     */
    @Test
    public void Test_getTileByTileNamesIndex_1_Existing(){
        Tile firstTile = new Tile(TileNames.NNNN);
        Tile secondTile = new Tile(TileNames.GRYR);
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        Tile firstTileByMethod = tiles.getTileByTileNamesIndex(firstTile.getTileIndex());
        Tile secondTileByMethod = tiles.getTileByTileNamesIndex(secondTile.getTileIndex());

        assertEquals(firstTile, firstTileByMethod);
        assertEquals(secondTile, secondTileByMethod);
    }

    /**
     * getTileByTileNamesIndex(int) Tile
     * 2
     * ob da der gesuchte Spielstein nicht vorhanden ist null zurueckgegeben wird
     */
    @Test
    public void Test_getTileByTileNamesIndex_2_NotExisting(){
        Tile firstTile = new Tile(TileNames.NNNN); //Index 25
        Tile secondTile = new Tile(TileNames.GRYR); //Index 21
        int notContainedIndex = 10; //Index 10 ist
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        Tile notExistingTile = tiles.getTileByTileNamesIndex(notContainedIndex);

        assertNull(notExistingTile);
    }

    //getTileByArrayIndex(int) Tile
        //keine Tests, da einfacher Getter


    //getTileByNameWithRotation(String) Tile

    /**
     * getTileByNameWithRotation(String) Tile
     * 1
     * ob der richtige Spielstein (unrotiert) welcher vorhanden ist zurueckgegeben wird
     */
    @Test
    public void Test_getTileByNameWithRotation_1_ExistingNotRotated(){
        Tile firstTile = new Tile(TileNames.NNNN, Rotation.R0);
        Tile secondTile = new Tile(TileNames.GRYR, Rotation.R0);
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        Tile firstTileByMethod = tiles.getTileByNameWithRotation(firstTile.getTileNameStringWithRotation());
        Tile secondTileByMethod = tiles.getTileByNameWithRotation(secondTile.getTileNameStringWithRotation());

        assertEquals(firstTile, firstTileByMethod);
        assertEquals(secondTile, secondTileByMethod);
    }

    /**
     * getTileByNameWithRotation(String) Tile
     * 2
     * ob der richtige Spielstein (rotiert) welcher vorhanden ist zurueckgegeben wird
     */
    @Test
    public void Test_getTileByNameWithRotation_2_ExistingRotated(){
        Tile firstTile = new Tile(TileNames.NNNN, Rotation.R2); //2x rotiert (R2)
        Tile secondTile = new Tile(TileNames.GRYR, Rotation.R3); //3x rotiert (R3)
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        Tile firstTileByMethod = tiles.getTileByNameWithRotation(firstTile.getTileNameStringWithRotation());
        Tile secondTileByMethod = tiles.getTileByNameWithRotation(secondTile.getTileNameStringWithRotation());

        assertEquals(firstTile, firstTileByMethod);
        assertEquals(secondTile, secondTileByMethod);
    }

    /**
     * getTileByNameWithRotation(String) Tile
     * 3
     * ob der richtige Spielstein (rotiert) welcher vorhanden ist zurueckgegeben wird
     * dieser ist aber in der falschen Rotation also muss am Ende geschaut werden, dass auch die Rotation stimmt
     */
    @Test
    public void Test_getTileByNameWithRotation_3_ExistingNeedsToBeRotated() {
        Tile firstTile = new Tile(TileNames.NNNN, Rotation.R2); //2x rotiert (R2)
        Tile secondTile = new Tile(TileNames.GRYR, Rotation.R3); //3x rotiert (R3)
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        String secondTile2RotationsString = "YRGR"; //GRYR:   R0: GRYR; R1: RGRY; R2; YRGR; R3: RYRG
        Tile searchedTileByMethod = tiles.getTileByNameWithRotation(secondTile2RotationsString);

        assertEquals(secondTile, searchedTileByMethod); //wurde gefunden
        assertEquals(Rotation.R2, searchedTileByMethod.getRotation()); //Rotation wurde durch die Methode gesetzt
    }


    /**
     * getTileByNameWithRotation(String) Tile
     * 4
     * ob null zurueckgegeben wird wenn ein nicht vorhandener Spielstein gesucht wird
     */
    @Test
    public void Test_getTileByNameWithRotation_4_NotExisting() {
        Tile firstTile = new Tile(TileNames.NNNN); //2x rotiert (R2)
        Tile secondTile = new Tile(TileNames.GRYR); //3x rotiert (R3)
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        Tile notContainedTile = new Tile(TileNames.GGGG);
        Tile notContainedTileByMethod = tiles.getTileByNameWithRotation(
                notContainedTile.getTileNameStringWithRotation());

        assertNull(notContainedTileByMethod);
    }

    /**
     * getTileByNameWithRotation(String) Tile
     * 5
     * ob der richtige Spielstein nicht vorhanden von der Rotation zurueckgesetzt wird
     */
    @Test
    public void Test_getTileByNameWithRotation_5_ExistingNeedsToBeRotated() {
        Tile firstTile = new Tile(TileNames.NNNN, Rotation.R2); //2x rotiert (R2)
        Tile secondTile = new Tile(TileNames.GRYR, Rotation.R3); //3x rotiert (R3)
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(firstTile);
        tileArrayList.add(secondTile);

        Tiles tiles = new Tiles(tileArrayList);

        String searchedTileString = TileNames.RGYG.name(); //nicht in Tiles vorhanden
        Tile searchedTileByMethod = tiles.getTileByNameWithRotation(searchedTileString);

        assertNull(searchedTileByMethod);
        assertEquals(Rotation.R2, firstTile.getRotation());
        assertEquals(Rotation.R3, secondTile.getRotation());
    }


    //containsTile() boolean


    //addTile(Tile) void

    /**
     * addTile(Tile) void
     * 1
     * zwei Spielsteine der leeren Tiles Klasse hinzufuegen
     */
    @Test
    public void test_addTile_1_EmtpyTilesClass(){
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
     * 2
     * zwei Spielstein der nicht leeren Tiles Klasse hinzufuegen
     */
    @Test
    public void test_addTile_2_NotEmptyTilesClass(){
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
     * 3
     * denselben Spielstein der Tiles Klasse mehrfach hinzufuegen
     */
    @Test
    public void test_addTile_3_SameTileMultipleTimesAdded(){
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


    //removeTile(Tile) void

    /**
     * removeTile(Tile) void
     * 1
     * einen Spielstein der leeren Tiles Klasse loeschen
     */
    @Test
    public void test_removeTile_1_EmtpyTilesClass(){
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
     * 2
     * einen Spielstein der Tiles Klasse loeschen, schauen ob der anderen Spielstein noch vorhanden ist
     */
    @Test
    public void test_removeTile_2_NotEmptyTilesClassTileContained(){
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
     * 3
     * einen Spielstein der Tiles Klasse mehrfach loeschen
     */
    @Test
    public void test_removeTile_3_SameTileMultipleTimes(){
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


    //cloneTiles()

    /**
     * cloneTiles() void
     * 1
     * ob die Refferenzen der Spielsteine der geklonten Tiles Klasse richtig von denen der originalen abweicht
     */
    @Test
    public void test_cloneTiles_1_AllTileDifferentInstance(){
        Tiles originalTiles = new Tiles();
        Tiles clonedTiles = originalTiles.cloneTiles();

        boolean status = true;
        for(int i = 0; i < originalTiles.getTileCount(); i++){
            Tile originalInstanceTile = originalTiles.getTileByArrayIndex(i);
            Tile clonedInstanceTile = clonedTiles.getTileByArrayIndex(i);

            if(originalInstanceTile.equals(clonedInstanceTile)) status = false;
        }
        assertTrue(status);
    }

    /**
     * cloneTiles() void
     * 1
     * ob die Nutzlasten der Spielsteine der geklonten Tiles Klasse von denen der originalen abweicht
     */
    @Test
    public void test_cloneTiles_2_AllTilePayloadSame(){
        Tiles originalTiles = new Tiles();
        Tiles clonedTiles = originalTiles.cloneTiles();

        boolean status = true;
        for(int i = 0; i < originalTiles.getTileCount(); i++){ //jedes Tile Element (Spielstein) durchlaufen
            Tile originalInstanceTile = originalTiles.getTileByArrayIndex(i);
            Tile clonedInstanceTile = clonedTiles.getTileByArrayIndex(i);

            //Nutzlasten des originalen und geklonten Spielstein vergleichen
            if(!(originalInstanceTile.getTileName().equals(clonedInstanceTile.getTileName()) &&
                    originalInstanceTile.getRotation().equals(clonedInstanceTile.getRotation()))) status = false;
        }
        assertTrue(status);
    }
}
