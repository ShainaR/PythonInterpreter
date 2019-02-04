import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.String;
import java.util.Scanner;

/*
<expr> -> <expr> + <term>
| <term>
<term> -> <term> * <factor>
| <factor>
<factor> -> (<expr>)
| <id>
<id> -> caught by regex
*/

public class SimpleParser {
	
	static boolean isID(String[] s){
		//<id> -> caught by regex
		//System.out.println("Is ID string: " + Arrays.toString(s));
		// convert to string to compare to regex
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < s.length; i++){
			result.append(s[i]);
		}
		String str = result.toString();
		
		//System.out.println("ID string: " + str);
		if(str == null)
			return false;

		// create regular expression to match numbers
		Pattern p = Pattern.compile(".*[\\d]");
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		while(m.find()){
			//System.out.println("Found:" + b);
		}
		//System.out.println("B: " + b);
		return b;
		
	}
	
	static boolean isFactor(String[] s){
		//<factor> -> (<expr>) | <id>
		String[] subArray = {};
		
		//System.out.println("Factor string: " + Arrays.toString(s));
		if (s.length < 1)
			return false;

		boolean openParen = false;
		boolean closeParen = false;
		int openIndex = -1;
		int closeIndex = -1;
		
		for (int i = 0; i < s.length; i++){
			if (s[i].equals("(")) {
				openParen = true;
				openIndex = i;
			} 
			if (s[i].equals(")")){
				closeParen = true;
				closeIndex = i;
			}
		}
		
		if (openParen && closeParen) {
			// create a subarray 
			subArray = Arrays.copyOfRange(s, openIndex, closeIndex - 1);
			return (isExpr(subArray));
		}
		else if (openParen && !closeParen) {
			//System.out.println("Just an opening (");
			subArray = Arrays.copyOfRange(s, openIndex + 1, s.length);
			//System.out.println("subarray: " + Arrays.toString(subArray));
			return isExpr(subArray);
		} else if (closeParen && !openParen) {
			//System.out.println("Just a closing )");
			subArray = Arrays.copyOfRange(s, 0, closeIndex);
			//System.out.println("subarray: " + Arrays.toString(subArray));
			return isExpr(subArray);
		} else {
			return isID(s);
		}
		
	}

	static boolean isTerm(String[] s){
		//<term> -> <term> * <factor> | <factor>
		String[] left = {};
		String[] right = {};
		
		//System.out.println("Term string: " + Arrays.toString(s));
		if (s.length < 1)
			return false;
		
		int start = 0;
		int end = s.length;
		boolean mult = false;
		int multIndex = -1;
		
		for (int i = 0; i < s.length; i++){
			if (s[i].equals("*") || s[i].equals("/") || s[i].equals("%")) {
				mult = true;
				multIndex = i;
			}
		}
		
		if (mult) {
			// create a subarray left and right 
			left = Arrays.copyOfRange(s, start, multIndex);
			right = Arrays.copyOfRange(s,  multIndex+1, end);
			return ((isTerm(left)) && isFactor(right));
		}
		else
			return isFactor(s);
	
	}
	
	static boolean isExprHelper(String s){
		String[] result = {};
		if (BalancedExpression.isBalanced(s)) {
			// tokenize
			result = Tokenizer.tokenize(s);
			return isExpr(result);
		} else {
			return BalancedExpression.isBalanced(s);
		}
	}

	static boolean isExpr(String[] s){
		//<expr> -> <expr> + <term> | <term>
		String[] left = {};
		String[] right = {};
		
		//System.out.println("Expr string: " + Arrays.toString(s));
		if (s.length < 1) 
			return false;
		int start = 0;
		int end = s.length;
		boolean plus = false;
		int plusIndex = -1;
		
		for (int i = 0; i < s.length; i++){
			//System.out.println(s[i]);
			if (s[i].equals("+") || s[i].equals("-")) {
				plus = true;
				plusIndex = i;
			}
		}
//		System.out.println(plus);
//		System.out.println(plusIndex);
		if (plus) {
			// create a subarray left and right 
			left = Arrays.copyOfRange(s, start, plusIndex);
			right = Arrays.copyOfRange(s,  plusIndex+1, end);
			return ((isExpr(left)) && isTerm(right));
		}
		else
			return isTerm(s);
	}
	
	public static void main(String[] args) {

		// test if well-formed expression
		System.out.println(isExprHelper("123"));
		System.out.println(isExprHelper("1 + 3"));
		System.out.println(isExprHelper("(1 + 3) * 45"));
		System.out.println(isExprHelper("(1 + (2 + 1)) * 45"));
		System.out.println(isExprHelper("(1 + (2 + 1)) * (78+3*15) +45"));
		System.out.println(isExprHelper("(1 +"));
		System.out.println(isExprHelper("1 + * 2"));
	
	}

}
