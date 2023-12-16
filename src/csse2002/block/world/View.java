package csse2002.block.world;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import csse2002.block.world.Controller;

public class View {
	/* The root node of the scene graph, to add all the GUI elements to */
    private HBox rootBox;

    private GraphicsContext context;

    /*
     * Buttons to draw different shapes ["Rectangle", "Circle", "Triangle",
     * "House", "Clear"]
     */
    private Button[] buttons;

    /*
     * text field to enter the x and y coord and previous pressed button/coords
     */
    private TextField xInput, yInput, previous;

    /**
     * Constructor
     */
    public View() {
        rootBox = new HBox();
        addComponents();
    }

    /**
     * Get the Scene of the GUI with the scene graph
     * 
     * @return the current scene
     */
    public Scene getScene() {
        return new Scene(rootBox);
    }

    /**
     * get the X coordinate entered in the corresponding Y coordinate input
     * field
     * 
     * @return the entered X coordinate from the text field
     */
    public String getXCoord() {
        return xInput.getText();
    }

    /**
     * get the Y coordinate entered in the corresponding Y coordinate input
     * field
     * 
     * @return the entered Y coordinate from the text field
     */
    public String getYCoord() {
        return yInput.getText();
    }

    /**
     * set the previously drawn button and (x,y) coordinates on display
     * 
     * @param button
     *            the previously pressed button
     */
    public void setPrevious(String button) {
        previous.setText(button);
    }

    /**
     * Get the number of buttons in the GUI
     * 
     * @return the number of buttons
     */
    public int numberOfButtons() {
        return buttons.length;
    }

    /**
     * Draws the shape with some colour at given x and y coordinates
     * 
     * @param shape
     *            the shape to draw
     * @param x
     *            the X coordinate of the shape
     * @param y
     *            the Y coordinate of the shape
     */
    public void drawShape(String shape, int x, int y) {
        switch (shape) {
            case "Rectangle" :
                drawRectangle(x, y);
                break;
            case "Circle" :
                drawCircle(x, y);
                break;
            case "Triangle" :
                drawTriangle(x, y);
                break;
            case "House" :
                drawHouse(x, y);
                break;
            case "Clear" :
                clearCanvas();
                break;
        }
    }

    /**
     * Adds the given handler to the given ith button
     * 
     * @param i
     *            the ith button to add the handler to
     * @param handler
     *            the handler to add to the button
     */
    public void addButtonHandler(EventHandler<ActionEvent> handler) {
        /*
         * Adds a handler to the setOnAction meaning when buttons is pressed her
         * this handler and its handle method
         */
        for (Button b : buttons) {
            b.setOnAction(handler);
        }
    }

    /**
     * Draws a rectangle with BLANCHEDALMOND colour :P
     * 
     * @param x
     *            the upper left X coordinate of the rectangle
     * @param y
     *            the upper left Y coordinate of the rectangle
     */
    private void drawRectangle(int x, int y) {
        /* Draw a rectangle at the given x and y coords */
        /* use the GraphicsContext (context) to draw on the canvas */
    	context.setFill(Color.BLANCHEDALMOND);
    	context.fillRect(x, y, 100, 100);

    }

    /**
     * Draws a circle with radius 50 and top left at (x,y) and TURQUOISE colour
     * 
     * @param x
     *            the X coordinate of the upper left bound of the circle.
     * @param y
     *            the Y coordinate of the upper left bound of the circle.
     */
    private void drawCircle(int x, int y) {
        /* Draw a circle at the given x and y coords */

        /* use the GraphicsContext (context) to draw on the canvas */

    	context.setFill(Color.TURQUOISE);
    	context.fillOval(x, y, 100, 100);

    }

    /**
     * Draws a triangle at the given (x,y) coordinates and colour DARKORANGE
     * <p>
     * draws the triangle such that bottom left point is at (x,y) another is at
     * <br>
     * (x+50, y-50) and last at (x+100, y)
     * 
     * @param x
     *            the x coordinate to start drawing the triangle
     * @param y
     *            the y coordinate to start drawing the triangle
     */
    private void drawTriangle(int x, int y) {
        /* Draw a triangle at the given x and y coords */
        /* use the GraphicsContext (context) to draw on the canvas */
    	double xpoints[] = {x, x+50, x+100};
    	double ypoints[] = {y, y-50, y};
        int npoints = 3;
        context.setFill(Color.DARKORANGE);
        context.fillPolygon(xpoints, ypoints, npoints);

    }

    /**
     * Draw the a house shape at the given x and y coords. <br>
     * The House's bottom left edge will be the starting (x,y)
     * <p>
     * x increase leftwards <br>
     * y increases downwards
     * 
     * @param x
     *            the x coordinate to start drawing at
     * @param y
     *            the y coordinate to start drawing the house at
     */
    private void drawHouse(int x, int y) {
        /*
         * Use multiple shapes and lines to draw something which looks like a
         * house
         * 
         *           /\ 
         * (x,y) -> /__\ 
         *          |  | 
         *          |__|
         * 
         * Something like this
         * 
         * Think about using existing methods from the View class
         */

        /*
         * use the GraphicsContext (context) to draw a house shape on the canvas
         */

        // TODO: code here
    }

    /**
     * clear the whole canvas, so remove all the drawn shapes on it
     */
    private void clearCanvas() {
        /* Remove all the shapes drawn on the canvas */
        /* use the GraphicsContext (context) to draw/clear the canvas */
        context.clearRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());

    }

    /**
     * clear all the text fields
     */
    public void clearFields() {
        /*
         * Clear all the TextFields so that users don't always have to clear
         * after entering a command
         */
        xInput.clear();
        yInput.clear();
        previous.clear();

    }

    /**
     * Adds all the GUI elements to the root layout
     * <p>
     * These is where the scene graph is created
     */
    private void addComponents() {
        VBox leftBox = new VBox();

        /* Add padding, colour to the left side */
        leftBox.setPadding(new Insets(10, 10, 10, 10));
        leftBox.setStyle("-fx-background-color: white");

        /* add all the leftside components to this leftBox */
        addLeftSideComponents(leftBox);

        /* Another layout node for the left side of the GUI */
        VBox rightBox = new VBox();

        /* add colour and padding to the right layout */
        rightBox.setSpacing(10);
        rightBox.setPadding(new Insets(20, 20, 20, 20));
        rightBox.setStyle("-fx-background-color: #336699");

        /* add all the right side components to this rightBox */
        addRightSideComponents(rightBox);

        /* add both layouts to the root HBox layout */
        rootBox.getChildren().add(leftBox);
        rootBox.getChildren().add(rightBox);
    }

    /**
     * Add all the Gui elements to the left container
     * 
     * @param Vbox
     *            the container to add the elements to
     */
    private void addLeftSideComponents(VBox box) {

        /*
         * This is where all the components which are on the left side such as
         * Canvas, TextFields and Labels will be added
         */

        /*
         * add the canvas inside a HBox the HBox (canvasContainer) is used so
         * that border can be added around the canvas
         */
        HBox canvasContainer = new HBox();
        Canvas canvas = new Canvas(450, 300);
        context = canvas.getGraphicsContext2D();
        canvasContainer.getChildren().add(canvas);
        canvasContainer.setStyle("-fx-border-color: black");

        /* Create another HBox and add textInputs and Labels inside it */
        HBox inputBox = new HBox();
        inputBox.setPadding(new Insets(10, 10, 10, 10));
        inputBox.setSpacing(15);
        /* Make everything inside the HBox Center aligned */
        inputBox.setAlignment(Pos.CENTER);

        /* Add labels and inputs to the HBox */

        /*
         * Add 2 labels, labelled "X-coord" and "Y-Coord" TODO: Next to
         * the label, add a TextField so that users can enter the coords assign
         * the TextFields to variables "xInput" and "yInput" The HBox inputBox
         * is already created for you to add the 2 labels and 2 textFields in
         * Make sure you add them to this HBox in the correct order
         */
        Label xlab = new Label("X-coord");
        Label ylab = new Label("Y-coord");
        
        xInput = new TextField();
        yInput = new TextField();
        
        inputBox.getChildren().addAll(xlab, xInput, ylab, yInput);

        /*
         * Add another textField to the VBox to display previous drawn command
         * assign the created TextField to variable called "previous" (already
         * created as intance variable) set the prompt as
         * "Previously entered command" and make sure the users are not able to
         * edit that field.
         */
        previous = new TextField();
        previous.setPromptText("Previously entered command");
        previous.setEditable(false);

        /*
         * add everything to the left VBox (which is passed as argument), add
         * the canvasContaner, the box(inputBox) containing all the TextFields
         * and the TextField which records the previous command
         */
        box.getChildren().addAll(canvasContainer, inputBox, previous);

    }

    /**
     * Add all the Gui elements to the right container
     * 
     * @param box
     *            the container to add the elements to
     */
    private void addRightSideComponents(VBox box) {

        /* Add some text to indicate what buttons are for */
        Text drawText = new Text("Draw Commands");
        drawText.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        drawText.setFill(Color.WHITE);
        box.getChildren().add(drawText);

        /*
         * add the following buttons to the VBox __________ |Rectangle| _______
         * |Circle| _________ |Triangle| ______ |House| ______ |Clear|
         * 
         * 
         * 
         */
        buttons = new Button[5]; // loop over this and add new Button to the
                                 // array

        // buttons[0] -> Rectangle button
        // button[1] -> Circle button
        // button[2] -> Triangle button
        // button[3] -> House button
        // button[5] -> Clear button

        // you can set a preferred size for all the button if you want

        //String[] labels = new String[5];
        String[] labels = {"Rectangle", "Circle", "Triangle", "House", "Clear"};
        for (int i = 0; i < 5; i++) {
        	buttons[i] = new Button(labels[i]);
        	buttons[i].setPrefWidth(100);
        }
        
        box.getChildren().addAll(buttons);

    }

}
