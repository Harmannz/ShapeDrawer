package assignment4.shapes;

/**
 * Represents the Intersection of two shapes
 * 
 * @author Harman Singh
 */
public class ShapeIntersect extends ShapeOperator{

	public ShapeIntersect(Shape shape1, Shape shape2){
	    this.shape1 = shape1;
	    this.shape2 = shape2;
	}

	@Override
	public boolean contains(int x, int y) {

		if(shape1.contains(x, y) && shape2.contains(x, y)){
			return true;
		}
		return false;
	}
}
