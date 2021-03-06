/*
 * 
 */
package jdcapp.data;

import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import jdcapp.gui.WorkspaceManager;

/**
 *
 * @author Noah
 */
public class CustomPoint extends CustomBox{
    
    public static final double DEFAULT_POINT_RADIUS = 4;
    public static final double DIAMOND_DISTRIBUTION = 4;
    public static final double DRAG_END_BUFFER = 10;
    
    //The type of point. Can be either square, diamond, arrow, or feathered arrow
    private String pointType;
    
    //The first and last points in any CustomConnection cannot be removed. All others can be.
    private boolean isRemovable;
    
    //The Group of all display elements in this point
    private Group display;
    
    public CustomPoint(double initX, double initY, String pointType, boolean isRemovable){
        super(initX, initY);
        this.isRemovable = isRemovable;
        this.pointType = pointType;
        display = new Group();
        toDisplay();
    }
    
    @Override
    public void toDisplay(){
        return;
    }
    
    public void toDisplay(double angle){
        display.getChildren().clear();
        
        if(pointType.equals(CustomConnection.DIAMOND_POINT_TYPE)){
            Polygon diamond = makeDiamond(angle);
            display.getChildren().add(diamond);
        }
        else if(pointType.equals(CustomConnection.ARROW_POINT_TYPE)){
            Polygon arrow = makeArrow(angle);
            display.getChildren().add(arrow);
        }
        else if(pointType.equals(CustomConnection.FEATHERED_ARROW_POINT_TYPE)){
            Polygon invisibleArrow = makeArrow(angle);
            invisibleArrow.setFill(Color.LIGHTGRAY);
            invisibleArrow.setStroke(Color.TRANSPARENT);
            display.getChildren().add(invisibleArrow);
            Polyline featheredArrow = makeFeatheredArrow(angle);
            display.getChildren().add(featheredArrow);
        }
        else{
            Circle pointCircle = new Circle();
            pointCircle.setRadius(DEFAULT_POINT_RADIUS);
            pointCircle.setCenterX(startX);
            pointCircle.setCenterY(startY);
            pointCircle.setFill(Color.TRANSPARENT);
            pointCircle.setStroke(Color.BLACK);
            pointCircle.setStrokeWidth(1.5);
            display.getChildren().add(pointCircle);
        }
    }
    
    public Polygon makeDiamond(double angle){
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[] {
            startX, startY,
            startX - (4 * Math.sin(angle) + 6 * Math.cos(angle)), startY + (4 * Math.cos(angle) - 6 * Math.sin(angle)),
            startX - (12 * Math.cos(angle)), startY + (-12 * Math.sin(angle)),
            startX - (-4 * Math.sin(angle) + 6 * Math.cos(angle)), startY + (-4 * Math.cos(angle) - 6 * Math.sin(angle)),
        });
        diamond.setFill(Color.BLACK);
        diamond.setStroke(Color.BLACK);
        diamond.setStrokeWidth(1);
        return diamond;
    }
    
    public Polygon makeArrow(double angle){
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[] {
           startX, startY,
           startX - (4 * Math.sin(angle) + 8 * Math.cos(angle)), startY + (4 * Math.cos(angle) - 8 * Math.sin(angle)),
           startX - (-4 * Math.sin(angle) + 8 * Math.cos(angle)), startY + (-4 * Math.cos(angle) - 8 * Math.sin(angle))
        });
        arrow.setFill(Color.BLACK);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(1);
        return arrow;
    }
    
    public Polyline makeFeatheredArrow(double angle){
        Polyline featheredArrow = new Polyline();
        featheredArrow.getPoints().addAll(new Double[] {
           startX - (4 * Math.sin(angle) + 8 * Math.cos(angle)), startY + (4 * Math.cos(angle) - 8 * Math.sin(angle)),
           startX, startY,
           startX - (-4 * Math.sin(angle) + 8 * Math.cos(angle)), startY + (-4 * Math.cos(angle) - 8 * Math.sin(angle))
        });
        featheredArrow.setFill(Color.TRANSPARENT);
        featheredArrow.setStroke(Color.BLACK);
        featheredArrow.setStrokeWidth(1);
        return featheredArrow;
    }

    @Override
    public Group getDisplay(){
        return display;
    }
    
    @Override
    public double getStartX(){
        return startX;
    }
    
    @Override
    public double getStartY(){
        return startY;
    }
    
    //This class returns null, as points have no text
    @Override
    public Text getNameText(){
        return null;
    }
    
    @Override
    public void highlight(Effect e){
        display.getChildren().get(0).setEffect(e);
    }
    
    @Override
    public Shape getOutlineShape(){
        return (Shape)display.getChildren().get(0);
    }
    
    public String getPointType(){
        return pointType;
    }
    
    public void setPointType(String type){
        pointType = type;
    }
    
    public boolean getIsRemovable(){
        return isRemovable;
    }
    
    public void reloadEndPoint(Rectangle oldBox, Rectangle newBox){
        if(startX == oldBox.getX() + oldBox.getWidth())
            startX = newBox.getX() + newBox.getWidth();
        if(startY == oldBox.getY() + oldBox.getHeight())
            startY = newBox.getY() + newBox.getHeight();
    }
    
    /**
     * Used when dragging a point attached to a CustomClass display rectangle.
     * Keeps point locked to rectangle edge.
     * @param x
     * @param y
     * @param r 
     */
    public void dragEnd(double x, double y, Rectangle r){
        
        //This will hopefully fix the issue that arises when the point's x or y gets shifted so
        //that it no longer falls exactly on the edge of the rectangle
        if(startX != r.getX() && startY != r.getY() && startX != r.getX() + r.getWidth() && startY != r.getY() + r.getHeight()){
            if(startX < r.getX() + DRAG_END_BUFFER && startX > r.getX() - DRAG_END_BUFFER)
                startX = r.getX();
            if(startX < r.getX() + r.getWidth() + DRAG_END_BUFFER && startX > r.getX() + r.getWidth() - DRAG_END_BUFFER )
                startX = r.getX() + r.getWidth();
            
            if(startY < r.getY() + DRAG_END_BUFFER && startY > r.getY() - DRAG_END_BUFFER)
                startY = r.getY();
            if(startY < r.getY() + r.getHeight() + DRAG_END_BUFFER && startY > r.getY() + r.getHeight() - DRAG_END_BUFFER )
                startY = r.getY() + r.getHeight();
        }
        
        //If the point is still off the edge of the rectangle, reset it.
        if(startX != r.getX() && startY != r.getY() && startX != r.getX() + r.getWidth() && startY != r.getY() + r.getHeight()){
            startX = r.getX();
            startY = r.getY();
        }
        
        //If the point is on one of the corners, determine which way to drag it
        if((startX == r.getX() || startX == r.getX() + r.getWidth()) && (startY == r.getY() || startY == r.getY() + r.getHeight())){
            if(Math.abs(x - dragX) > Math.abs(y - dragY)){
                if(startX + (x - dragX) < r.getX())
                    startX = r.getX();
                else if(startX + (x - dragX) > r.getX() + r.getWidth())
                    startX = r.getX() + r.getWidth();
                else
                    startX = startX + (x - dragX);
            }
            else{
                if(startY + (y - dragY) < r.getY())
                    startY = r.getY();
                else if(startY + (y - dragY) > r.getY() + r.getHeight())
                    startY = r.getY() + r.getHeight();
                else
                    startY = startY + (y - dragY);
            }
        }
        //If the point is on one of the horizontal edges, it can only be dragged horizontally
        else if(startX == r.getX() || startX == r.getX() + r.getWidth()){
            if(startY < r.getY())
                startY = r.getY();
            else if(startY > r.getY() + r.getHeight())
                startY = r.getY() + r.getHeight();
            else
                startY = startY + (y - dragY);
        }
        //If the point is on one of the vertical edges, it can only be dragged vertically
        else if(startY == r.getY() || startY == r.getY() + r.getHeight()){
            if(startX < r.getX())
                startX = r.getX();
            else if(startX > r.getX() + r.getWidth())
                startX = r.getX() + r.getWidth();
            else
                startX = startX + (x - dragX);
        }
        dragX = x;
        dragY = y;
    }
    
    /**
     * Used when dragging a point attached to a CustomClass display rectangle.
     * Keeps point locked to rectangle edge.
     * @param x
     * @param y
     * @param r 
     */
    public void dragSnappedEnd(double x, double y, Rectangle r){
        int gridshift = WorkspaceManager.GRID_BOX_SIZE;
        gridX = gridX + (x - dragX);
        gridY = gridY + (y - dragY);
        
        //This will hopefully fix the issue that arises when the point's x or y gets shifted so
        //that it no longer falls exactly on the edge of the rectangle
        if(startX != r.getX() && startY != r.getY() && startX != r.getX() + r.getWidth() && startY != r.getY() + r.getHeight()){
            if(startX < r.getX() + DRAG_END_BUFFER && startX > r.getX() - DRAG_END_BUFFER)
                startX = r.getX();
            if(startX < r.getX() + r.getWidth() + DRAG_END_BUFFER && startX > r.getX() + r.getWidth() - DRAG_END_BUFFER )
                startX = r.getX() + r.getWidth();
            
            if(startY < r.getY() + DRAG_END_BUFFER && startY > r.getY() - DRAG_END_BUFFER)
                startY = r.getY();
            if(startY < r.getY() + r.getHeight() + DRAG_END_BUFFER && startY > r.getY() + r.getHeight() - DRAG_END_BUFFER )
                startY = r.getY() + r.getHeight();
        }
        
        //If the point is still off the edge of the rectangle, reset it.
        if(startX != r.getX() && startY != r.getY() && startX != r.getX() + r.getWidth() && startY != r.getY() + r.getHeight()){
            startX = r.getX();
            startY = r.getY();
        }
        
        //If the point is on one of the corners, determine which way to drag it
        if((startX == r.getX() || startX == r.getX() + r.getWidth()) && (startY == r.getY() || startY == r.getY() + r.getHeight())){
            if(Math.abs(x - dragX) > Math.abs(y - dragY)){
                if(gridX < r.getX())
                    startX = r.getX();
                else if(gridX > r.getX() + r.getWidth())
                    startX = r.getX() + r.getWidth();
                else if(gridX % gridshift > (gridshift / 2))
                    startX = gridX + (gridshift - (gridX % gridshift));
                else
                    startX = gridX - (gridX % gridshift);
            }
            else{
                if(gridY < r.getY())
                    startY = r.getY();
                else if(gridY > r.getY() + r.getHeight())
                    startY = r.getY() + r.getHeight();
                else if(gridY % gridshift > (gridshift / 2))
                    startY = gridY + (gridshift - (gridY % gridshift));
                else
                    startY = gridY - (gridY % gridshift);
            }
        }
        //If the point is on one of the horizontal edges, it can only be dragged horizontally
        else if(startX == r.getX() || startX == r.getX() + r.getWidth()){
            if(startY < r.getY())
                startY = r.getY();
            else if(startY > r.getY() + r.getHeight())
                startY = r.getY() + r.getHeight();
            else if(gridY % gridshift > (gridshift / 2))
                startY = gridY + (gridshift - (gridY % gridshift));
            else
                startY = gridY - (gridY % gridshift);
        }
        //If the point is on one of the vertical edges, it can only be dragged vertically
        else if(startY == r.getY() || startY == r.getY() + r.getHeight()){
            if(startX < r.getX())
                startX = r.getX();
            else if(startX > r.getX() + r.getWidth())
                startX = r.getX() + r.getWidth();
            if(gridX % gridshift > (gridshift / 2))
                startX = gridX + (gridshift - (gridX % gridshift));
            else
                startX = gridX - (gridX % gridshift);
        }
        dragX = x;
        dragY = y;
    }
}
