package rocket.net.io;

import io.vertx.core.buffer.Buffer;

public class BufferReader {
	private Buffer buffer = Buffer.buffer();
	private int position;
	
	public BufferReader(byte[] bytes) {
		buffer.appendBytes(bytes);
	}
	
	public int getSignedByte() {
		return getSignedByte(Conversion.NONE);
	}
	
	public int getUnsignedByte() {
		return getUnsignedByte(Conversion.NONE);
	}
	
	public int getSignedByteA() {
		return getSignedByte(Conversion.ADD);
	}
	
	public int getSignedByteS() {
		return getSignedByte(Conversion.SUBSTRACT);
	}
	
	public int getSignedByteC() {
		return getSignedByte(Conversion.NEGATE);
	}
	
	public int getSignedByte(Conversion conversion) {
		return Transformer.transform(conversion, buffer.getByte(position++));
	}
	
	public int getUnsignedByte(Conversion conversion) {
		return Transformer.transform(conversion, buffer.getUnsignedByte(position++)) & 0xff;
	}
	
	public int getSignedShort() {
		return getSignedShort(Conversion.NONE, Endian.BIG);
	}
	
	public int getUnsignedShort() {
		return getUnsignedShort(Conversion.NONE, Endian.BIG);
	}

	public int getSignedInt() {
		return getSignedInt(Conversion.NONE, Endian.BIG);
	}
	
	public int getUnsignedInt() {
		return getUnsignedInt(Conversion.NONE, Endian.BIG);
	}
	
	public int getLong() {
		return getUnsignedLong(Conversion.NONE, Endian.BIG);
	}
	
	public String getString() {
		byte temp;
        StringBuilder b = new StringBuilder();
        while ((temp = (byte) getUnsignedByte()) != 10) {
            b.append((char) temp);
        }
        return b.toString();
	}
	
	public int getUnsignedShort(Conversion conversion, Endian endian) {
		return endianShort(conversion, endian) & 0xffff;
	}
	
	public int getSignedShort(Conversion conversion, Endian endian) {
		return endianShort(conversion, endian);
	}
	
	private int endianShort(Conversion conversion, Endian endian) {
		int value = 0;
		switch(endian) {
		case LITTLE:
			value |= getUnsignedByte(conversion);
            value |= getUnsignedByte() << 8;
			break;
		case BIG:
            value |= getUnsignedByte() << 8;
			value |= getUnsignedByte(conversion);
			break;
		default:
			throw new UnsupportedOperationException("Operation unsupported");
		}
		return value;
	}
	
	public int getUnsignedInt(Conversion conversion, Endian endian) {
		long value = 0;
		switch(endian) {
		case LITTLE:
			value |= getUnsignedByte(conversion);
            value |= getUnsignedByte() << 8;
            value |= getUnsignedByte() << 16;
            value |= getUnsignedByte() << 24;
            break;
		case MIDDLE:
			value |= getUnsignedByte() << 8;
            value |= getUnsignedByte(conversion);
            value |= getUnsignedByte() << 24;
            value |= getUnsignedByte() << 16;
			break;
		case INVERSED_MIDDLE:
			value |= getUnsignedByte() << 16;
            value |= getUnsignedByte() << 24;
            value |= getUnsignedByte(conversion);
            value |= getUnsignedByte() << 8;
			break;
		case BIG:
			value |= getUnsignedByte() << 24;
            value |= getUnsignedByte() << 16;
            value |= getUnsignedByte() << 8;
            value |= getUnsignedByte(conversion);
			break;
		}
		return (int) value;
	}
	
	public int getSignedInt(Conversion conversion, Endian endian) {
		return (int) (getUnsignedInt(conversion, endian) & 0xffffffffL);
	}
	
	public long getL() {
		long value = buffer.getLong(position);
		position+= 8;
		return value;
	}
	
	public int getUnsignedLong(Conversion conversion, Endian endian) {
		int value = 0;
		 switch (endian) {
	        case BIG:
	        	value |= getUnsignedByte() << 56;
	        	value |= getUnsignedByte() << 48;
	        	value |= getUnsignedByte() << 40;
	        	value |= getUnsignedByte() << 32;
	            value |= getUnsignedByte() << 24;
	            value |= getUnsignedByte() << 16;
	            value |= getUnsignedByte() << 8;
	            value |= getUnsignedByte(conversion);
	            break;
	        case LITTLE:
	            value |= getUnsignedByte(conversion);
	            value |= getUnsignedByte() << 8;
	            value |= getUnsignedByte() << 16;
	            value |= getUnsignedByte() << 24;
	            value |= getUnsignedByte() << 32;
	            value |= getUnsignedByte() << 40;
	            value |= getUnsignedByte() << 48;
	            value |= getUnsignedByte() << 56;
	            break;
	        default:
	        	throw new UnsupportedOperationException("Operation unsupported");
	        }
	        return value;
	}
	
	public byte[] getBytes(int amount) {
		return getBytes(amount, Conversion.NONE);
	}
	
	public byte[] getBytes(int amount, Conversion conversion) {
        byte[] data = new byte[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = (byte) getUnsignedByte(conversion);
        }
        return data;
    }
	
	public int getLength() {
		return buffer.length();
	}
	
}