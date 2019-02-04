import java.util.Stack;

public class BalancedExpression {
	
	
	static boolean isBalanced(String s) { //, Stack<Character> stack){
		Stack<Character> stack = new Stack<Character>();
			// loops through all characters in string s
			for (int i = 0; i < s.length(); i++) {		
				
				// pushes character on stack if opening parenthesis, bracket, etc.
				if(s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '<' || s.charAt(i) == '{') {
					stack.push(s.charAt(i));
					//System.out.println(stack.peek());
				}	
				
				// checks closing parenthesis
				// if the stack is empty and there is a closing parenthesis, returns false
				// if the top of the opening parenthesis stack does not match the closing, returns false
				// if matches and stack is not empty, pops opening parenthesis off stack and continues
				if (s.charAt(i) == ')') {
					if (stack.isEmpty() || stack.peek() != '(') 
						return false;
					else stack.pop();
				} else if (s.charAt(i) == ']') {
					if (stack.isEmpty() || stack.peek() != '[') 
						return false;
					else stack.pop();
				} else if (s.charAt(i) == '>') {
					if (stack.isEmpty() || stack.peek() != '<') 
						return false;
					else stack.pop();
				} else if (s.charAt(i) == '}') {
					if (stack.isEmpty() || stack.peek() != '{') 
						return false;
					else stack.pop();
				} 
				
			}
		
		// Checks to see if there is missing closing parenthesis
		if (!stack.isEmpty()) 
			return false;
		// only reach this condition if true
		else
			return true;
	}
			

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(isBalanced("(xx () x)")); //returns true
		System.out.println(isBalanced("({)}")); //returns false
		System.out.println(isBalanced("(()")); //return false
		System.out.println(isBalanced("{(() () [abc] <x> xx)}")); //returns true
		System.out.println(isBalanced("}")); //returns false
		System.out.println(isBalanced("}")); //returns false
		System.out.println(isBalanced("()]")); //returns false
		System.out.println(isBalanced("2 * (3 + (13 % 3)) / (4 - 2)")); //returns false
		
	}

}
