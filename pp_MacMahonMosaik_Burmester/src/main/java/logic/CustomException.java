package logic;

/**
 * Klasse welche die java.lang Exception ueberschreibt/ erweitert um eigene FehlerCodes
 *
 * @author Anton Burmester
 */
public class CustomException extends Exception {
    //die Fehler Codes
    public static final int ERROR_WINDOW_OPEN = 101;
    public static final int ERROR_INVALID_FILE = 201;
    public static final int ERROR_FILE_READ_FAILED = 202;
    public static final int ERROR_INVALID_JSON_STRUCTURE = 301;
    public static final int ERROR_INVALID_JSON_NO_FIELD = 302;
    public static final int ERROR_INVALID_JSON_WRONG_FIELD_TYPE = 303;
    public static final int ERROR_INVALID_JSON_GAME_SIZE = 304;
    public static final int ERROR_INVALID_TILENAMES = 401;
    public static final int ERROR_INVALID_TILENAMES_BORDER = 402;
    public static final int ERROR_INVALID_TILENAMES_EDGE = 403;
    public static final int ERROR_MIDDLEGAMEFIELD_TILE_TOO_OFTEN = 404;
    public static final int ERROR_MIDDLEGAMEFIELD_HOLE = 405;
    public static final int ERROR_INVALID_GAME_SIZE = 501;
    public static final int ERROR_NO_GAME_OPEN = 502;
    public static final int ERROR_BORDER_NOT_SETTED = 503;
    public static final int ERROR_EDITOR_MODE_ON = 504;


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
