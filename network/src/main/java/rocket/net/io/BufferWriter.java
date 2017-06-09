package rocket.net.io;

import io.vertx.core.buffer.Buffer;

public class BufferWriter {
	private Buffer buffer = Buffer.buffer();
	
	private BufferWriter() {}
	private BufferWriter(int opcode) {
		put(opcode);
	}
	
	public BufferWriter putByte(int b) {
		buffer.appendByte((byte) b);
		return this;
	}
	
	public BufferWriter putBytes(byte[] bytes) {
		buffer.appendBytes(bytes);
		return this;
	}
	
	public BufferWriter put(int value) {
		put(value, Conversion.NONE);
		return this;
	}
	
	public BufferWriter putShort(int value) {
		putShort(value, Conversion.NONE, Endian.BIG);
		return this;
	}
	
	public BufferWriter putInt(int value) {
		putInt(value, Conversion.NONE, Endian.BIG);
		return this;
	}
	
	public BufferWriter putLong(long value) {
		putLong((int) value, Conversion.NONE, Endian.BIG);
		return this;
	}
	
	public BufferWriter put(int value, Conversion transformation) {
		putByte(Transformer.transform(transformation, value));
		return this;
	}
	
	public BufferWriter putShort(int value, Conversion transformation, Endian order) {
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
		return this;
	}
	
	public BufferWriter putInt(int value, Conversion transformation, Endian order) {
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
		return this;
	}
	
	public BufferWriter putLong(int value, Conversion transformation, Endian order) {
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
		return this;
	}
	
	public BufferWriter putString(String s) {
		for (byte b : s.getBytes()) {
			put(b);
		}
		put(10);
		return this;
	}
	
	public Buffer getBuffer() {
		return buffer;
	}
	
	public static BufferWriter writer() {
		return new BufferWriter();
	}
}
