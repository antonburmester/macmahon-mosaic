package logic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die GameTiles Klasse testet
 * @author Anton Burmester
 */

public class GameTilesTest {

    /**
     * ob bei der Initialisierung der GameTiles Klasse alle GameTiles dem Set hinzugefuegt werden
     */
    @Test
    public void constructorTest(){
        GameTiles gameTilesClass = new GameTiles();
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
        assertEquals(gameTilesClass.getTiles(), allTiles);
    }


    /**
     * ob bei dem loeschen eines der GameTiles Teile dieses erfolgreich geloescht wird
     */
    /*
    @Test
    public void removeTileTest(){
        GameTiles tilesClass = new GameTiles();
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
     * ob bei dem loeschen eines der GameTiles Teile dieses erfolgreich geloescht wird
     */
    /*
    @Test
    public void removeTileTwiceTest(){
        GameTiles tilesClass = new GameTiles();
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
        GameTiles tilesClass = new GameTiles();
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
