package logic;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

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
        Tile tileClass = new Tile(TileNames.GRGR);
        assertEquals(tileClass.getTile(), TileNames.GRGR);
    }

    /**
     * ob bei der Initialisierung der Tile Klasse die Rotation richtig gesetzt wurde
     */
    @Test
    public void constructor2Test1Rotation() {
        Tile tileClass = new Tile(TileNames.GRGR, Rotation.R1);
        assertEquals(tileClass.getRotation(), Rotation.R1);
    }

    /**
     * ob bei der Initialisierung der Tile Klasse die Rotation richtig gesetzt wurde
     */
    @Test
    public void constructor2Test3Rotation() {
        Tile tileClass = new Tile(TileNames.GRGR, Rotation.R3);
        assertEquals(tileClass.getRotation(), Rotation.R3);
    }

    /**
     * ob bei der Tile Klasse die Rotation richtig gesetzt wird
     */
    @Test
    public void rotations2Tile() {
        Tile tileClass = new Tile(TileNames.GRGR);
        tileClass.rotateTile();
        tileClass.rotateTile();
        assertEquals(tileClass.getRotation(), 180);
    }

    /**
     * ob der Name eines Rotierten Mosaiksteins korrekt ist
     */
    @Test
    public void rotations2TileString() {
        Tile tileClass = new Tile(TileNames.RGYG);
        tileClass.rotateTile();
        tileClass.rotateTile();
        assertEquals(tileClass.getTileNameWithRotation(), "YGRG");
    }

    /**
     * ob der Name eines Rotierten Mosaiksteins korrekt ist
     */
    @Test
    public void rotations3TileString() {
        Tile tileClass = new Tile(TileNames.RGYG, Rotation.R1);
        tileClass.rotateTile();
        tileClass.rotateTile();
        tileClass.rotateTile();
        assertEquals(tileClass.getTileNameWithRotation(), "RGYG");
    }

    /**
     * ob die richtige Tile Klasse mit dem richtigen Motiv und der richtigen Anzahl an Drehungen zurueckgegeben wird
     */
    @Test
    public void getTileClassFromTileName1() {
        Tile tileClass = Tile.getTileClassFromTileName(TileNames.GRYR.toString());
        assertTrue(Objects.equals(tileClass.getTileNameWithRotation(), "GRYR") &&
                tileClass.getRotation() == Rotation.R0);
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
        Tile tileClass = Tile.getTileClassFromTileName("YRGR");
        assertEquals("GRYR", tileClass.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R2, tileClass.getRotation()); // überprüft die Rotation
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
        Tile tileClass = Tile.getTileClassFromTileName("RYRG");
        assertEquals("GRYR", tileClass.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R3, tileClass.getRotation()); // überprüft die Rotation
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
        Tile tileClass = Tile.getTileClassFromTileName("GRYR");
        assertEquals("GRYR", tileClass.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R0, tileClass.getRotation()); // überprüft die Rotation
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
        Tile tileClass = Tile.getTileClassFromTileName("YYGR");
        assertEquals("YGRY", tileClass.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R1, tileClass.getRotation()); // überprüft die Rotation
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
        Tile tileClass = Tile.getTileClassFromTileName("GRYY");
        assertEquals("YGRY", tileClass.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R3, tileClass.getRotation()); // überprüft die Rotation
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
        Tile tileClass = Tile.getTileClassFromTileName("YGRY");
        assertEquals("YGRY", tileClass.getTileString()); // überprüft den Namen nach der Drehung
        assertEquals(Rotation.R0, tileClass.getRotation()); // überprüft die Rotation
    }

    /**
     * Testet alle moeglichen Steine in allen Moeglichen Drehungen
     * ob die richtige Tile Klasse mit dem richtigen TileNames Motiv und dem Namen nach den Drehungen richtig ist
     */
    @Test
    public void getTileClassFromTileNameAllTests() {
        boolean status = true;
        Tile currTileClass;
        Tile foundTileClass;
        for(TileNames currTileName : TileNames.values()) {
            currTileClass = new Tile(currTileName);
            for (int i = 0; i < 4; i++) {
                foundTileClass = Tile.getTileClassFromTileName(currTileClass.getTileNameWithRotation());
                if(!(currTileClass.getTileString().equals(foundTileClass.getTileString())
                        && currTileClass.getTileNameWithRotation().equals(foundTileClass.getTileNameWithRotation()))){
                    status = false;
                }
                currTileClass.rotateTile();
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
