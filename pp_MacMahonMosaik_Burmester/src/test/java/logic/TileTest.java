package logic;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Tile Klasse testet
 * @author Anton Burmester
 */

public class TileTest {

    /**
     * ob bei der Initialisierung der Tile Klasse das Motiv richtig gesetzt wurde
     */
    @Test
    public void constructor1Test() {
        Tile tile = new Tile(TileNames.GRGR);
        assertEquals(tile.getTile(), TileNames.GRGR);
    }

    /**
     * ob bei der Initialisierung der Tile Klasse die Rotation richtig gesetzt wurde
     */
    @Test
    public void constructor2Test1Rotation() {
        Tile tile = new Tile(TileNames.GRGR, Rotation.R1);
        assertEquals(tile.getRotation(), Rotation.R1);
    }

    /**
     * ob bei der Initialisierung der Tile Klasse die Rotation richtig gesetzt wurde
     */
    @Test
    public void constructor2Test3Rotation() {
        Tile tile = new Tile(TileNames.GRGR, Rotation.R3);
        assertEquals(tile.getRotation(), Rotation.R3);
    }

    /**
     * ob bei der Tile Klasse die Rotation richtig gesetzt wird
     */
    @Test
    public void rotations2Tile() {
        Tile tile = new Tile(TileNames.GRGR);
        tile.rotateTile();
        tile.rotateTile();
        assertEquals(tile.getRotation(), Rotation.R2);
    }

    /**
     * ob der Name eines Rotierten Mosaiksteins korrekt ist
     */
    @Test
    public void rotations2TileString() {
        Tile tile = new Tile(TileNames.RGYG);
        tile.rotateTile();
        tile.rotateTile();
        assertEquals(tile.getTileNameWithRotation(), "YGRG");
    }

    /**
     * ob der Name eines Rotierten Mosaiksteins korrekt ist
     */
    @Test
    public void rotations3TileString() {
        Tile tile = new Tile(TileNames.RGYG, Rotation.R1);
        tile.rotateTile();
        tile.rotateTile();
        tile.rotateTile();
        assertEquals(tile.getTileNameWithRotation(), "RGYG");
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileName1() {
        Tile tile = Tile.getTileClassFromTileName(TileNames.GRYR.toString());
        assertTrue(Objects.equals(tile.getTileNameWithRotation(), "GRYR") &&
                tile.getRotation() == Rotation.R0);
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileName2() {
        //Anfangs Mosaikstein: GRYR
        //Einmal gedreht: RGRY
        //Zweimal gedreht YRGR
        //Dreimal gedreht RYRG
        //Viermal gedreht GRYR
        Tile tile = Tile.getTileClassFromTileName("YRGR");
        assertEquals("GRYR", tile.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R2, tile.getRotation()); // überprüft die Rotation
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileName3() {
        //Anfangs Mosaikstein: GRYR
        //Einmal gedreht: RGRY
        //Zweimal gedreht YRGR
        //Dreimal gedreht RYRG
        //Viermal gedreht GRYR
        Tile tile = Tile.getTileClassFromTileName("RYRG");
        assertEquals("GRYR", tile.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R3, tile.getRotation()); // überprüft die Rotation
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileName4() {
        //Anfangs Mosaikstein: GRYR
        //Einmal gedreht: RGRY
        //Zweimal gedreht YRGR
        //Dreimal gedreht RYRG
        //Viermal gedreht GRYR
        Tile tile = Tile.getTileClassFromTileName("GRYR");
        assertEquals("GRYR", tile.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R0, tile.getRotation()); // überprüft die Rotation
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileNameYGRY1() {
        //Anfangs Mosaikstein: YGRY
        //Einmal gedreht: YYGR
        //Zweimal gedreht RYYG
        //Dreimal gedreht GRYY
        //Viermal gedreht YGRY
        Tile tile = Tile.getTileClassFromTileName("YYGR");
        assertEquals("YGRY", tile.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R1, tile.getRotation()); // überprüft die Rotation
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileNameYGRY2() {
        //Anfangs Mosaikstein: YGRY
        //Einmal gedreht: YYGR
        //Zweimal gedreht RYYG
        //Dreimal gedreht GRYY
        //Viermal gedreht YGRY
        Tile tile = Tile.getTileClassFromTileName("GRYY");
        assertEquals("YGRY", tile.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R3, tile.getRotation()); // überprüft die Rotation
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileNameYGRY3() {
        //Anfangs Mosaikstein: YGRY
        //Einmal gedreht: YYGR
        //Zweimal gedreht RYYG
        //Dreimal gedreht GRYY
        //Viermal gedreht YGRY
        Tile tile = Tile.getTileClassFromTileName("YGRY");
        assertEquals("YGRY", tile.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R0, tile.getRotation()); // überprüft die Rotation
    }

    /**
     * Testet alle moeglichen Steine in allen Moeglichen Drehungen
     * ob die richtige Tile Klasse mit dem richtigen TileNames Motiv und dem Namen nach den Drehungen richtig ist
     */
    @Test
    public void getTileClassFromTileNameAllTests() {
        boolean status = true;
        Tile currTile;
        Tile foundTile;
        for(TileNames currTileName : TileNames.values()) {
            currTile = new Tile(currTileName);
            for (int i = 0; i < 4; i++) {
                foundTile = Tile.getTileClassFromTileName(currTile.getTileNameWithRotation());
                if(!(currTile.getTileString().equals(foundTile.getTileString())
                        && currTile.getTileNameWithRotation().equals(foundTile.getTileNameWithRotation()))){
                    status = false;
                }
                currTile.rotateTile();
            }
        }
        assertTrue(status);
    }

    /**
     * Konstruktor Test der Tile Klasse welcher auf Grundlage eines Tile Namens inklusive Rotationen die passende Tile
     * Klasse initialisiert
     */
    @Test
    public void tileKonstruktorRotationStringTest(){
        String tileNameWithRotation = "GYYR"; //YRGY YYRG GYYR RGYY YRGY
        Tile tile = new Tile(tileNameWithRotation);
        boolean isNameWithoutRotationMatching = tile.getTileString().equals("YRGY");
        boolean isNameWithRotationMatching = tile.getTileNameWithRotation().equals("GYYR");
        boolean isRotationMatching = tile.getRotation() == Rotation.R2;
        assertTrue(isNameWithoutRotationMatching && isNameWithRotationMatching && isRotationMatching);
    }
}
