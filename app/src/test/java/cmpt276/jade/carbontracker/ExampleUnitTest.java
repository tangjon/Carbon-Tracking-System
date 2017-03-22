package cmpt276.jade.carbontracker;

import org.junit.Test;

import java.util.Date;

import cmpt276.jade.carbontracker.model.Graph;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void testCompareDates() throws Exception {
		Date date1 = new Date(2016, 3, 10);
		Date date2 = new Date(2017, 3, 10);

		try { assertEquals(Graph.compareDates(date1, date2), -1);
        } catch (Exception e){}

		date1.setYear(2017);
		try { assertEquals(Graph.compareDates(date1, date2), 0);
		} catch (Exception e){}

		date1.setMonth(2);
		try { assertEquals(Graph.compareDates(date1, date2), -1);
		} catch (Exception e){}

		date1.setMonth(4);
		try { assertEquals(Graph.compareDates(date1, date2), 1);
		} catch (Exception e){}

		date1.setMonth(3);
		try { assertEquals(Graph.compareDates(date1, date2), 0);
		} catch (Exception e){}

		date1.setDate(9);
		try { assertEquals(Graph.compareDates(date1, date2), -1);
		} catch (Exception e){}

		date1.setMonth(11);
		try { assertEquals(Graph.compareDates(date1, date2), 1);
		} catch (Exception e){}

		date1.setMonth(3);
		try { assertEquals(Graph.compareDates(date1, date2), 0);
		} catch (Exception e){}
	}
}