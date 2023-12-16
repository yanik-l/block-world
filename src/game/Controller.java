package game;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The controller class that will interact with the user and update information
 * about the map and on the GUI
 * 
 * @author Yanik
 *
 */
public class Controller {
	
	//the view for the canvas application
    private View view;
    //the world map that will be loaded, modified and saved
    private WorldMap wm;
    //a simple check to see if the map has been loaded before performing actions
    private boolean mapLoaded;
    
    
    /**
     * Create a new Controller for the given view and add the button and drop-
     * down menu handlers
     */
    public Controller(View view) {
        this.view = view;
        view.addButtonHandler(new DrawHandler());
        view.addFileMenuHandler(new FileMenuHandler());
        view.addMoveComboBoxHandler(new MoveComboBoxHandler());
        
    }
    
    
    /**
     * EventHandler class which deals with user inputs on the buttons in the 
     * view
     */
    private class DrawHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            /* Get the button which was just pressed */
            Button pressedButton = (Button) event.getSource();
            /* Gets the name of that button */
            String pressedButtonName = pressedButton.getText();
            
          //if the user pressed the Dig button and if a map has been loaded
            if (pressedButtonName == "Dig" && mapLoaded == true) {
            	try {
            		//try to dig on current tile and then update the GUIMap
					wm.getBuilder().digOnCurrentTile();
					updateGUIMap("update");
				//process various errors and display alert boxes
				} catch (TooLowException e) {
					AlertBoxCreator(AlertType.WARNING, "Dig Action", 
							"Digging too low", "The builder cannot dig on a "
									+ "block this low");
				} catch (InvalidBlockException e) {
					AlertBoxCreator(AlertType.WARNING, "Dig Action", 
							"Block cannot be dug", "The builder cannot dig on "
									+ "a block of this type");
				}
            //if the user pressed the Drop button and if a map has been loaded
            } else if (pressedButtonName == "Drop" && mapLoaded == true) {
            	
            	//retrieve the index that was entered into the Drop field
            	String dropIndexStr = view.getDropIndex();
            	//turn it into an integer
            	int dropIndex = Integer.parseInt(dropIndexStr);
            	try {
            		/* try to drop the item from the builders inventory and 
            		 * then update the GUIMap */
					wm.getBuilder().dropFromInventory(dropIndex);
					updateGUIMap("update");
					//process various errors and display alert boxes
				} catch (InvalidBlockException e) {
					AlertBoxCreator(AlertType.WARNING, "Drop Action", 
							"Item cannot be dropped", "The inventoryIndex is "
									+ "out of the inventory index range");
				} catch (TooHighException e) {
					AlertBoxCreator(AlertType.WARNING, "Drop Action", 
							"Item cannot be dropped", "There are 8 blocks on "
									+ "the current tile already, or the block "
									+ "is an instance of GroundBlock and there"
									+ " are already 3 or more blocks on the "
									+ "current tile.");
				//squash the TooLowException since it should not be thrown
				} catch (TooLowException e) {} 
            } else if (pressedButtonName == "North" || pressedButtonName == 
            		"East" || pressedButtonName == "South" || 
            		pressedButtonName == "West") {
            	//unfinished code
            	
            /* if a map has not already been loaded, prompt the user with an 
             * alert box */
            } else if (mapLoaded == false) {
            	AlertBoxCreator(AlertType.ERROR, "Action Error", 
						"No map loaded", "An action cannot be performed"
								+ " before a map has been loaded. Load the map"
								+ " first and then perform actions on it");
            }
        }
    }
    
    
    /**
     * EventHandler class which deals with user inputs on the file drop-down 
     * menu in the view
     */
    private class FileMenuHandler implements EventHandler<ActionEvent> {

    	//create a stage for the file chooser when loading and saving
    	private Stage st;
    	//create the file chooser
    	private FileChooser fileChooser = new FileChooser();
    	
		@Override
		public void handle(ActionEvent event) {
			/* Get the item which was just selected from the drop down bar and 
			 * turn it into a string*/
			ComboBox<?> selected = (ComboBox<?>) event.getSource();
        	String selectedOption = (String) selected.getValue();
            
            if (selectedOption == "Load Game World") {
            	File file = fileChooser.showOpenDialog(st);
            	if (file != null) {
            		try {
            			//first clear the canvas of any existing maps
            			view.clearCanvas();
            			//create a new WorldMap from the selected file
						wm = new WorldMap(file.getAbsolutePath());
						//use the helper method to load the GUI
						updateGUIMap("load");
						/* let the system and user know that a map has been 
						 * loaded now */
						mapLoaded = true;
						AlertBoxCreator(AlertType.CONFIRMATION, "File Loaded", 
								"Map Loaded", "The World Map has been loaded"
										+ " successfully");
					//process various errors and display alert boxes
					} catch (WorldMapFormatException e) {
						AlertBoxCreator(AlertType.ERROR, "File Error", 
								"Load Map Error", "A World Map file contains "
										+ "the wrong format");
					} catch (WorldMapInconsistentException e) {
						AlertBoxCreator(AlertType.ERROR, "File Error", 
								"Load Map Error", "A World Map file is "
										+ "geometrically inconsistent.");
					} catch (FileNotFoundException e) {
						AlertBoxCreator(AlertType.ERROR, "File Error", 
								"Load Map Error", "A World Map file is "
										+ "geometrically inconsistent.");
					} catch (TooLowException e) {
						AlertBoxCreator(AlertType.ERROR, "File Error", 
								"Load Map Error", "The specified pathname"
										+ "does not exist or the file is "
										+ "inaccessible");
					}
            		
					
            	}
            } else if (selectedOption == "Save World Map") {
            	File file = fileChooser.showSaveDialog(st);
            	if (file != null) {
            		try {
            			/* try to save the map using the WorldMap function and 
            			 * prompt the user if it was successful */
						wm.saveMap(file.getAbsolutePath());
						AlertBoxCreator(AlertType.CONFIRMATION, "File Saved", 
								"World Map Saved", "The world map has been"
										+ " saved successfully to your "
										+ "specified destination");
					//process various errors and display alert boxes
					} catch (IOException e) {
						AlertBoxCreator(AlertType.ERROR, "File Error", 
								"Save Map Error", "The file cannot be opened "
										+ "or written to");
					} catch (NullPointerException e) {
						AlertBoxCreator(AlertType.ERROR, "File Error", 
								"Save Map Error", "You must first load a map "
										+ "in order to save it");
					}
            	}
            }
		}
    }
    
    /**
     * EventHandler class which deals with user inputs on the move drop-down 
     * menu in the view
     */
    private class MoveComboBoxHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
        	
        	/* Get the item which was just selected from the drop down bar and 
			 * turn it into a string*/
        	ComboBox<?> selected = (ComboBox<?>) event.getSource();
        	String selectedOption = (String) selected.getValue();

        	//incomplete code
        	if (selectedOption == "Move Builder") {
            	System.out.println("Do Something");
            } else if (selectedOption == "Move Block"){
            	System.out.println("Do Something Else");
            }
        }  
    }
    
    private void updateGUIMap(String option) throws TooLowException {
    	
    	/* these will be the x and y values that will be used to shift the 
    	 * blocks to the centre of the canvas by zero'ing their start position 
    	 * and using their relative position */
    	int xShift = wm.getStartPosition().getX();
    	int yShift = wm.getStartPosition().getY();
    	
    	/* can update the GUI in the same way if either the GUI is first 
    	 * loaded or an update on the map is performed */
    	if (option == "load" || option == "update") {
    		int mapDimension = 9;
    		/* these two loops iterate through each possible position on the 
    		 * map given a certain map dimension. In the case of this 
    		 * assignment only a 9x9 block map had to be considered so 
    		 * mapDimension was set to 9
    		 */
    		for (int i = 0; i < mapDimension; i++) {
        		for (int j = 0; j < mapDimension; j++) {
        			Tile tileIterate = wm.getTile(new Position(i, j));
        			// checks to see if there is a tile at the given position
        			if (tileIterate != null) {
        				//finds the colour of the top block
        				String blockColour = tileIterate.getTopBlock()
        						.getColour();
        				//draws the block on the canvas at the given position
        				view.drawShape("Rectangle", i - xShift, j - yShift, 
        						blockColour, "", 0);
        				//finds the set of tile exits
        				Set<String> tileSet = tileIterate.getExits().keySet();
        				String[] tileArray = tileSet.toArray(new String[0]);
        				/* uses the array of tile exits to draw each exit on
        				 * the tile */
        				for (int k = 0; k < tileArray.length; k++) {
        					view.drawShape("Triangle", i - xShift, j - yShift, 
            						"", tileArray[k], 0);
        				}
        				/* checks if the builder is on this tile and if the 
        				 * builders current tile is this tile, it will draw a 
        				 * yellow circle to represent it */
        				if (wm.getBuilder().getCurrentTile() == tileIterate) {
        					view.drawShape("Circle", i - xShift, j - yShift, 
        							"", "", 0);
        				}
        				/*
        				 * Below is clode that would've let me add the number
        				 * of blocks on each tile but as discussed in 
        				 * view.drawNumber, there was an error with it
        				 */
        				//int numberOfBlocks = tileIterate.getBlocks().size();
        				//view.drawShape("Number", i - xShift, j - yShift, "", 
        						//"", numberOfBlocks);
        			}
        		}
        	}
    		//update the builders inventory and display it on the GUI
    		ArrayList<String> inventoryList = new ArrayList<String>();
			for (Block element : wm.getBuilder().getInventory()) {
				inventoryList.add(element.getBlockType());
			}
			view.updateInventoryLabelBox(inventoryList);
    	}
    }
    
    
    /**
     * Simple helper method to create alert boxes
     * 
     * @param at - the type of alert that you want to prompt the user with
     * @param title - title of the alert box
     * @param header - header of the alert box
     * @param description - description of the alert box
     */
    private void AlertBoxCreator(AlertType at, String title, String header, 
    		String description) {
    	Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.showAndWait();
    }
}
