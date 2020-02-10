package prob6;

public class ShapeTest {

	public static void main(String[] args) {
		Shape[] shapes = new Shape[2];
		
		shapes[0] = new Rectangle(5, 6);
		shapes[1] = new RectTriangle(6, 2);
		//Math.sqrt(삼각형 대각선 길이 구하는 공식)
		for( Shape shape : shapes ) {
			System.out.println( "area:" + shape.getArea() );
			System.out.println( "perimeter:" + shape.getPerimeter() );
			
			if( shape instanceof Resizable ) {
				Resizable resizable = (Resizable) shape;
				resizable.resize( 0.5 );
				System.out.println( "new area:" + shape.getArea() );
				System.out.println( "new perimeter:" + shape.getPerimeter() );
			}
		}
	}
}