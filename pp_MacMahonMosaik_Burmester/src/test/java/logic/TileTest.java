package logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Tile Klasse testet
 * @author Anton Burmester
 */

public class TileTest {

    //Tile(TileNames)

    /**
     * Tile(TileNames)
     * 1
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     */
    @Test
    public void Test_TileConstructor_1_RightTileName(){
        TileNames tileName = TileNames.RRRR;
        Tile tile = new Tile(tileName);
        assertEquals(tileName, tile.getTileName());
    }

    /**
     * Tile(TileNames)
     * 2
     * pruefen ob Tile Instanz mit Rotation R0 initialisiert wird
     */
    @Test
    public void Test_TileConstructor_2_RotationIsR0(){
        TileNames tileName = TileNames.HHHH;
        Tile tile = new Tile(tileName);
        assertEquals(Rotation.R0, tile.getRotation());
    }


    //Tile(TileNames, Rotation)

    /**
     * Tile(TileNames, Rotation)
     * 1
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     */
    @Test
    public void Test_TileConstructor2_1_RightTileName(){
        TileNames tileName = TileNames.RGYG;
        Tile tile = new Tile(tileName, Rotation.R0);
        assertEquals(tileName, tile.getTileName());
    }

    /**
     * Tile(TileNames, Rotation)
     * 2
     * pruefen ob Tile Instanz mit richtigen Rotation initialisiert wird
     */
    @Test
    public void Test_TileConstructor2_2_RigthRotation_R0(){
        TileNames tileName = TileNames.RGYG;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);
        assertEquals(rotation, tile.getRotation());
    }

    /**
     * Tile(TileNames, Rotation)
     * 3
     * pruefen ob Tile Instanz mit richtigen Rotation initialisiert wird
     */
    @Test
    public void Test_TileConstructor2_3_RigthRotation_R2(){
        TileNames tileName = TileNames.RGYG;
        Rotation rotation = Rotation.R2;
        Tile tile = new Tile(tileName, rotation);
        assertEquals(rotation, tile.getRotation());
    }


    //Tile(String)

    /**
     * Tile(String)
     * 1
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     * der Name des Spielsteins ist nicht rotiert
     */
    @Test
    public void Test_TileConstructor3_1_RightTileNameNotRotated(){
        String stringTileName = "RGYG"; //TileName als String (unrotiert)
        Tile tile = new Tile(stringTileName);

        TileNames correspondingTileName = TileNames.RGYG; //der String TileName als TileName
        assertEquals(correspondingTileName, tile.getTileName());
    }

    /**
     * Tile(String)
     * 2
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     * der Name des Spielsteins ist rotiert
     */
    @Test
    public void Test_TileConstructor3_2_RightTileNameRotated(){
        String stringTileName = "YGRG"; //TileName als String (2x rotiert, eigentlich RGYG)
        Tile tile = new Tile(stringTileName);

        TileNames correspondingTileName = TileNames.RGYG; //der String TileName als TileName (natuerlich unrotiert)
        assertEquals(correspondingTileName, tile.getTileName());
    }

    /**
     * Tile(String)
     * 3
     * pruefen ob Tile Instanz mit der richtigen Rotation initialisiert wird
     * der Name des Spielsteins ist nicht rotiert
     */
    @Test
    public void Test_TileConstructor3_3_RightTileNameNotRotated(){
        String stringTileName = "RGYG"; //TileName als String (unrotiert)
        Tile tile = new Tile(stringTileName);

        Rotation rotation = Rotation.R0; //keinmal rotiert
        assertEquals(rotation, tile.getRotation());
    }

    /**
     * Tile(String)
     * 4
     * pruefen ob Tile Instanz mit der richtigen Rotation initialisiert wird
     * der Name des Spielsteins ist 2x rotiert
     */
    @Test
    public void Test_TileConstructor3_4_RightTileNameRotated(){
        String stringTileName = "YGRG"; //TileName als String (2x rotiert, eigentlich RGYG)
        Tile tile = new Tile(stringTileName);

        Rotation rotation = Rotation.R2; //zweimal rotiert
        assertEquals(rotation, tile.getRotation());
    }


    //getTileName() TileNames
        //keine Tests, da einfacher Getter


    //getTileNameString() String
        //keine Tests, da einfacher Getter


    //getRotation() Rotation
        //keine Tests, da einfacher Getter


    //getTileNameWithRotation() String

    /**
     * getTileNameWithRotation() String
     * 1
     * pruefen ob die getTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R0
     */
    @Test
    public void Test_getTileNameStringWithRotation_1_R0(){
        TileNames tileName = TileNames.RGYR;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);

        String rotatedTileNameString = "RGYR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, tile.getTileNameStringWithRotation());
    }

    /**
     * getTileNameWithRotation() String
     * 2
     * pruefen ob die getTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R3
     */
    @Test
    public void Test_getTileNameStringWithRotation_2_R3(){
        TileNames tileName = TileNames.RGYR;
        Rotation rotation = Rotation.R3;
        Tile tile = new Tile(tileName, rotation);

        String rotatedTileNameString = "GYRR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, tile.getTileNameStringWithRotation());
    }


    //static simulateTileNameWithRotation(String, Rotation) String

    /**
     * static simulateTileNameWithRotation(String, Rotation) String
     * 1
     * pruefen ob die simulateTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R0
     */
    @Test
    public void Test_simulateTileNameWithRotation_1_R0(){
        String tileNameString = TileNames.RGYR.name();
        Rotation rotation = Rotation.R0;

        String rotatedTileNameString = "RGYR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, Tile.simulateTileNameWithRotation(tileNameString, rotation));
    }

    /**
     * static simulateTileNameWithRotation(String, Rotation) String
     * 2
     * pruefen ob die simulateTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R3
     */
    @Test
    public void Test_simulateTileNameWithRotation_2_R3(){
        String tileNameString = TileNames.RGYR.name();
        Rotation rotation = Rotation.R3;

        String rotatedTileNameString = "GYRR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, Tile.simulateTileNameWithRotation(tileNameString, rotation));
    }


    //isNormalGameTile() boolean

    /**
     * isNormalGameTile() boolean
     * 1
     * pruefen ob normale Spielsteine true liefern
     */
    @Test
    public void Test_isNormalGameTile_1_True(){
        ArrayList<Tile> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesicht durchlaufen
            if(currTileName.equals(TileNames.NNNN) || currTileName.equals(TileNames.HHHH)) continue; //da diese keine
            // normalen Spielsteine sind ueberspringen
            Tile currTile = new Tile(currTileName);

            if(!currTile.isNormalGameTile()) { //normaler Spielstein wird nicht als normaler Spielstein angesehen
                errorTiles.add(currTile);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong Tiles: " + errorTiles);
    }

    /**
     * isNormalGameTile() boolean
     * 2
     * pruefen ob nicht Spielsteine NNNN und HHHH false liefert
     */
    @Test
    public void Test_isNormalGameTile_2_NNNNFalse(){
        assertFalse(new Tile(TileNames.NNNN).isNormalGameTile(), "NNNN sollte false liefern");
        assertFalse(new Tile(TileNames.HHHH).isNormalGameTile(), "HHHH sollte false liefern");
    }


    //isPlaceHolderTile() boolean

    /**
     * isPlaceHolderTile() boolean
     * 1
     * pruefen ob nur Nichts gelegt Stein NNNN true liefert
     */
    @Test
    public void Test_isPlaceHolderTile_1_True(){
        assertTrue(new Tile(TileNames.NNNN).isPlaceHolderTile());
    }

    /**
     * isPlaceHolderTile() boolean
     * 2
     * pruefen ob alle anderen Steine false liefern
     */
    @Test
    public void Test_isPlaceHolderTile_2_False(){
        ArrayList<Tile> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesicht durchlaufen
            if(currTileName.equals(TileNames.NNNN)) continue; //da dieser Spielstein wahr ist diesen ueberspringen
            Tile currTile = new Tile(currTileName);

            if(currTile.isPlaceHolderTile()) { //normaler Spielstein wird als Platzhalter angesehen (Fehler)
                errorTiles.add(currTile);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong Tiles: " + errorTiles);
    }


    //isHoleTile() boolean

    /**
     * isHoleTile() boolean
     * 1
     * pruefen ob nur Lochstein HHHH true liefert
     */
    @Test
    public void Test_isHoleTile_1_True(){
        assertTrue(new Tile(TileNames.HHHH).isHoleTile());
    }

    /**
     * isPlaceHolderTile() boolean
     * 2
     * pruefen ob alle anderen Steine false liefern
     */
    @Test
    public void Test_isHoleTile_2_False(){
        ArrayList<Tile> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesicht durchlaufen
            if(currTileName.equals(TileNames.HHHH)) continue; //da dieser Spielstein wahr ist diesen ueberspringen
            Tile currTile = new Tile(currTileName);

            if(currTile.isHoleTile()) { //normaler Spielstein wird als Platzhalter angesehen (Fehler)
                errorTiles.add(currTile);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong Tiles: " + errorTiles);
    }


    //isTileBorderLayable() boolean

    /**
     * isTileBorderLayable() boolean
     * 1
     * pruefen ob kompatible Randsteine RRRR, GGGG, YYYY true liefern
     */
    @Test
    public void Test_isTileBorderLayable_1_True(){
        assertTrue(new Tile(TileNames.RRRR).isTileBorderLayable());
        assertTrue(new Tile(TileNames.GGGG).isTileBorderLayable());
        assertTrue(new Tile(TileNames.YYYY).isTileBorderLayable());
    }

    /**
     * isTileBorderLayable() boolean
     * 2
     * pruefen ob alle anderen Steine false liefern
     */
    @Test
    public void Test_isTileBorderLayable_2_False(){
        ArrayList<Tile> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesicht durchlaufen
            if(currTileName.equals(TileNames.RRRR) || currTileName.equals(TileNames.GGGG) ||
                    currTileName.equals(TileNames.YYYY)) continue; //da diese Spielstein wahr sind diese ueberspringen

            Tile currTile = new Tile(currTileName);

            if(currTile.isTileBorderLayable()) { //nicht legbarer Rand Spielstein wird als wahr angesehen (Fehler)
                errorTiles.add(currTile);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong Tiles: " + errorTiles);
    }


    //static isTileStringBorderLayable(String)

    /**
     * static isTileStringBorderLayable(String) boolean
     * 1
     * pruefen ob RRRR, GGGG, YYYY, NNNN true liefern
     */
    @Test
    public void Test_isTileStringBorderLayable_1_True(){
        assertTrue(Tile.isTileStringBorderLayable(TileNames.RRRR.name()));
        assertTrue(Tile.isTileStringBorderLayable(TileNames.GGGG.name()));
        assertTrue(Tile.isTileStringBorderLayable(TileNames.YYYY.name()));
        assertTrue(Tile.isTileStringBorderLayable(TileNames.NNNN.name()));
    }

    /**
     * static isTileStringBorderLayable(String) boolean
     * 2
     * pruefen ob andere false liefern
     */
    @Test
    public void Test_isTileStringBorderLayable_2_False(){
        ArrayList<TileNames> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesicht durchlaufen
            //da diese Spielstein wahr sind diese ueberspringen
            if(currTileName.equals(TileNames.RRRR) || currTileName.equals(TileNames.GGGG) ||
                    currTileName.equals(TileNames.YYYY) || currTileName.equals(TileNames.NNNN)) continue;

            if(Tile.isTileStringBorderLayable(currTileName.name())) { //nicht legbarer Rand wird als wahr angesehen
                // (Fehler)
                errorTiles.add(currTileName);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong TileNames: " + errorTiles);
    }


    //static isTileStringEdgeLayable(String) boolean

    /**
     * static isTileStringEdgeLayable(String) boolean
     * 1
     * pruefen ob nur NNNN true liefert
     */
    @Test
    public void Test_isTileStringEdgeLayable_1_True(){
        assertTrue(Tile.isTileStringEdgeLayable(TileNames.NNNN.name()));
    }

    /**
     * static isTileStringEdgeLayable(String) boolean
     * 2
     * pruefen ob andere false liefert
     */
    @Test
    public void Test_isTileStringEdgeLayable_2_False(){
        ArrayList<TileNames> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesicht durchlaufen
            if(currTileName.equals(TileNames.NNNN)) continue; //da dieser Spielstein wahr ist diesen ueberspringen

            if(Tile.isTileStringEdgeLayable(currTileName.name())) { //nicht legbarer Rand wird als wahr angesehen
                // (Fehler)
                errorTiles.add(currTileName);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong TileNames: " + errorTiles);
    }


    //static getTileClassFromTileName(String) Tile

    /**
     * getTileClassFromTileName(String) Tile
     * 15.1
     * pruefen ob nicht rotierter String den richtigen Tile liefert
     */
    @Test
    public void Test_getTileClassFromTileName_1_NotRotated(){
        String tileNameString = TileNames.RGYG.name(); // "RGYG"
        Tile tile = Tile.getTileClassFromTileName(tileNameString);

        assertNotNull(tile);
        assertEquals(tile.getTileName(), TileNames.RGYG);
        assertEquals(tile.getRotation(), Rotation.R0);
    }

    /**
     * getTileClassFromTileName(String) Tile
     * 2
     * pruefen ob rotierter String den richtigen Tile und die richtige Rotation liefert
     */
    @Test
    public void Test_getTileClassFromTileName_2_Rotated(){
        String tileNameString = "YGRG"; // RGYG um 2x (R2) rotiert
        Tile tile = Tile.getTileClassFromTileName(tileNameString);

        assertNotNull(tile);
        assertEquals(TileNames.RGYG, tile.getTileName());
        assertEquals(Rotation.R2, tile.getRotation());
    }

    /**
     * getTileClassFromTileName(String) Tile
     * 3
     * pruefen ob nicht bekannter String null liefert
     */
    @Test
    public void Test_getTileClassFromTileName_3_Unknown(){
        assertNull(Tile.getTileClassFromTileName("XXXX"));
    }


    //rotateTile() void

    /**
     * rotateTile() void
     * 1
     * pruefen ob 1x Rotation R0 zu R1 funktioniert
     */
    @Test
    public void Test_rotateTile_1_R0toR1(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R0);
        tile.rotateTile();
        assertEquals(Rotation.R1, tile.getRotation());
    }

    /**
     * rotateTile() void
     * 2
     * pruefen ob 1x Rotation R3 zu R0 funktioniert
     */
    @Test
    public void Test_rotateTile_2_R3toR0(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R3);
        tile.rotateTile();
        assertEquals(Rotation.R0, tile.getRotation());
    }

    /**
     * rotateTile() void
     * 3
     * pruefen ob 4x rotieren zur Ausgangsrotation fuehrt
     */
    @Test
    public void Test_rotateTile_3_FourTimesBackToStart(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R1);
        tile.rotateTile(); // R2
        tile.rotateTile(); // R3
        tile.rotateTile(); // R0
        tile.rotateTile(); // R1
        assertEquals(Rotation.R1, tile.getRotation());
    }

    /**
     * rotateTile() void
     * 4
     * pruefen ob 4x rotieren zur Ausgangsrotation fuehrt
     */
    @Test
    public void Test_rotateTile_4_FourTimesBackToStart2(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R0);
        tile.rotateTile(); // R1
        tile.rotateTile(); // R2
        tile.rotateTile(); // R3
        tile.rotateTile(); // R0
        assertEquals(Rotation.R0, tile.getRotation());
    }


    //setTileRotation(Rotation) void

    /**
     * setTileRotation(Rotation)
     * 1
     * pruefen ob Rotation korrekt gesetzt wird
     */
    @Test
    public void Test_setTileRotation_1_R0toR0(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R0);
        tile.setTileRotation(Rotation.R0);
        assertEquals(tile.getRotation(), Rotation.R0);
    }

    /**
     * setTileRotation(Rotation)
     * 2
     * pruefen ob Rotation korrekt gesetzt wird
     */
    @Test
    public void Test_setTileRotation_2_R0toR3(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R0);
        tile.setTileRotation(Rotation.R3);
        assertEquals(Rotation.R3, tile.getRotation());
    }


    //resetTileRotation() void

    /**
     * resetTileRotation() void
     * 1
     * pruefen ob Rotation R0 auf R0 zurueckgesetzt wird
     */
    @Test
    public void Test_resetTileRotation_1_ResetsR0ToR0(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R0);
        tile.resetTileRotation();
        assertEquals(Rotation.R0, tile.getRotation());
    }

    /**
     * resetTileRotation() void
     * 2
     * pruefen ob Rotation R0 auf R0 zurueckgesetzt wird
     */
    @Test
    public void Test_resetTileRotation_2_ResetsR3ToR0(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R3);
        tile.resetTileRotation();
        assertEquals(Rotation.R0, tile.getRotation());
    }


    //static getTileNamesString(String) String

    /**
     * static getTileNamesString(String) String
     * 19.1
     * pruefen ob unrotierter String den Enum Namen liefert
     */
    @Test
    public void Test_getTileNamesString_1_NotRotated(){
        String inputString = TileNames.RYGY.name();
        assertEquals("RYGY", Tile.getTileNamesString(inputString));
    }

    /**
     * static getTileNamesString(String) String
     * 2
     * pruefen ob rotierter String den unrotierten Enum Namen liefert
     */
    @Test
    public void Test_getTileNamesString_2_Rotated(){
        String rotated = "YGRG"; //RGYG 2x rotiert (R2)
        assertEquals("RGYG", Tile.getTileNamesString(rotated));
    }

    /**
     * static getTileNamesString(String) String
     * 3
     * pruefen ob ein unbekannter String null liefert
     */
    @Test
    public void Test_getTileNamesString_3_Unknown(){
        assertNull(Tile.getTileNamesString("ZZZZ"));
    }


    //getTileIndex() int

    /**
     * getTileIndex() int
     * 1
     * pruefen ob ein vorhandener Tile-Name den richtigen Index liefert
     */
    @Test
    public void Test_getTileIndex_1(){
        ArrayList<TileNames> errorTiles = new ArrayList<>();

        for(TileNames currTileName : TileNames.values()){ //jedes moegliche Spielstein Gesichtern durchlaufen
            Tile currTile = new Tile(currTileName);

            if(currTile.getTileIndex() != currTileName.ordinal()) { //Spielstein TileNames Index gleicht nicht den
                // TileNames Indexen mit dem der Spielstein initialisiert wurde (Fehler)
                errorTiles.add(currTileName);
            }
        }

        assertTrue(errorTiles.isEmpty(), "Wrong TileNames: " + errorTiles);
    }


    //cloneTile() void

    /**
     * cloneTile() void
     * 1
     * pruefen ob clon gleiche Werte hat, aber andere Instanz ist
     */
    @Test
    public void Test_cloneTile_1_EqualValuesButDifferentInstance(){
        Tile original = new Tile(TileNames.RGYG, Rotation.R2);
        Tile copy = original.cloneTile();

        //Payloads sind identisch
        assertNotSame(original, copy);
        assertEquals(original.getTileName(), copy.getTileName());
        assertEquals(original.getRotation(), copy.getRotation());
    }

    /**
     * cloneTile() void
     * 2
     * pruefen ob clon nicht mehr abhaengig ist
     */
    @Test
    public void Test_cloneTile_2_Independent(){
        Tile original = new Tile(TileNames.RGYG, Rotation.R2);
        Tile copy = original.cloneTile();

        //Unabhaengigkeit pruefen
        copy.rotateTile();
        assertNotEquals(original.getRotation(), copy.getRotation());
    }
}