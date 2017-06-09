package rocket.net.io;

public class Transformer {
	public static int transform(Conversion transformation, int value) {
		switch (transformation) {
		case ADD:
			value = value - 128; //read?
			//value += 128; //this is for write? 
			break;
		case NEGATE:
			value = -value;
			break;
		case SUBSTRACT:
			value = 128 - value;
			break;
		default:
			break;
		}
		return value;
	}
}
