package logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Tile Klasse testet
 * @author Anton Burmester
 */

public class TileTest {

    //1 Tile(TileNames)

    /**
     * Tile(TileNames)
     * 1.1
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     */
    @Test
    public void Test_1_1_TileConstructor_RightTileName(){
        TileNames tileName = TileNames.RRRR;
        Tile tile = new Tile(tileName);
        assertEquals(tileName, tile.getTile());
    }

    /**
     * Tile(TileNames)
     * 1.2
     * pruefen ob Tile Instanz mit Rotation R0 initialisiert wird
     */
    @Test
    public void Test_1_2_TileConstructor_RotationIsR0(){
        TileNames tileName = TileNames.HHHH;
        Tile tile = new Tile(tileName);
        assertEquals(Rotation.R0, tile.getRotation());
    }


    //2 Tile(TileNames, Rotation)

    /**
     * Tile(TileNames, Rotation)
     * 2.1
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     */
    @Test
    public void Test_2_1_TileConstructor2_RightTileName(){
        TileNames tileName = TileNames.RGYG;
        Tile tile = new Tile(tileName, Rotation.R0);
        assertEquals(tileName, tile.getTile());
    }

    /**
     * Tile(TileNames, Rotation)
     * 2.2
     * pruefen ob Tile Instanz mit richtigen Rotation initialisiert wird
     */
    @Test
    public void Test_2_2_TileConstructor2_RigthRotation_R0(){
        TileNames tileName = TileNames.RGYG;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);
        assertEquals(rotation, tile.getRotation());
    }

    /**
     * Tile(TileNames, Rotation)
     * 2.3
     * pruefen ob Tile Instanz mit richtigen Rotation initialisiert wird
     */
    @Test
    public void Test_2_3_TileConstructor2_RigthRotation_R2(){
        TileNames tileName = TileNames.RGYG;
        Rotation rotation = Rotation.R2;
        Tile tile = new Tile(tileName, rotation);
        assertEquals(rotation, tile.getRotation());
    }


    //3 Tile(String)

    /**
     * Tile(String)
     * 3.1
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     * der Name des Spielsteins ist nicht rotiert
     */
    @Test
    public void Test_3_1_TileConstructor3_RightTileName_NotRotated(){
        String stringTileName = "RGYG"; //TileName als String (unrotiert)
        Tile tile = new Tile(stringTileName);

        TileNames correspondingTileName = TileNames.RGYG; //der String TileName als TileName
        assertEquals(correspondingTileName, tile.getTile());
    }

    /**
     * Tile(String)
     * 3.2
     * pruefen ob Tile Instanz mit richtigem TileName initialisiert wird
     * der Name des Spielsteins ist rotiert
     */
    @Test
    public void Test_3_2_TileConstructor3_RightTileName_Rotated(){
        String stringTileName = "YGRG"; //TileName als String (2x rotiert, eigentlich RGYG)
        Tile tile = new Tile(stringTileName);

        TileNames correspondingTileName = TileNames.RGYG; //der String TileName als TileName (natuerlich unrotiert)
        assertEquals(correspondingTileName, tile.getTile());
    }

    /**
     * Tile(String)
     * 3.3
     * pruefen ob Tile Instanz mit der richtigen Rotation initialisiert wird
     * der Name des Spielsteins ist nicht rotiert
     */
    @Test
    public void Test_3_3_TileConstructor3_RightTileName_NotRotated(){
        String stringTileName = "RGYG"; //TileName als String (unrotiert)
        Tile tile = new Tile(stringTileName);

        Rotation rotation = Rotation.R0; //keinmal rotiert
        assertEquals(rotation, tile.getRotation());
    }

    /**
     * Tile(String)
     * 3.4
     * pruefen ob Tile Instanz mit der richtigen Rotation initialisiert wird
     * der Name des Spielsteins ist 2x rotiert
     */
    @Test
    public void Test_3_4_TileConstructor3_RightTileName_Rotated(){
        String stringTileName = "YGRG"; //TileName als String (2x rotiert, eigentlich RGYG)
        Tile tile = new Tile(stringTileName);

        Rotation rotation = Rotation.R2; //zweimal rotiert
        assertEquals(rotation, tile.getRotation());
    }


    //4 getTile() TileNames

    /**
     * getTile() TileNames
     * 4.1
     * pruefen ob die getTile Methode den richtigen TileNames zurueckgibt
     * nicht rotiert (Rotierung hat keine Auswirkung auf TileNames)
     */
    @Test
    public void Test_4_1_getTile_NotRotated(){
        TileNames tileName = TileNames.GRGR;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);

        assertEquals(tileName, tile.getTile());
    }

    /**
     * getTile() TileNames
     * 4.2
     * pruefen ob die getTile Methode den richtigen TileNames zurueckgibt
     * zweimal rotiert (Rotierung hat keine Auswirkung auf TileNames)
     */
    @Test
    public void Test_4_2_getTile_Rotated(){
        TileNames tileName = TileNames.GRGR;
        Rotation rotation = Rotation.R3;
        Tile tile = new Tile(tileName, rotation);

        assertEquals(tileName, tile.getTile());
    }


    //5 getTileString() String

    /**
     * getTileString() String
     * 5.1
     * pruefen ob die getTileString Methode den richtigen TileNames String zurueckgibt
     * nicht rotiert (Rotierung hat keine Auswirkung auf String des TileNames)
     */
    @Test
    public void Test_5_1_getTileString_NotRotated(){
        TileNames tileName = TileNames.GRGR;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);

        String tileNameString = tileName.name();
        assertEquals(tileNameString, tile.getTileString());
    }

    /**
     * getTileString() String
     * 5.2
     * pruefen ob die getTileString Methode den richtigen TileNames zurueckgibt
     * zweimal rotiert (Rotierung hat keine Auswirkung auf String des TileNames)
     */
    @Test
    public void Test_5_2_getTileString_Rotated(){
        TileNames tileName = TileNames.GRGR;
        Rotation rotation = Rotation.R3;
        Tile tile = new Tile(tileName, rotation);

        String tileNameString = tileName.name();
        assertEquals(tileNameString, tile.getTileString());
    }


    //6 getRotation() Rotation

    /**
     * getRotation() Rotation
     * 6.1
     * pruefen ob die getRotation Methode die richtige Rotation zurueckgibt
     * R0
     */
    @Test
    public void Test_6_1_getRotation_R0(){
        TileNames tileName = TileNames.GRGR;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);

        assertEquals(rotation, tile.getRotation());
    }

    /**
     * getRotation() Rotation
     * 6.2
     * pruefen ob die getRotation Methode die richtige Rotation zurueckgibt
     * R3
     */
    @Test
    public void Test_6_2_getRotation_R3(){
        TileNames tileName = TileNames.GRGR;
        Rotation rotation = Rotation.R3;
        Tile tile = new Tile(tileName, rotation);

        assertEquals(rotation, tile.getRotation());
    }


    //7 getTileNameWithRotation() String

    /**
     * getTileNameWithRotation() String
     * 7.1
     * pruefen ob die getTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R0
     */
    @Test
    public void Test_7_1_getTileNameWithRotation_R0(){
        TileNames tileName = TileNames.RGYR;
        Rotation rotation = Rotation.R0;
        Tile tile = new Tile(tileName, rotation);

        String rotatedTileNameString = "RGYR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, tile.getTileNameWithRotation());
    }

    /**
     * getTileNameWithRotation() String
     * 7.2
     * pruefen ob die getTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R3
     */
    @Test
    public void Test_7_2_getTileNameWithRotation_R3(){
        TileNames tileName = TileNames.RGYR;
        Rotation rotation = Rotation.R3;
        Tile tile = new Tile(tileName, rotation);

        String rotatedTileNameString = "GYRR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, tile.getTileNameWithRotation());
    }


    //8 static simulateTileNameWithRotation(String, Rotation) String

    /**
     * static simulateTileNameWithRotation(String, Rotation) String
     * 8.1
     * pruefen ob die simulateTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R0
     */
    @Test
    public void Test_8_1_simulateTileNameWithRotation_R0(){
        String tileNameString = TileNames.RGYR.name();
        Rotation rotation = Rotation.R0;

        String rotatedTileNameString = "RGYR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, Tile.simulateTileNameWithRotation(tileNameString, rotation));
    }

    /**
     * static simulateTileNameWithRotation(String, Rotation) String
     * 8.2
     * pruefen ob die simulateTileNameWithRotation Methode die den richtigen String zuruekgibt
     * R3
     */
    @Test
    public void Test_8_2_simulateTileNameWithRotation_R3(){
        String tileNameString = TileNames.RGYR.name();
        Rotation rotation = Rotation.R3;

        String rotatedTileNameString = "GYRR"; //R0: RGYR; R1: RRGY; R2: YRRG; R3: GYRR
        assertEquals(rotatedTileNameString, Tile.simulateTileNameWithRotation(tileNameString, rotation));
    }


    //9 isNormalGameTile() boolean

    /**
     * isNormalGameTile() boolean
     * 9.1
     * pruefen ob normale Spielsteine true liefern
     */
    @Test
    public void Test_9_1_isNormalGameTile_True(){
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
     * 9.2
     * pruefen ob nicht Spielsteine NNNN und HHHH false liefert
     */
    @Test
    public void Test_9_2_isNormalGameTile_NNNNFalse(){
        assertFalse(new Tile(TileNames.NNNN).isNormalGameTile(), "NNNN sollte false liefern");
        assertFalse(new Tile(TileNames.HHHH).isNormalGameTile(), "HHHH sollte false liefern");
    }


    //10 isPlaceHolderTile() boolean

    /**
     * isPlaceHolderTile() boolean
     * 10.1
     * pruefen ob nur Nichts gelegt Stein NNNN true liefert
     */
    @Test
    public void Test_10_1_isPlaceHolderTile_True(){
        assertTrue(new Tile(TileNames.NNNN).isPlaceHolderTile());
    }

    /**
     * isPlaceHolderTile() boolean
     * 10.2
     * pruefen ob alle anderen Steine false liefern
     */
    @Test
    public void Test_10_2_isPlaceHolderTile_False(){
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


    //11 isHoleTile() boolean

    /**
     * isHoleTile() boolean
     * 11.1
     * pruefen ob nur Lochstein HHHH true liefert
     */
    @Test
    public void Test_11_1_isHoleTile_True(){
        assertTrue(new Tile(TileNames.HHHH).isHoleTile());
    }

    /**
     * isPlaceHolderTile() boolean
     * 11.2
     * pruefen ob alle anderen Steine false liefern
     */
    @Test
    public void Test_11_2_isHoleTile_False(){
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


    //12 isTileBorderLayable() boolean

    /**
     * isTileBorderLayable() boolean
     * 12.1
     * pruefen ob kompatible Randsteine RRRR, GGGG, YYYY true liefern
     */
    @Test
    public void Test_12_1_isTileBorderLayable_True(){
        assertTrue(new Tile(TileNames.RRRR).isTileBorderLayable());
        assertTrue(new Tile(TileNames.GGGG).isTileBorderLayable());
        assertTrue(new Tile(TileNames.YYYY).isTileBorderLayable());
    }

    /**
     * isTileBorderLayable() boolean
     * 12.2
     * pruefen ob alle anderen Steine false liefern
     */
    @Test
    public void Test_12_2_isTileBorderLayable_False(){
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


    //13 static isTileStringBorderLayable(String)

    /**
     * static isTileStringBorderLayable(String) boolean
     * 13.1
     * pruefen ob RRRR, GGGG, YYYY, NNNN true liefern
     */
    @Test
    public void Test_13_1_isTileStringBorderLayable_True(){
        assertTrue(Tile.isTileStringBorderLayable(TileNames.RRRR.name()));
        assertTrue(Tile.isTileStringBorderLayable(TileNames.GGGG.name()));
        assertTrue(Tile.isTileStringBorderLayable(TileNames.YYYY.name()));
        assertTrue(Tile.isTileStringBorderLayable(TileNames.NNNN.name()));
    }

    /**
     * static isTileStringBorderLayable(String) boolean
     * 13.2
     * pruefen ob andere false liefern
     */
    @Test
    public void Test_13_2_isTileStringBorderLayable_False(){
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


    //14 static isTileStringEdgeLayable(String) boolean

    /**
     * static isTileStringEdgeLayable(String) boolean
     * 14.1
     * pruefen ob nur NNNN true liefert
     */
    @Test
    public void Test_14_1_isTileStringEdgeLayable_True(){
        assertTrue(Tile.isTileStringEdgeLayable(TileNames.NNNN.name()));
    }

    /**
     * static isTileStringEdgeLayable(String) boolean
     * 14.2
     * pruefen ob andere false liefert
     */
    @Test
    public void Test_14_2_isTileStringEdgeLayable_False(){
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


    //15 static getTileClassFromTileName(String) Tile

    /**
     * getTileClassFromTileName(String) Tile
     * 15.1
     * pruefen ob nicht rotierter String den richtigen Tile liefert
     */
    @Test
    public void Test_15_1_getTileClassFromTileName_NotRotated(){
        String tileNameString = TileNames.RGYG.name(); // "RGYG"
        Tile tile = Tile.getTileClassFromTileName(tileNameString);

        assertNotNull(tile);
        assertEquals(tile.getTile(), TileNames.RGYG);
        assertEquals(tile.getRotation(), Rotation.R0);
    }

    /**
     * getTileClassFromTileName(String) Tile
     * 15.2
     * pruefen ob rotierter String den richtigen Tile und die richtige Rotation liefert
     */
    @Test
    public void Test_15_2_getTileClassFromTileName_Rotated(){
        String tileNameString = "YGRG"; // RGYG um 2x (R2) rotiert
        Tile tile = Tile.getTileClassFromTileName(tileNameString);

        assertNotNull(tile);
        assertEquals(TileNames.RGYG, tile.getTile());
        assertEquals(Rotation.R2, tile.getRotation());
    }

    /**
     * getTileClassFromTileName(String) Tile
     * 15.3
     * pruefen ob nicht bekannter String null liefert
     */
    @Test
    public void Test_15_3_getTileClassFromTileName_Unknown(){
        assertNull(Tile.getTileClassFromTileName("XXXX"));
    }


    //16 rotateTile() void

    /**
     * rotateTile() void
     * 16.1
     * pruefen ob 1x Rotation R0 zu R1 funktioniert
     */
    @Test
    public void Test_16_1_rotateTile_R0toR1(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R0);
        tile.rotateTile();
        assertEquals(Rotation.R1, tile.getRotation());
    }

    /**
     * rotateTile() void
     * 16.2
     * pruefen ob 1x Rotation R3 zu R0 funktioniert
     */
    @Test
    public void Test_16_2_rotateTile_R3toR0(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R3);
        tile.rotateTile();
        assertEquals(Rotation.R0, tile.getRotation());
    }

    /**
     * rotateTile() void
     * 16.3
     * pruefen ob 4x rotieren zur Ausgangsrotation fuehrt
     */
    @Test
    public void Test_16_3_rotateTile_FourTimesBackToStart(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R1);
        tile.rotateTile(); // R2
        tile.rotateTile(); // R3
        tile.rotateTile(); // R0
        tile.rotateTile(); // R1
        assertEquals(Rotation.R1, tile.getRotation());
    }

    /**
     * rotateTile() void
     * 16.4
     * pruefen ob 4x rotieren zur Ausgangsrotation fuehrt
     */
    @Test
    public void Test_16_4_rotateTile_FourTimesBackToStart2(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R0);
        tile.rotateTile(); // R1
        tile.rotateTile(); // R2
        tile.rotateTile(); // R3
        tile.rotateTile(); // R0
        assertEquals(Rotation.R0, tile.getRotation());
    }


    //17 setTileRotation(Rotation) void

    /**
     * setTileRotation(Rotation)
     * 17.1
     * pruefen ob Rotation korrekt gesetzt wird
     */
    @Test
    public void Test_17_1_setTileRotation_R0toR0(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R0);
        tile.setTileRotation(Rotation.R0);
        assertEquals(tile.getRotation(), Rotation.R0);
    }

    /**
     * setTileRotation(Rotation)
     * 17.2
     * pruefen ob Rotation korrekt gesetzt wird
     */
    @Test
    public void Test_17_2_setTileRotation_R0toR3(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R0);
        tile.setTileRotation(Rotation.R3);
        assertEquals(Rotation.R3, tile.getRotation());
    }


    //18 resetTileRotation() void

    /**
     * resetTileRotation() void
     * 18.1
     * pruefen ob Rotation R0 auf R0 zurueckgesetzt wird
     */
    @Test
    public void Test_18_1_resetTileRotation_ResetsR0ToR0(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R0);
        tile.resetTileRotation();
        assertEquals(Rotation.R0, tile.getRotation());
    }

    /**
     * resetTileRotation() void
     * 18.2
     * pruefen ob Rotation R0 auf R0 zurueckgesetzt wird
     */
    @Test
    public void Test_18_2_resetTileRotation_ResetsR3ToR0(){
        Tile tile = new Tile(TileNames.GRGR, Rotation.R3);
        tile.resetTileRotation();
        assertEquals(Rotation.R0, tile.getRotation());
    }


    //19 static getTileNamesString(String) String

    /**
     * static getTileNamesString(String) String
     * 19.1
     * pruefen ob unrotierter String den Enum Namen liefert
     */
    @Test
    public void Test_19_1_getTileNamesString_NotRotated(){
        String inputString = TileNames.RYGY.name();
        assertEquals("RYGY", Tile.getTileNamesString(inputString));
    }

    /**
     * static getTileNamesString(String) String
     * 19.2
     * pruefen ob rotierter String den unrotierten Enum Namen liefert
     */
    @Test
    public void Test_19_2_getTileNamesString_Rotated(){
        String rotated = "YGRG"; //RGYG 2x rotiert (R2)
        assertEquals("RGYG", Tile.getTileNamesString(rotated));
    }

    /**
     * static getTileNamesString(String) String
     * 19.3
     * pruefen ob ein unbekannter String null liefert
     */
    @Test
    public void Test_19_3_getTileNamesString_Unknown(){
        assertNull(Tile.getTileNamesString("ZZZZ"));
    }


    //20 getTileIndex() int

    /**
     * getTileIndex() int
     * 20.1
     * pruefen ob ein vorhandener Tile-Name den richtigen Index liefert
     */
    @Test
    public void Test_20_1_getTileIndex(){
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


    //21 cloneTile() void

    /**
     * cloneTile() void
     * 21.1
     * pruefen ob clon gleiche Werte hat, aber andere Instanz ist
     */
    @Test
    public void Test_22_1_cloneTile_EqualValuesButDifferentInstance(){
        Tile original = new Tile(TileNames.RGYG, Rotation.R2);
        Tile copy = original.cloneTile();

        //Payloads sind identisch
        assertNotSame(original, copy);
        assertEquals(original.getTile(), copy.getTile());
        assertEquals(original.getRotation(), copy.getRotation());
    }

    /**
     * cloneTile() void
     * 21.2
     * pruefen ob clon nicht mehr abhaengig ist
     */
    @Test
    public void Test_21_1_cloneTile_Independent(){
        Tile original = new Tile(TileNames.RGYG, Rotation.R2);
        Tile copy = original.cloneTile();

        //Unabhaengigkeit pruefen
        copy.rotateTile();
        assertNotEquals(original.getRotation(), copy.getRotation());
    }


    //22 toString() String

    /**
     * toString() String
     * 22.1
     * pruefen ob toString relevante Informationen enthaelt
     */
    @Test
    public void Test_23_1_toString_ContainsFields(){
        Tile tile = new Tile(TileNames.RGYR, Rotation.R3);
        String tileString = tile.toString();

        assertTrue(tileString.contains("Normal Tile Name:"));
        assertTrue(tileString.contains("Tile Name with Rotation:"));
        assertTrue(tileString.contains("Tile Rotation:"));
        assertTrue(tileString.contains(tile.getTileString()));
        assertTrue(tileString.contains(tile.getTileNameWithRotation()));
        assertTrue(tileString.contains(tile.getRotation().toString()));
    }
}