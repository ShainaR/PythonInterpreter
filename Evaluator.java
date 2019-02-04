import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;
import java.math.BigDecimal;


public class Evaluator {
	
	// determines order of precedence for evaluating arithmetic 
    public static int precedence(String operator) {
        if (operator.equals("*") || operator.equals("/") || operator.equals("%")){
            return 3;
        } else if (operator.equals("+") || operator.equals("-")){
            return 2;
        } else
            return 1;
    }
    
    public static String calculate(double x, double y, String operator){
        String expression = "";
        
        if (operator.equals("+")){
            expression = Double.toString(add(x,y));
        } else if (operator.equals("*")){
            expression = Double.toString(mult(x,y));
        } else if (operator.equals("%")) {
        	expression = Double.toString(mod(x,y));
        } else if (operator.equals("-")) {
        	expression = Double.toString(sub(x,y));
        } else if (operator.equals("/")) {
        	expression = Double.toString(div(x,y));
        }
        
        return expression;
    }
	
    public static double mult(double x, double y){
        return x * y;
    }
    
    public static double add(double x, double y){
        return x + y;
    }
    
    public static double sub(double x, double y) {
    	return x - y;
    }
    public static double div(double x, double y) {
    	return x / y;
    }
    
    public static double mod(double x, double y) {
    	System.out.println("X: " + x + "Y: " + y + x%y);
    	return x % y;
    }
    
	public static String evalExpr(String s) {
		String[] str = Tokenizer.tokenize(s);

		double x = 0, y = 0;
		int currentPrec = 0, nextPrec = 0;
		String op = "";
		String currentOp = "";
		String newValue = "";
		boolean d = false;
			
		Stack<String> numbers = new Stack<String>();
		Stack<String> operators = new Stack<String>();
		Stack<String> variables = new Stack<String>();
		
		Pattern p = Pattern.compile("^[A-Za-z]");
		Matcher m = p.matcher(str[0]);
		boolean b = m.matches();
		
		// find variable names in string		
		if (!SimpleParser.isExprHelper(s) && !b){
        	return null;
        }
		
		for(int i = 0; i < str.length; i++) {	
			if (str[i].contains(".") || str[i].contains("/"))
				d = true;
			if(b && str.length > 1) {
				variables.push(str[i]);
			} else if (b && str.length == 1){
				variables.push(str[i]);
			}
			
			if (str[i].equals("") || str[i].equals("="))
				continue;
			// push numbers onto stack and operators onto separate stack
			if (SimpleParser.isExprHelper(str[i])){
				numbers.push(str[i]);
			} else if (str[i].equals("(")){
				operators.push(str[i]);
			} else if (str[i].equals(")")) {
				while (!operators.isEmpty() && !operators.peek().equals("(") && numbers.size()>1){
					op = operators.pop();
					y = Double.parseDouble(numbers.pop());
					x = Double.parseDouble(numbers.pop());
					newValue = calculate(x, y, op);
					numbers.push(newValue);	
						
				}

				// Pop opening parentheses off operators stack
				if (!operators.isEmpty() && !operators.peek().equals("("))
					operators.pop();
			} else if (!SimpleParser.isExprHelper(str[i])){
				currentOp = str[i];
				currentPrec = precedence(currentOp);

				// Check for opening parentheses
				if (!operators.isEmpty()) {
					nextPrec = precedence(operators.peek());
				} else 
					nextPrec = 0;
				if (!operators.isEmpty() && operators.peek().equals("("))
					operators.pop();
				while (!operators.isEmpty() && nextPrec >= currentPrec) {
					op = operators.pop();
					y = Double.parseDouble(numbers.pop());
					x = Double.parseDouble(numbers.pop());
					newValue = calculate(x, y, op);
					numbers.push(newValue);

				}
				operators.push(str[i]);
			} else if(b) {
				variables.push(str[i]);
			}
				
		}
		
		while (!operators.isEmpty() && numbers.size() > 1) {
			op = operators.pop();
			y = Double.parseDouble(numbers.pop());
			x = Double.parseDouble(numbers.pop());
			newValue = calculate(x, y, op);
			numbers.push(newValue);	
		}
		
		if (!variables.isEmpty() && numbers.isEmpty()) {
	    	return variables.pop();
		} else if (d && !numbers.isEmpty()) {
			return numbers.pop();
		} else if (!numbers.isEmpty()) {
			//System.out.println(numbers.peek());
			BigDecimal number = new BigDecimal(numbers.pop());
		    return number.stripTrailingZeros().toPlainString();
	    } else 
	    	return null;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//      System.out.println(evalExpr("234")); // 234
//      System.out.println(evalExpr("(1 + 3)"));  // 4
//      System.out.println(evalExpr("(1 + 3) * 45")); // 180
//      System.out.println(evalExpr("4 + 6 + (1 * 3 + 10)")); // 23
//      System.out.println(evalExpr("(1 + (2 + 1)) * 45"));   // 180
//      System.out.println(evalExpr("(1 + (2 + 1)) * (78+3*15) +45"));    // 537
//      System.out.println(evalExpr("(1 +")); // null
//      System.out.println(evalExpr("1 + * 2")); // null
//      System.out.println(evalExpr("a1"));
//      System.out.println(evalExpr("a = 1"));
//     System.out.println(evalExpr("a"));
//    	System.out.println(evalExpr("((1+3/2)*45)"));
//    	System.out.println(evalExpr("((13 - 10) * 2 / 2) + 20"));
//    	System.out.println(evalExpr("5%8"));
    	//System.out.println(5%8);

    }
}
