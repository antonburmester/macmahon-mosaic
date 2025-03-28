package gui;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import logic.*;

import java.util.Objects;

/**
 * Klasse durch welche die Logik veraenderungen der GUI durchfuehren kann.
 * Die JavaFXGUI wird vom UserInterfaceController erzeugt und als Parameter an die Logik uebergeben.
 *
 * @author Anton Burmester
 */
public class JavaFXGUI implements GUIConnector {

    private GridPane gridPane;

    private Pane centerPane;

    private BorderPane borderPane;

    private ImageView[] imageViews;

    /**
     * Konstruktor welcher diese Klasse initialisiert
     * @param gridPane die FXML Instanz
     */
    public JavaFXGUI(BorderPane borderPane, Pane centerPane, GridPane gridPane){
        this.borderPane = borderPane;
        this.centerPane = centerPane;
        this.gridPane = gridPane;
    }

    /**
     * Methode welche Form der GridPane anzeigt
     * @param gameField das Spielfeld
     */
    public void updateGridPaneFormat(GameField gameField){
        this.gridPane.getChildren().clear(); //entfernt alte Zellen
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();

        int columnsCount = gameField.getGameField().length;
        int rowsCount = gameField.getGameField()[0].length;
        gridPane.setMinSize(0, 0); // Setze den minimalen Bereich auf 0, damit das Layout sofort skaliert.

        // Sofortige Größenanpassung nach dem Initialisieren der GUI
        /*
        Platform.runLater(() -> {
            adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
        });
         */


        /*
        double middleRowWidthSizePercentage = 100.0 / (rowsCount - 1); //-1 wegen des Randes
        double middleColWidthSizePercentage = 100.0 / (columnsCount - 1); //-1 wegen des Randes
        double borderRowWidthSizePercentage = middleRowWidthSizePercentage / 2;
        double borderColWidthSizePercentage = middleColWidthSizePercentage / 2;

         */
        double middleColWidthSizePercentage = 100 / (columnsCount - 1.5d);
        double borderColWidthSizePercentage = middleColWidthSizePercentage / 4;
        double middleRowWidthSizePercentage = 100 / (rowsCount - 1.5d);
        double borderRowWidthSizePercentage = middleRowWidthSizePercentage / 4;

        //gridPane.widthProperty().addListener((obs, oldVal, newVal) -> updateCellSizes(gameField));
        //gridPane.heightProperty().addListener((obs, oldVal, newVal) -> updateCellSizes(gameField));
        

        //neue Spalten
        for(int i = 0; i < columnsCount; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.ALWAYS);
            colConstraints.setPercentWidth((i == 0 || i == columnsCount - 1) ? borderColWidthSizePercentage : middleColWidthSizePercentage);
            //colConstraints.setPercentWidth(percentageWidth);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        //neue Zeilen
        for(int i = 0; i < rowsCount; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setPercentHeight((i == 0 || i == rowsCount - 1) ? borderRowWidthSizePercentage : middleRowWidthSizePercentage);
            //rowConstraints.setPercentHeight(percentageHeight);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // ChangeListener hinzufuegen, damit sich die GridPane an die Groeßenveraenderung der BorderPane anpasst
        centerPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));
        centerPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));
        adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
        gridPane.setGridLinesVisible(true);
        gridPane.setStyle("-fx-background-color: lightblue;");
        //displayGridPaneTiles(gridPane);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }


    private void adjustGridPaneSize(GridPane gridPane, double width, double height) {
        final int middleCols = gridPane.getColumnCount()-2;
        final int middleRows = gridPane.getRowCount()-2;
        //die äußeren beiden Spalten/Reihen zählen zusammen nur als halbe Spalte/Reihe
        final double colCount = middleCols + 0.5d;
        final double rowCount = middleRows + 0.5d;

        double cellSize = Math.min(width / colCount, height / rowCount);
        gridPane.setPrefSize(cellSize * colCount, cellSize * rowCount);
    }

    /**
     * Methode welche die GridPane fuellt
     * @param gameField das Spielfeld
     */
    public void displayGridPaneTiles(GameField gameField){
        int columnsCount = gridPane.getColumnCount();
        int rowsCount = gridPane.getRowCount();
        // Buttons erstellen und einfügen
        for (int x = 0; x < columnsCount; x++) {
            for (int y = 0; y < rowsCount; y++) {
                if(!((x == 0 && y == 0) //linke obere Ecke
                        || (x == 0 && y == rowsCount - 1) //linke untere Ecke
                        || (x == columnsCount - 1 && y == 0) //rechte obere Ecke
                        || (x == columnsCount - 1 && y == rowsCount - 1))){ //rechte untere Ecke
                Button btn = new Button(x + "," + y);
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Button passt sich an die Zelle an
                btn.setStyle("-fx-border-color: black; -fx-border-width: 1;"); // Sichtbare Umrandung
                gridPane.add(btn, x, y);
                }
            }
        }
        this.loadImages();
    }

    /**
     * Methode welche alle Bilder am Anfang des Spiels laedt ohne diese anzuzeigen
     * Die Bilder werden in dieser Klasse in einem Eindimensionalem Array
     * in der Reihenfolge des TileNames Enums gespeichert
     */
    public void loadImages(){
        ImageView[] imageViews = new ImageView[TileNames.values().length - 2];
        String imagePath;
        for(int i = 0; i < imageViews.length; i++){ //-2 weil HHHH und NNNN nicht als Bild vorhanden sind
                imagePath = "/tiles/" + TileNames.values()[i] + ".png";
                System.out.println(imagePath);
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                ImageView currIndexImage =
                        new ImageView(image);
                imageViews[i] = currIndexImage;
        }
        this.imageViews = imageViews;
    }

    /**
     * Methode welche die Bilder der Mosaiksteine setzt
     * @param gameField das Spielfeld
     */
    public void displayImages(GameField gameField){
        //TODO
    }

    private void updateCellSizes(GameField gameField) {
        int columnsCount = gameField.getGameField().length;
        int rowsCount = gameField.getGameField()[0].length;

        double spielfeldGroesse = Math.min(gridPane.getWidth(), gridPane.getHeight());
        double cellSize = spielfeldGroesse / (Math.max(columnsCount, rowsCount));

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                Integer col = GridPane.getColumnIndex(node);
                Integer row = GridPane.getRowIndex(node);

                if (row == 0 || row == rowsCount - 1) {
                    btn.setMinSize(cellSize, cellSize / 2);
                    btn.setMaxSize(cellSize, cellSize / 2);
                } else if (col == 0 || col == columnsCount - 1) {
                    btn.setMinSize(cellSize / 2, cellSize);
                    btn.setMaxSize(cellSize / 2, cellSize);
                } else {
                    btn.setMinSize(cellSize, cellSize);
                    btn.setMaxSize(cellSize, cellSize);
                }
            }
        }
    }

}
