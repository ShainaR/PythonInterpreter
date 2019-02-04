import java.util.Arrays;

public class AcceptedRegExpression {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String stringMatch = "variable1 = 1 + 2 * (4 / 6)";
		String string2 = "1+";
		String str = stringMatch.replaceAll("\\s+", "");
		String str2 = string2.replaceAll("\\s+", ""); 
		String[] result = str2.split("(?<=[-+*\\/=])|(?=[-+*\\/=])");
		
		System.out.println(Arrays.toString(result));
	}
}