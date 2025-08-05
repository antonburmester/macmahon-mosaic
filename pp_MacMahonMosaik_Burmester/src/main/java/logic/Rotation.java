package logic;

/**
 * Enum welches alle Rotationen enthaelt
 *
 * @author Anton Burmester
 */
public enum Rotation {
    R0,
    R1,
    R2,
    R3;

    /**
     * Methode welche eine Rotation dieses Enums in Grad umrechnet
     * @param rotation die Rotation des Enums welche umgerechnet werden soll
     * @return die Gradzahl welche der Rotation entspricht
     */
    public static int rotationToDegrees(Rotation rotation) {
        return switch (rotation) {
            case R0 -> 0;
            case R1 -> 90;
            case R2 -> 180;
            case R3 -> 270;
        };
    }
}
