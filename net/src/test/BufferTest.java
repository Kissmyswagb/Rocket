import org.junit.Assert;
import org.junit.Test;

import rocket.net.io.BufferWriter;

public class BufferTest {

	@Test
	public void createBuffer() {
		BufferWriter writer = new BufferWriter();
		writer.putLong(1);
		Assert.assertEquals(writer.getBuffer().length(), 8); //long is 8 in length
	}
	
}
