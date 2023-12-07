package a_Introductory;


import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);

		assertEquals(Optional.of(4), Optional.of(res1.x));
		assertEquals(Optional.of(-21), Optional.of(res1.y));
		assertEquals(Optional.of(-3), Optional.of(res2.x));
		assertEquals(Optional.of(12), Optional.of(res2.y));
	}

	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);

		assertEquals(Optional.of(10), Optional.of(res1.x));
		assertEquals(Optional.of(39), Optional.of(res1.y));
		assertEquals(Optional.of(17), Optional.of(res2.x));
		assertEquals(Optional.of(6), Optional.of(res2.y));
	}

}
