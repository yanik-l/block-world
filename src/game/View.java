package game;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The View class will set up the entire GUI window that will later be used
 * and called upon by the Controller class in order to do something with the
 * basic GUI window
 * 
 * @author Yanik
 *
 */
public class View {
	/* The root node of the scene graph, to add all the GUI elements to */
    private HBox rootBox;
    /* This is used to issue draw calls to a Canvas */
    private GraphicsContext context;

    /*
     * Action command buttons ["North", "West", "East", "South", "Dig", "Drop]
     */
    private Button[] buttons;

    /*
     * Text field to enter the index of the item that the user would like to
     * drop from the builders inventory
     */
    private TextField dropIndexField;
    /*
     * Label that displays the builders inventory
     */
    private Label inventoryLabel;
    /*
     * The HBox and VBox are used to build the GUI window into the correct 
     * structure
     */
    private VBox leftBox, rightBox;
    private HBox inventoryBox;
    /*
     * The two drop-down menus, moveMenu letting the user select from either 
     * moving a block or the builder and fileMenu letting the user either load
     * or save a map
     */
    private ComboBox<String> moveMenu, fileMenu;

    
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
     * Get the number entered into the dropIndexField
     * 
     * @return the string of the entered index to drop from the builders 
     * inventory
     */
    public String getDropIndex() {
        return dropIndexField.getText();
    }

    
    /**
     * A small helper method to make it easier later to be able to change the
     * canvas size and use its size to make calculations
     * 
     * @return the size of the canvas
     */
    public int getCanvasSize() {
    	int squareSize = 450;
    	return squareSize;
    }
    

    /**
     * Draws the shape with some colour, direction and number at given x and y 
     * coordinates. The colour is only used to draw the correct colour of the 
     * rectangle (representing the top block). The direction is only used to
     * know the direction of the exit from the tile. The number is used to
     * display how many blocks are on the tile
     * 
     * @param shape
     *            the shape to draw
     * @param x
     *            the X coordinate of the shape
     * @param y
     *            the Y coordinate of the shape
     * @param colour
     *            the colour of the block
     * @param direction
     *            the direction of the exit
     * @param number
     *            the number of blocks on the tile         
     */
    public void drawShape(String shape, int x, int y, String colour, String
    		direction, int number) {
        switch (shape) {
            case "Rectangle" :
                drawRectangle(x, y, colour);
                break;
            case "Circle" :
                drawCircle(x, y);
                break;
            case "Triangle" :
                drawTriangle(x, y, direction);
                break;
            case "Number" :
            	drawNumber(x, y, number);
            case "Clear" :
                clearCanvas();
                break;
        }
    }
    

    /**
     * Draws a rectangle at the given x and y coordinates with the given 
     * colour. The colour can be one of either BROWN, GREEN, BLACK or GREY
     * 
     * @param x
     *            the upper left X coordinate of the rectangle
     * @param y
     *            the upper left Y coordinate of the rectangle
     * @param colour
     *            the colour of the block
     */
    private void drawRectangle(int x, int y, String colour) {
        /* The integer that will be fed into the x and y variables will be on
         * a smaller scale and non centred in the middle of the canvas. For 
         * example the starting block may have a position of (1,2) and the 
         * tile next to it may have a position of (1,3). Before this method is
         * called, the position is normalised to (0,0) for the starting block 
         * and so the tile next to it will now have a position of (0,1). This 
         * is then rescaled to a larger size and then centred on the canvas*/
    	x = (50*x) + 200;
    	y = (50*y) + 200;
    	if (colour == "brown") {
    		context.setFill(Color.BROWN);
        	context.fillRect(x, y, 50, 50);
    	} else if (colour == "green") {
    		context.setFill(Color.GREEN);
        	context.fillRect(x, y, 50, 50);
    	} else if (colour == "black") {
    		context.setFill(Color.BLACK);
        	context.fillRect(x, y, 50, 50);
    	} else if (colour == "gray") {
    		context.setFill(Color.GREY);
        	context.fillRect(x, y, 50, 50);
    	}
    }
    

    /**
     * Draws a yellow circle with a radius of 8 and top left at (x,y)
     * 
     * @param x
     *            the X coordinate of the upper left bound of the circle.
     * @param y
     *            the Y coordinate of the upper left bound of the circle.
     */
    private void drawCircle(int x, int y) {
        /* As explained in the drawRectangle method, this is just to rescale
         * and centre the circle in the middle of the canvas. Read 
         * drawRectangle for more information on it
         */
    	x = (50*x) + 220;
    	y = (50*y) + 232;
    	context.setFill(Color.YELLOW);
    	context.fillOval(x, y, 8, 8);
    }

    
    /**
     * Draws a white triangle at the given (x,y) coordinates pointing in the
     * given direction
     * 
     * @param x
     *            the x coordinate to start drawing the triangle
     * @param y
     *            the y coordinate to start drawing the triangle
     * @param direction
     *            the y coordinate to start drawing the triangle
     */
    private void drawTriangle(int x, int y, String direction) {
        /* Draw a triangle at the given x and y coords */
    	int npoints = 3;
    	/* Used to rescale and centre the triangle in the middle of the canvas.
    	 * Read drawRectangle for more information on this*/
    	x = (50*x) + 218;
    	y = (50*y) + 208;
        context.setFill(Color.WHITE);
    	if (direction.equals("north")) {
    		double xpoints[] = {x, x+6, x+12};
        	double ypoints[] = {y, y-6, y};
            context.fillPolygon(xpoints, ypoints, npoints);
    	} else if (direction.equals("east")) {
    		/* These specific values used for the x and y coordinates were 
    		 * mostly found using a bit of trial and error in order for them
    		 * to appear in the exact right position on the block*/
    		x = x + 24;
            y = y + 22;
    		double xpoints[] = {x, x+6, x};
        	double ypoints[] = {y, y-6, y-12};
            context.fillPolygon(xpoints, ypoints, npoints);
    	} else if (direction.equals("south")) {
    		y = y + 34;
    		double xpoints[] = {x, x+6, x+12};
        	double ypoints[] = {y, y+6, y};
            context.fillPolygon(xpoints, ypoints, npoints);
    	} else if (direction.equals("west")) {
    		x = x - 10;
            y = y + 22;
    		double xpoints[] = {x, x-6, x};
        	double ypoints[] = {y, y-6, y-12};
            context.fillPolygon(xpoints, ypoints, npoints);
    	}
    }
    
    
    /**
     * Is supposed to draw a number on the middle of the block but for some 
     * unknown reason the entire canvas turns blank white when this method is 
     * used and no block map can be seen on the canvas anymore. For this reason
     * the method was written out, but not used in the final GUI
     * 
     * @param x
     *            the x coordinate to place the number
     * @param y
     *            the y coordinate to place the number
     * @param number
     *            the number that will be placed on the canvas
     */
    private void drawNumber(int x, int y, int number) {
    	x = (50*x) + 220;
    	y = (50*y) + 230;
    	String numberStr = Integer.toString(number);
    	context.setFill(Color.WHITE);
    	context.fillText(numberStr, x, y);
    }


    /**
     * Clears the whole canvas and so removes all of the shapes on it
     */
    public void clearCanvas() {
        /* Remove all the shapes drawn on the canvas */
        context.clearRect(0, 0, context.getCanvas().getWidth(), context.
        		getCanvas().getHeight());
    }

    
    /**
     * Clears all the text fields
     */
    public void clearFields() {
        /*
         * Clear the dropIndexField so that users don't always have to clear
         * after entering a command
         */
        dropIndexField.clear();
    }
    
    
    /**
     * Adds the given handler to each button
     * 
     * @param handler
     *            the handler to add to the button
     */
    public void addButtonHandler(EventHandler<ActionEvent> handler) {
        /* Adds a handler to the setOnAction */
        for (Button b : buttons) {
            b.setOnAction(handler);
        }
    }
    
    
    /**
     * Adds the given handler to the drop-down file menu
     * 
     * @param handler
     *            the handler to add to the drop-down file menu
     */
    public void addFileMenuHandler(EventHandler<ActionEvent> handler) {
    	/* Adds a handler to the setOnAction */
    	fileMenu.setOnAction(handler);
    }
    
    
    /**
     * Adds the given handler to the move builder/block drop-down menu
     * 
     * @param handler
     *            the handler to add to the drop-down move menu
     */
    public void addMoveComboBoxHandler(EventHandler<ActionEvent> handler) {
    	/* Adds a handler to the setOnAction */
    	moveMenu.setOnAction(handler);
    }
    
    
    /**
     * Takes in the builders inventory as an ArrayList and updates the 
     * inventory label to display the updated builders inventory
     * 
     * @param inventoryList
     *            the builders inventory passed to the function as an ArrayList
     */
    public void updateInventoryLabelBox(ArrayList<String> inventoryList) {
    	inventoryLabel = new Label(inventoryList.toString());
    	inventoryBox.getChildren().clear();
    	inventoryBox.getChildren().add(inventoryLabel);
    }
    

    /**
     * Adds all the GUI elements to the root layout
     */
    private void addComponents() {
        leftBox = new VBox();

        /* Add padding, colour to the left side */
        leftBox.setPadding(new Insets(10, 10, 10, 10));
        leftBox.setStyle("-fx-background-color: white");

        /* Add all the leftside components to this leftBox. This method builds 
         * the left side of the GUI*/
        addLeftSideComponents(leftBox);

        /* Another layout node for the left side of the GUI */
        rightBox = new VBox();

        /* Add blue colour and padding to the right layout */
        rightBox.setSpacing(10);
        rightBox.setPadding(new Insets(20, 50, 20, 50));
        rightBox.setStyle("-fx-background-color: #336699");

        /* Add all the right side components to this rightBox. This method 
         * builds the right side of the GUI*/
        addRightSideComponents(rightBox);

        /* Add both layouts to the root HBox layout */
        rootBox.getChildren().add(leftBox);
        rootBox.getChildren().add(rightBox);
    }

    /**
     * Add all the GUI elements to the left container
     * 
     * @param Vbox
     *            the container to add the elements to
     */
    private void addLeftSideComponents(VBox box) {

        /*
         * This is where all the components which are on the left side such as
         * Canvas, TextFields, HBox, ComboBox and Labels will be added
         */
    	
    	//Creating the drop-down file menu
    	fileMenu = new ComboBox<String>();
    	fileMenu.getItems().addAll("Load Game World", "Save World Map");
    	fileMenu.setPromptText("File");

        /*
         * adding the canvas inside a HBox (canvasContainer) is used so
         * that a border can be added around the canvas
         */
        HBox canvasContainer = new HBox();
        Canvas canvas = new Canvas(getCanvasSize(), getCanvasSize());
        context = canvas.getGraphicsContext2D();
        canvasContainer.getChildren().add(canvas);
        canvasContainer.setStyle("-fx-border-color: black");

        /* Create another HBox to add the inventoryLabel inside it */
        HBox inventoryLabelBox = new HBox();
        inventoryLabelBox.setPadding(new Insets(10, 10, 10, 10));
        inventoryLabelBox.setSpacing(15);
        /* Make everything inside the HBox left aligned */
        inventoryLabelBox.setAlignment(Pos.TOP_LEFT);

        /* Add the inventory labels */
        Label inventoryTitleLabel = new Label("Builder Inventory:");
        inventoryLabelBox.getChildren().add(inventoryTitleLabel);
        
        inventoryBox = new HBox();
        inventoryBox.setPadding(new Insets(0, 10, 10, 10));
        inventoryBox.setSpacing(15);
        inventoryBox.setAlignment(Pos.TOP_LEFT);

        inventoryLabel = new Label("[Load map to show inventory]");
        inventoryBox.getChildren().add(inventoryLabel);

        /*
         * Add everything to the left VBox (box). Add the fileMenu, 
         * canvasContaner, inventoryLabelBox and the inventoryBox
         */
        box.getChildren().addAll(fileMenu, canvasContainer, inventoryLabelBox, 
        		inventoryBox);
    }

    
    /**
     * Add all the GUI elements to the right container
     * 
     * @param box
     *            the container to add the elements to
     */
    private void addRightSideComponents(VBox box) {

        /* Add some white text to label the buttons */
        Text drawText = new Text("Action Commands");
        drawText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        drawText.setFill(Color.WHITE);
        box.getChildren().add(drawText);

        buttons = new Button[6];
        // button[0] -> North button
        // button[1] -> West button
        // button[2] -> East button
        // button[3] -> South button
        // button[4] -> Dig button
        // button[5] -> Drop button
        String[] labels = {"North", "West", "East", "South", "Dig", "Drop"};
        //each button is now created
        for (int i = 0; i < 6; i++) {
        	buttons[i] = new Button(labels[i]);
        	buttons[i].setPrefWidth(100);
        	buttons[i].setPrefHeight(40);
        }
        
        /* 
         * The field to enter the index of the builders inventory you will 
         * drop from
         */
        dropIndexField = new TextField();
        dropIndexField.setPromptText("Drop Index");
        dropIndexField.setMinSize(100, 40);
        
        /*
         * second drop-down menu that will let you select between move builder
         * and move block
         */
        moveMenu = new ComboBox<String>();
        moveMenu.getItems().addAll("Move Builder", "Move Block");
        
        //all of this is just to get the NESW style direction layout
        HBox buttonContainer1 = new HBox();
        HBox buttonContainer2 = new HBox();
        HBox buttonContainer3 = new HBox();
        HBox buttonContainer4 = new HBox();
        HBox buttonContainer5 = new HBox();
        HBox buttonContainer6 = new HBox();
        
        buttonContainer1.setAlignment(Pos.TOP_CENTER);
        buttonContainer2.setAlignment(Pos.TOP_CENTER);
        buttonContainer3.setAlignment(Pos.TOP_CENTER);
        buttonContainer4.setAlignment(Pos.TOP_CENTER);
        buttonContainer5.setAlignment(Pos.TOP_LEFT);
        buttonContainer6.setAlignment(Pos.TOP_LEFT);
        
        buttonContainer1.getChildren().add(buttons[0]);
        buttonContainer2.getChildren().addAll(buttons[1], buttons[2]);
        buttonContainer3.getChildren().add(buttons[3]);
        buttonContainer4.getChildren().add(moveMenu);
        buttonContainer5.getChildren().add(buttons[4]);
        buttonContainer6.getChildren().addAll(buttons[5], dropIndexField);
        
        buttonContainer2.setSpacing(100);
        buttonContainer5.setSpacing(10);
        
        //the box that will hold the North, East, South, West buttons
        VBox directionBox = new VBox();
        
        directionBox.getChildren().addAll(buttonContainer1, buttonContainer2, 
        		buttonContainer3);
        /*
         * adding the NESW buttons and the drop-down move menu along with the
         * dig and drop buttons (plus drop index field)
         */
        box.getChildren().addAll(directionBox, buttonContainer4, buttonContainer5, 
        		buttonContainer6);
        
        directionBox.setSpacing(0);
        box.setSpacing(20);
    }
}
