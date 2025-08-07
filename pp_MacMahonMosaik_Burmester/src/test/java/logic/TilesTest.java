package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Tiles Klasse testet
 * @author Anton Burmester
 */

public class TilesTest {

    /**
     * ob bei der Initialisierung der Tiles Klasse alle Tiles dem Set hinzugefuegt werden
     */
    @Test
    public void constructorTest(){
        Tiles tilesClass = new Tiles();
        boolean status = true;
        for(int i = 0; i < TileNames.values().length - 2; i++){ //-2 da kein NNNN und kein HHHH
            if(!tilesClass.getTileByTileNamesIndex(i).getTileString().equals(TileNames.values()[i].name())){
                status = false;
            }
        }
        assertTrue(status);
    }


    /**
     * ob bei dem loeschen eines der Tiles Teile dieses erfolgreich geloescht wird
     */
    /*
    @Test
    public void removeTileTest(){
        Tiles tilesClass = new Tiles();
        tilesClass.removeTile(TileNames.GRGR);
        Set<TileNames> allTiles = Set.of(
                TileNames.RRRR,
                TileNames.GGGG,
                TileNames.YYYY,
                //TileNames.GRGR, //das geloeschte Teil
                TileNames.YRYR,
                TileNames.YGYG,
                TileNames.GRRR,
                TileNames.YRRR,
                TileNames.RGGG,
                TileNames.YGGG,
                TileNames.RYYY,
                TileNames.GYYY,
                TileNames.RGYR,
                TileNames.RYGR,
                TileNames.GRYG,
                TileNames.GYRG,
                TileNames.YRGY,
                TileNames.YGRY,
                TileNames.GGRR,
                TileNames.YYGG,
                TileNames.RRYY,
                TileNames.GRYR,
                TileNames.RGYG,
                TileNames.RYGY,
                TileNames.HHHH,
                TileNames.NNNN
        );
        assertEquals(tilesClass.getTiles(), allTiles);
    }

     */

    /**
     * ob bei dem loeschen eines der Tiles Teile dieses erfolgreich geloescht wird
     */
    /*
    @Test
    public void removeTileTwiceTest(){
        Tiles tilesClass = new Tiles();
        tilesClass.removeTile(TileNames.GRGR);
        tilesClass.removeTile(TileNames.GRGR);
        Set<TileNames> allTiles = Set.of(
                TileNames.RRRR,
                TileNames.GGGG,
                TileNames.YYYY,
                //TileNames.GRGR, //das geloeschte Teil
                TileNames.YRYR,
                TileNames.YGYG,
                TileNames.GRRR,
                TileNames.YRRR,
                TileNames.RGGG,
                TileNames.YGGG,
                TileNames.RYYY,
                TileNames.GYYY,
                TileNames.RGYR,
                TileNames.RYGR,
                TileNames.GRYG,
                TileNames.GYRG,
                TileNames.YRGY,
                TileNames.YGRY,
                TileNames.GGRR,
                TileNames.YYGG,
                TileNames.RRYY,
                TileNames.GRYR,
                TileNames.RGYG,
                TileNames.RYGY,
                TileNames.HHHH,
                TileNames.NNNN
        );
        assertEquals(tilesClass.getTiles(), allTiles);
    }

     */

    /**
     * ob ein geloeschtes Teil wieder hinzugefuegt werden kann
     */
    /*
    @Test
    public void addTileTest(){
        Tiles tilesClass = new Tiles();
        tilesClass.removeTile(TileNames.GRYG);
        tilesClass.addTile(TileNames.GRYG);
        Set<TileNames> allTiles = Set.of(
                TileNames.RRRR,
                TileNames.GGGG,
                TileNames.YYYY,
                TileNames.GRGR,
                TileNames.YRYR,
                TileNames.YGYG,
                TileNames.GRRR,
                TileNames.YRRR,
                TileNames.RGGG,
                TileNames.YGGG,
                TileNames.RYYY,
                TileNames.GYYY,
                TileNames.RGYR,
                TileNames.RYGR,
                TileNames.GRYG,
                TileNames.GYRG,
                TileNames.YRGY,
                TileNames.YGRY,
                TileNames.GGRR,
                TileNames.YYGG,
                TileNames.RRYY,
                TileNames.GRYR,
                TileNames.RGYG,
                TileNames.RYGY,
                TileNames.HHHH,
                TileNames.NNNN
        );
        assertEquals(tilesClass.getTiles(), allTiles);
    }

     */ //TODO
}
