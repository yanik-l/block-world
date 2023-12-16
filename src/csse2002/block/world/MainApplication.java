package csse2002.block.world;

import javafx.application.Application;
import javafx.stage.Stage;
import csse2002.block.world.View;

public class MainApplication extends Application {
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Block World");
		
        
        View view = new View();
        
        /* Create a new controller 
         * Do this after all the code in View has been finished
         */
        new Controller(view);
        
        primaryStage.setScene(view.getScene());
        primaryStage.show(); 

	}

}
