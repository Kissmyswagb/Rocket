package rocket.net.io;

import io.vertx.core.buffer.Buffer;

public class BufferWriter {
	private Buffer buffer = Buffer.buffer();
	
	public BufferWriter() {}
	public BufferWriter(int opcode) {
		put(opcode);
	}
	
	public void putByte(int b) {
		buffer.appendByte((byte) b);
	}
	
	public void putBytes(byte[] bytes) {
		buffer.appendBytes(bytes);
	}
	
	public void put(int value) {
		put(value, Conversion.NONE);
	}
	
	public void putShort(int value) {
		putShort(value, Conversion.NONE, Endian.BIG);
	}
	
	public void putInt(int value) {
		putInt(value, Conversion.NONE, Endian.BIG);
	}
	
	public void putLong(int value) {
		putLong(value, Conversion.NONE, Endian.BIG);
	}
	
	public void put(int value, Conversion transformation) {
		putByte(Transformer.transform(transformation, value));
	}
	
	public void putShort(int value, Conversion transformation, Endian order) {
		switch(order) {
		case LITTLE:
			put(value, transformation);
            put(value >> 8);
			break;
		case BIG:
            put(value >> 8);
			put(value, transformation);
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation");
		}
	}
	
	public void putInt(int value, Conversion transformation, Endian order) {
		switch(order) {
		case LITTLE:
			 put(value, transformation);
	         put(value >> 8);
	         put(value >> 16);
	         put(value >> 24);
			break;
		case MIDDLE:
			put(value >> 8);
            put(value, transformation);
            put(value >> 24);
            put(value >> 16);
			break;
		case INVERSED_MIDDLE:
			put(value >> 16);
            put(value >> 24);
            put(value, transformation);
            put(value >> 8);
			break;
		case BIG:
			put(value >> 24);
            put(value >> 16);
            put(value >> 8);
            put(value, transformation);
			break;
		}
	}
	
	public void putLong(int value, Conversion transformation, Endian order) {
		switch(order) {
		case LITTLE:
			put(value, transformation);
            put(value >> 8);
            put(value >> 16);
            put(value >> 24);
            put(value >> 32);
            put(value >> 40);
            put(value >> 48);
            put(value >> 56);
			break;
		case BIG:
			put(value >> 56);
            put(value >> 48);
            put(value >> 40);
            put(value >> 32);
            put(value >> 24);
            put(value >> 16);
            put(value >> 8);
            put(value, transformation);
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation");
		}
	}
	
	public void putString(String s) {
		for (byte b : s.getBytes()) {
			put(b);
		}
		put(10);
	}
	
	public Buffer getBuffer() {
		return buffer;
	}
}
