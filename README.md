# ShapeDrawer

This project provides an easier way for programmers to draw shapes on a canvas or use the shapes created in their own project.  
The main interaction is done through the Interpreter Class that parses the input and draws a shape using the Canvas.
The Interpreter Class is capable of parsing rectangle shape expression or a multiple of rectangular shape expressions.  
The shapes can be unioned (+), intersected (&) or difference of two shape expression can be computed (-).    

The shape is constructed by --**[**-- x,y,width,height --**]**-- which means to construct a rectangle at the x,y coordinate with the given width and height.  
--**draw**-- command draws the outline of the shape produced on the canvas.  
--**fill**-- command draws a filled version of the shape on the canvas.  
  
  The main method in the Interpreter Class has some pre-written arguments that can be used for reference. Just uncomment them to and run the Interpreter Class.  
--**Example of Input/Output**--  
Here the two shapes are constructed and the union of the two shapes is drawn.  
![](http://gdurl.com/VR0M)  
