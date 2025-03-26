package logic;

/**
 * Klasse welche die java.lang Exception ueberschreibt/ erweitert um eigene FehlerCodes
 *
 * @author Anton Burmester
 */
public class CustomException extends Exception {
    //die Fehler Codes
    public static final int ERROR_WINDOW_OPEN = 101;
    public static final int ERROR_INVALID_FILE = 102;
    public static final int ERROR_INVALID_GAME_SIZE = 201;
    public static final int NO_GAME_OPEN = 301;


    private final int errorCode;

    /**
     * Konstruktor welcher die Instanz initialisiert mit dem errorCode
     *
     * @param errorCode Code des Fehlers
     */
    public CustomException(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * getter welcher den Error Code zurueckgibt
     *
     * @return der Error Code
     */
    public int getErrorCode() {
        return (this.errorCode);
    }
}
