package assignment4.shapes;

/**
 * The Interpreter class is used to parse the input commands that have been applied upon initialisation.
 * After the run() method is called a Canvas object is returned that allows the shapes to be drawn 
 * 
 * @author Harman Singh
 */
import java.util.*;

public class Interpreter {
    private String input;
    private int index;
    private HashMap<String, Shape> ShapeMap = new HashMap<String, Shape>();
    
    public Interpreter(String input) {
    	this.input = input;
    	this.index = 0;
    }

    /**
     * This method should return a canvas to which the input commands have been applied.
     * @return a canvas that shows the result of the input.
     */
	public Canvas run() {
		Canvas canvas = new Canvas();
		while (index < input.length()) {
			evaluateNextCommand(canvas);
        }
		return canvas;
 	}

    /**
     * This method reads the string and evaluates the command
     * 
     * @param canvas
     */
	private void evaluateNextCommand(Canvas canvas) {
		skipWhiteSpace();
		String cmd = readWord();
		skipWhiteSpace();
		
		if (cmd.equals("fill")) {
			Shape shape = readShape();
			Color color = readColor();
			fillShape(shape, color, canvas);
		}
		else if(cmd.equals("draw")){
			Shape shape = readShape();
			Color color = readColor();
			drawShape(shape, color, canvas);
		}
		else{
			//cmd is a variable and we are assigning a variable to a shape or to another variable
			//we expect the next Character to be '='
			if(input.charAt(index) == '='){
				index++;
			} else{
				error();
			}
			
			skipWhiteSpace();
 			Shape reference = readShape();
			ShapeMap.put(cmd, reference);
		
		}
		skipWhiteSpace();
	}
	
	
	private Shape readShape() {
		Shape shape = null;
		skipWhiteSpace();
		char lookahead = input.charAt(index);

		if(lookahead == '('){
			shape = evaluateBracketed();
		}
		else if(lookahead == '['){
			shape = evaluateShape();
		}
		else{
			shape = ShapeMap.get(readWord());
			if(shape == null){
				error();
			}
		}
		
		skipWhiteSpace();
		if(index < input.length()) {
			lookahead = input.charAt(index);
			
			if(lookahead == '+') {
				match("+");
				shape = new ShapeUnion(shape,readShape());
			}
			else if(lookahead == '&') {
				match("&");
				shape = new ShapeIntersect(shape,readShape());
			}
			else if(lookahead == '-') {
				match("-");
				shape = new ShapeDifference(shape, readShape());
			}

		}
		return shape;
	}

	
	private Shape evaluateBracketed() {
		match("(");
		Shape shape = readShape();
		match(")");
		return shape;
	}
	
	private void match(String text) {
		skipWhiteSpace();
		if(input.startsWith(text,index)) {
			index += text.length();
		} else {
//			System.out.println("there is an error with matching bracket");
			error();
		}
	}

	
	private Shape evaluateShape(){
		if(input.charAt(index) == '['){
			index++;
			int start = index;
			while(index <= input.length() && input.charAt(index) != ']'){
				index++;
			}
			
			String[] specsStr = input.substring(start, index).split(",");
//			System.out.println(Arrays.toString(specsStr));
			if(specsStr.length != 4){
				error();
			}
			int[] specsInt = new int[4];
			for (int i = 0; i < specsStr.length; i++){
				try{					
					//Integer was expected
					int integer = Integer.parseInt(specsStr[i].trim());
					if (integer < 0){
						error();
					}
					specsInt[i] = integer;
				}catch(Exception e){
					error();
				}
				
			}
			
			try{
			Shape rectangle = new Rectangle(specsInt[0], specsInt[1], specsInt[2], specsInt[3]);
			index++;
			return rectangle;
			}catch(Exception e){
				error();
			}
		}
		return null;
	}

	
	private void fillShape(Shape shape, Color color, Canvas canvas) {
		Rectangle bound = shape.boundingBox();	
		int yStart = bound.getYCor();
		int yEnd = yStart + bound.getHeight();
		for(;yStart <= yEnd; yStart++){
			//now loop over the x coordinates
			int xStart = bound.getXCor();
			int xEnd = xStart + bound.getWidth();
			for(; xStart <= xEnd; xStart++){
				//see if these cordinates are contained within the shape and if so then colour this position
				if(shape.contains(xStart, yStart)){
					canvas.draw(xStart, yStart, color);
				}
			}
		}
	}
	
	private void drawShape(Shape shape, Color color, Canvas canvas){
		Rectangle bound = shape.boundingBox();
		int yStart = bound.getYCor();
		int yEnd = yStart + bound.getHeight();
		for(;yStart <= yEnd; yStart++){
			boolean outsideShape = true;
			boolean insideShape = false;
			//now loop over the x coordinates
			int xStart = bound.getXCor();
			int xEnd = xStart + bound.getWidth();
			for(; xStart <= xEnd; xStart++){
			    //now we go through horizontally and whenever we go from out to in or in to out we colour that point
				if(shape.contains(xStart + 1, yStart) && outsideShape && shape.contains(xStart,yStart)){ 
					//we moving from being outside of Shape to inside
					insideShape = true;
					canvas.draw(xStart, yStart, color);					
					outsideShape = false; //now we are inside the shape
				}
				else if(!shape.contains(xStart + 1, yStart) && insideShape){ 
					// we are moving from being insideShape to outside
					outsideShape = true;
					canvas.draw(xStart, yStart,color);
					insideShape = false;
					}
				}
		}
		
		int xStart = bound.getXCor();
		int xEnd = xStart + bound.getWidth();
		for(;xStart <= xEnd; xStart++){
			boolean outsideShape = true;
			boolean insideShape = false;
			//now loop over the x coordinates
            int yStart1 = bound.getYCor();
            int yEnd1 = yStart + bound.getHeight();
			for(; yStart1 <= yEnd1; yStart1++){
			    //now we go through horizontally and whenever we go from out to in or in to out we colour that point
				if(shape.contains(xStart, yStart1+1) && outsideShape && shape.contains(xStart,yStart1)){ 
					//we moving from being outside of Shape to inside
					insideShape = true;
					canvas.draw(xStart, yStart1, color);					
					outsideShape = false; //now we are inside the shape
				}
				else if(!shape.contains(xStart, yStart1+1) && insideShape){ 
					// we are moving from being insideShape to outside
					outsideShape = true;
					canvas.draw(xStart, yStart1,color);
					insideShape = false;
					}
				}
		}
		
	}
			
	
    private Color readColor() {
    	skipWhiteSpace();
    	int start = index;
        while(index < input.length() && (Character.isLetterOrDigit(input.charAt(index)) || input.charAt(index) == '#' )) {
            index++;
        }
    	String colorNum = input.substring(start, index);
//    	System.out.println(colorNum);
    	Color color = new Color(colorNum);
    	return color;
	}




	/**
	 * this method continues to read the input until a character is not a letter
	 * @return
	 */
	private String readWord() {
        int start = index;
        while(index < input.length() && (Character.isLetter(input.charAt(index)) || Character.isDigit(input.charAt(index)))) {
                index++;
        }
        return input.substring(start,index);
    }

    private void skipWhiteSpace() {         
        while (index < input.length()
                        && (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
                index = index + 1;
        }
    }
    
    private void error() {
		throw new IllegalArgumentException();		
	}
    
    public static void main(String[] args){

//    	Interpreter inter = new Interpreter("Box = [25,25,125,125]\nsmallBox = [50,50,50,50]"
//    			+ "\nBox = (Box + Box) & Box\ndraw (Box - [50,50,50,50]) #aa0000");
//    	Interpreter inter = new Interpreter("x = [0,0,500,500]"
//    			+"\ndraw x #010101"
//    			+"\ntl = [100,100,140,140]"
//    			+"\ntr = [260,100,140,140]"
//    			+"\nbl = [100,260,140,140]"
//    			+"\nbr = [260,260,140,140]"
//    			+"\nfill tl #ff0000"
//    			+"\nfill tr #00ff00"
//				+"\nfill bl #0000ff"
//				+"\nfill br #000000"
//				+"\nc = [220,220,60,60]"
//				+"\nc = c - (tl+tr+bl+br)"
//				+"\nfill c #a0a0a0");
    	Interpreter inter = new Interpreter( "d1=[6,3,12,12]\nd1=[6,3,10,10]\nfill d1 #010103\n");
    	Canvas can = inter.run();
    	can.show();
    }
}
