import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tokenizer {

	static String[] tokenize(String s) {
		String str = s.replaceAll("\\s+", "");
		String[] result = str.split("(?<=[-+*/=()])|(?=[-+*/=()])");
		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		System.out.println(Arrays.toString(tokenize("123+56*num1"))); //returns 123, +, 56, *, num1
		System.out.println(Arrays.toString(tokenize("(1+ 23) * 9"))); //→ returns (, 1, +, 23, ), *, 9
		System.out.println(Arrays.toString(tokenize("aa1= (14 - 3) *2/a23"))); //→ returns aa1, =, (, 14, -, 3, ), *, 2, /, a23

	}
	
	
}
	


