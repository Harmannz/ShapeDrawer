package assignment4.shapes;

/**
 * This class captures the abstract notion of a Rectangle shape.  
 * @author Harman Singh
 *
 */
public class Rectangle implements Shape{
	private int xCor, yCor, width, height;

	@Override
	public boolean contains(int x, int y) {
		if((xCor <= x && x <= (width+xCor - 1)) && (yCor <= y && y <= (height + yCor - 1))){
			return true;
		}
		return false;
	}

	@Override
	public Rectangle boundingBox() {
		return new Rectangle(xCor, yCor, width, height);
	}
	
	public int getYCor() {
		return yCor;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getXCor() {
		return xCor;
	}

	public Rectangle(int x, int y, int width, int height){
		this.xCor = x;
		this.yCor = y;
		this.width = width;
		this.height = height;
	}


}
