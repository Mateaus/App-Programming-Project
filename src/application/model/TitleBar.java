package application.model;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class TitleBar {
	
	/*
	 * x and y values utilized to get our x and y axis location of our titlebar.
	 */
	private static double x = 0;
	private static double y = 0;
	
	/*
     *  The methods below are used for the only purpose of controlling the customized created
     *  toolbar.
     *
     *  toolbarDragging methods helps us move the scene by dragging on the toolbar area.
     */
	
	public static void toolbarDragging(MouseEvent mouseEvent) {
		Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
	}
	
	/*
     * toolbarPressed gets us the x and y values to then be used on toolbarDragging
     * as we click on the toolbar location.
     */
	
	public static void toolbarPressed(MouseEvent mouseEvent) {
		x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
	}
	
	/**
     * toolbarMinimize is utilized to minimize the screen.
     */

    public static void toolbarMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * toolbarExit is utilized to exit the screen.
     */

    public static void toolbarExit(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
