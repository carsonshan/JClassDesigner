/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Contains a reference to a CustomClass object as data, and holds all requisite
 * values needed to generate the display for that CustomClass object in the workspace.
 * @author Noah
 */
public class CustomClassWrapper extends Group{

    static final double DEFAULT_WRAPPING_WIDTH = 200;
    static double TEXT_LINE_PIXEL_HEIGHT = 12;
    
    //The data contained within this wrapper class
    private CustomClass data;

    //The starting locations from which the CustomClass will be drawn
    private double startX;
    private double startY;
    
    //The width and height of the CustomClass
    private double width;
    private double height;
    
    //The wrapping width, to be used with the toDisplay method (will be set when class is resized)
    private double wrappingWidth;
    
    //The text font, to be used with the toDisplay method (lets us resize font)
    private Font textFont;
    
    //The HashMap containing lists of points on the lines connecting this class and its parents
    private HashMap<String, ArrayList<Point2D>> points;
    
    public CustomClassWrapper(double initX, double initY, boolean isInterface){
        super();
        data = new CustomClass(isInterface);
        startX = initX;
        startY = initY;
        wrappingWidth = DEFAULT_WRAPPING_WIDTH;
        textFont = Font.font("sans-serif", FontWeight.NORMAL, TEXT_LINE_PIXEL_HEIGHT);
        toDisplay();
    }
    
    /**
     * Constructs requisite text objects and rectangles for the display of the CustomClass
     * object in the workspace.
     */
    public void toDisplay(){
        //Call clear first, to remove any old display objects from the Group
        super.getChildren().clear();
        
        Rectangle outline = new Rectangle();
        Text nameText = new Text();
        Text varsText = new Text();
        Text methodsText = new Text();
        
        //Set the id of the Text objects so that the text is formatted by the CSS
        nameText.setFont(textFont);
        varsText.setFont(textFont);
        methodsText.setFont(textFont);
        
        //Create the name text display
        //TODO: Edit this slightly to take into account interface displaying
        nameText.setText(data.getClassName());
        nameText.setX(startX);
        nameText.setY(startY + TEXT_LINE_PIXEL_HEIGHT);
        nameText.setWrappingWidth(wrappingWidth);
        int numLinesName = 2;
        
        //Create the rectangle surrounding the name text
        Rectangle nameOutline = new Rectangle(startX, startY, wrappingWidth, 
                (numLinesName) * TEXT_LINE_PIXEL_HEIGHT);
        nameOutline.setStroke(Color.BLACK);
        nameOutline.setStrokeWidth(1);
        nameOutline.setFill(Color.WHITE);
        
        //Create the variable text display
        String vars = "";
        int numLinesVars = 2;
        if(!data.getVariables().isEmpty()){
            numLinesVars = 1;
            //vars = "";
            for(CustomVar v : data.getVariables()){
                vars += v.getVarName() + "\n";
                numLinesVars++;
            }
        }
        varsText.setText(vars);
        varsText.setX(startX);
        varsText.setY(startY + ((numLinesName + 1) * TEXT_LINE_PIXEL_HEIGHT));
        varsText.setWrappingWidth(wrappingWidth);
        
        //Create the rectangle surrounding the variable text
        Rectangle varsOutline = new Rectangle(startX, startY + (numLinesName * TEXT_LINE_PIXEL_HEIGHT), 
                wrappingWidth, (numLinesVars) * TEXT_LINE_PIXEL_HEIGHT);
        varsOutline.setStroke(Color.BLACK);
        varsOutline.setStrokeWidth(1);
        varsOutline.setFill(Color.WHITE);
        
        //Create the method text display
        String methods = "";
        int numLinesMethods = 2;
        if(!data.getMethods().isEmpty()){
            numLinesMethods = 1;
            //methods = "";
            for(CustomMethod m : data.getMethods()){
                methods += m.getMethodName() + "\n";
                numLinesMethods++;
            }
        }
        methodsText.setText(methods);
        methodsText.setX(startX);
        methodsText.setY(startY + ((numLinesName + numLinesVars + 1) * TEXT_LINE_PIXEL_HEIGHT));
        methodsText.setWrappingWidth(wrappingWidth);
        
        //Create the rectangle surrounding the method text
        Rectangle methodsOutline = new Rectangle(startX, startY + ((numLinesName + numLinesVars) * TEXT_LINE_PIXEL_HEIGHT), 
                wrappingWidth, (numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        methodsOutline.setStroke(Color.BLACK);
        methodsOutline.setStrokeWidth(1);
        methodsOutline.setFill(Color.WHITE);
        
        //Create the overlaying rectangle
        outline.setX(startX);
        outline.setY(startY);
        outline.setWidth(wrappingWidth);
        outline.setHeight((numLinesName + numLinesVars + numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(1);
        outline.setFill(Color.WHITE);
        
        //Set the width and height of the CustomClass to the width and height of the overlaying rectangle
        setWidth(wrappingWidth);
        setHeight((numLinesName + numLinesVars + numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        
        super.getChildren().add(outline);
        super.getChildren().add(nameOutline);
        super.getChildren().add(varsOutline);
        super.getChildren().add(methodsOutline);
        super.getChildren().add(nameText);
        super.getChildren().add(varsText);
        super.getChildren().add(methodsText);
    }
    
    public Rectangle getOutlineRectangle(){
        return (Rectangle)super.getChildren().get(0);
    }
    
    public Text getNameText(){
        return (Text)super.getChildren().get(4);
    }
    
    public Text getVarsText(){
        return (Text)super.getChildren().get(5);
    }
    
    public Text getMethodsText(){
        return (Text)super.getChildren().get(6);
    }
    
    public CustomClass getData(){
        return data;
    }
    
    public double getStartX() { return startX; }

    public void setStartX(double startX) { this.startX = startX; }

    public double getStartY() { return startY; }

    public void setStartY(double startY) { this.startY = startY; }

    public double getWidth() { return width; }

    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }

    public void setHeight(double height) { this.height = height; }

    public HashMap<String, ArrayList<Point2D>> getPoints() {
        return points;
    }

    public void setPoints(HashMap<String, ArrayList<Point2D>> points) {
        this.points = points;
    }

    private double getWrappingWidth() {
        return wrappingWidth;
    }
    
    public void setWrappingWidth(double w){
        wrappingWidth = w;
    }
    
        
    //TODO: Finish coding this class
}
