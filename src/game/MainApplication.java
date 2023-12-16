package game;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main application that will launch the Block World GUI
 * 
 * @author Yanik
 *
 */
public class MainApplication extends Application {
	
	/**
	 * Main method that will call for the GUI to be launched
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * This method will create the view and controller and open the actual
	 * window of the GUI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Block World");
		
        //Create a new view that will be fed to the controller
        View view = new View();
        
        // Create a new controller 
        new Controller(view);
        
        primaryStage.setScene(view.getScene());
        primaryStage.show(); 
	}
}
