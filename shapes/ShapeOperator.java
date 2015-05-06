package assignment4.shapes;

/**
 * Represents the Abstract notion of the shape operators that can be applied to two shapes
 * Subtypes may include operations such as union, intersection and difference
 * 
 * @author Harman Singh
 */
public abstract class ShapeOperator implements Shape {
	
	protected Shape shape1, shape2;
	
	public Rectangle boundingBox() {

		Rectangle boundShape1 = shape1.boundingBox();
		Rectangle boundShape2 = shape2.boundingBox();
		
		int minX = Math.min(boundShape1.getXCor(), boundShape2.getXCor());
		int minY = Math.min(boundShape1.getYCor(), boundShape2.getYCor());
		int maxWidth = Math.max((boundShape1.getXCor() + boundShape1.getWidth() - 1), (boundShape2.getXCor() + boundShape2.getWidth() - 1));  
		int maxHeight = Math.max((boundShape1.getYCor() + boundShape1.getHeight() - 1), (boundShape2.getYCor() + boundShape2.getHeight() - 1));
		
		Rectangle rect = new Rectangle(minX, minY, maxWidth, maxHeight);
		
		return rect;
	}

	
}
