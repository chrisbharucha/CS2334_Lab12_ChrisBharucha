import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Panel in which drawing occurs.
 * 
 * @author CS2334. Modified by: ?????
 * @version 2018-04-16
 */

public class DrawPanel extends JPanel
{
    /**
     * Makes Eclipse happy
     */
    private static final long serialVersionUID = 1L;
    /** The list of shapes to be drawn. */
    private ArrayList<Shape> shapeList = new ArrayList<Shape>();
    /** A temporary shape that is also drawn */
    private Shape tempShape;
    /** Index of chosen shape being edited */
    private int shapeIndex;

    /** Coordinates for button-down event. */
    private int x0;
    private int y0;
    /** Coordinates for button-up event. */
    private int x1;
    private int y1;
    /** Indicate if a shape is currently being drawn. */
    private boolean drawingFlag;

    /** Reference to the frame in which the panel is a member. */
    private DrawFrame frame;

    //////////////////////////////////////////////////////////////
    /**
     * Class defines the behavior inside of the drawing panel in response to
     * mouse events
     * 
     */
    public class MouseHandler extends MouseAdapter
    {
        /**
         * Respond to a button-down event.
         * 
         * @param e Mouse event
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            // Remember this starting coordinate (look at documentation for mouse events)
            // You can store these in x0 and y0
            // TODO
        	x0 = e.getX();
        	y0 = e.getY();
        	
            // are we in edit mode?
            if (frame.isEditing())
            {
                // create point where the mouse was clicked
                // TODO
            	Point mouseClick = new Point(x0,y0);
            	
                // find which shape was clicked
                // loop through shapes in stack fashion, LIFO
                // TODO
                for (int i = shapeList.size() - 1; i >= 0; --i)
                {
                	if (shapeList.get(i).contains(mouseClick))
                    {
                        // if the shape contains the point, set the shapeIndex
                        // to be the index in the shapeList
                        shapeIndex = i;
                        tempShape = shapeList.get(shapeIndex);

                        // TODO: find if the shape is filled
                        // TODO: set fillBox to match the status of the shape
                       if (tempShape.isFilled()) {
                    	   frame.controlPanel.fillBox.setEnabled(true);
                       }
                       else {
                    	   frame.controlPanel.fillBox.setEnabled(false);
                       }
                        // TODO: get color of the shape
                        // TODO: set the color of the frame to match the shape's color
                        frame.controlPanel.setColor(tempShape.getColor());
                        
                        // TODO: break out of the for loop
                        break;
                        
                    }
                }
            }
            else if (frame.isDeleting()) // are we in delete mode?
            {
                // TODO: create point where the mouse was clicked
            	Point mouseClick = new Point(x0,y0);
            	
                // find which shape was clicked
                // loop through shapes in stack fashion, LIFO
                for (int i = shapeList.size() - 1; i >= 0; --i)
                {
                    tempShape = shapeList.get(i);
                	if (tempShape.contains(mouseClick))
                    {
                        // If the shape contains the point, prompt the user for
                        // a confirmation to delete
                        int ret = JOptionPane.showConfirmDialog(
                                        frame.getControlPanel()
                                                        .getActionPanel(),
                                        "Delete chosen shape?", "",
                                        JOptionPane.YES_NO_OPTION);
                        // TODO: Check answer, remove shape if yes 
                        // You may need to review JOptionPane documentation
                        if (ret == 0) {
                        	removeShape(i);
                        }
                        // TODO: break out of for loop
                        break;
                    }
                }
            }
            else // we're drawing a shape
            {
                // TODO: Indicate that drawing of a shape has begun (look at what flags may be set)
            	drawingFlag = true;
            }
        }

        /**
         * Respond to a button-up event.
         * 
         * @param e Mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e)
        {
            // Are we currently drawing?
            if (drawingFlag)
            {
                // Yes - we are currently drawing

                // Coordinates of the cursor (x0/y0 are already being used, what should you use?)
                // TODO
            	x1 = e.getX();
            	y1 = e.getY();
                // Indicate that we are no longer drawing
                // TODO
            	drawingFlag = false;
                // We no longer need a temporary shape (set to null)
                // TODO
            	tempShape = null;
                // Create the shape given the current state
                // TODO
            	Shape newShape = createShape();
                // Add the shape to the panel list if the shape exists
                // TODO
                if (newShape != null) {
                	DrawPanel.this.addShape(newShape);
                	
                }
            	
                //repaint
                repaint();
            }
        }

        /**
         * Address a mouse movement event (movement only when the button is
         * down)
         * 
         * @param e Mouse event
         */
        @Override
        public void mouseDragged(MouseEvent e)
        {
            // TODO just comments
            // Are we currently drawing a shape?
            if (drawingFlag)
            {
                // Yes
                // Note the current coordinates
                // TODO
            	x1 = e.getX();
            	y1 = e.getY();
                // Create a temporary shape (look at what variables we have)
                // TODO
            	tempShape = createShape();
                // repaint
                repaint();
            }
        }

        /**
         * Given the current state of the control panel and the selected points
         * in the draw panel, create a new shape.
         * 
         * @return The newly created shape
         */
        private Shape createShape()
        {
            // Identify the width and height:
            int xdist = x1 - x0; // Distance from start mouse point to current point
            int ydist = y1 - y0;
            int width = xdist*2; // Width/height are twice the distance from ovals, rectangles, and diamonds.
            int height = ydist*2;

            Point point = new Point(x0, y0);
            // Create a new object, depending on what is selected
            // TODO give them diamond, comments else
            if (frame.isOval())
            {
                // TODO: create and return an Oval
            	Shape newShape = new Oval(point, width, height, frame.getColor(), frame.controlPanel.fillBox.isSelected());
            	return newShape;
            }
            else if (frame.isRectangle())
            {
                // TODO: create and return a Rectangle
            	Shape newShape = new Rectangle(point, width, height, frame.getColor(), frame.controlPanel.fillBox.isSelected());
            	return newShape;
                
            }
            else if (frame.isTriangle())
            {
                // TODO: create and return a Triangle
            	Shape newShape = new RightTriangle(point, width, height, frame.getColor(), frame.controlPanel.fillBox.isSelected());
            	return newShape;
                
            }
            else if (frame.isDiamond())
            {
                // TODO: create and return diamond
            	Shape newShape = new Diamond(point, width, height, frame.getColor(), frame.controlPanel.fillBox.isSelected());
            	return newShape;
                
            }
            // Should not get here, but be safe
            return null;
        }
    }

    //////////////////////////////////////////////////////////////
    /**
     * Constructor: Create the draw panel
     * 
     * @param frame The Frame in which the panel is embedded
     */
    public DrawPanel(DrawFrame frame)
    {
        
        this.frame = frame;

        // Border for the frame
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        // Initially, we are currently not editing a shape
        drawingFlag = false;

        // Set the panel to be focusable: we will receive mouse events
        this.setFocusable(true);

        // Create a handler for the mouse events
        MouseHandler listener = new MouseHandler();

        // Listen for button events
        this.addMouseListener(listener);

        // Also listen for mouse movement events
        this.addMouseMotionListener(listener);
    }

    /**
     * This method adds shapes to the set of shapes.
     * 
     * @param shape The shape to be added to the list of shapes.
     */
    public void addShape(Shape shape)
    {
        shapeList.add(shape);
    }

    /**
     * This method draws the shapes in the set of shapes.
     * 
     * @param g A Graphics object
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // TODO: Draw each shape on the list
        for (int i = 0; i < shapeList.size(); ++i) {
        	shapeList.get(i).draw(g);
        }
        // TODO: If there is a temporary shape, then draw it, too
        if (tempShape != null) {
        	tempShape.draw(g);
        }
        
    }

    /**
     * Return the list of shapes
     * 
     * @return The list of shapes
     */
    protected ArrayList<Shape> getShapeList()
    {
        return shapeList;
    }

    /**
     * Set the list of shapes to a new set
     * 
     * @param shapeList to copy into our panel
     */
    protected void setShapeList(ArrayList<Shape> shapeList)
    {
        if (shapeList != null)
        {
            this.shapeList = shapeList;
            repaint();
        }
    }

    /**
     * Remove all shapes from the list.
     */
    protected void clearShapeList()
    {
        shapeList.clear();
        repaint();
    }

    /**
     * Removed the selected shape from the shape list.
     * 
     * @param index The index of the selected shape.
     */
    protected void removeShape(int index)
    {
        shapeList.remove(index);
        repaint();
    }

    /**
     * Getter for shape index.
     * 
     * @return Index of the shape chosen by the user.
     */
    public int getShapeIndex()
    {
        return shapeIndex;
    }

}
