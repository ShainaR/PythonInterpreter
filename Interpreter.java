import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
	
	static HashMap<String, String> varMap = new HashMap<>();
	
	public static boolean isVar(String str){
		Pattern p = Pattern.compile("^[A-Za-z][\\_]*[\\w]*");
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		//System.out.println(b);
		return b;
	}
	
	
	public static void interpret(String str){
		String[] tokens = Tokenizer.tokenize(str);
		//System.out.println(tokens[0]);

		if (isVar(tokens[0])){
			if (tokens.length > 2 && tokens[1].equals("=")){
				str = "";
				for (int i = 0; i < tokens.length; i++){
					// set string to empty
					if (isVar(tokens[i]) && i >= 2){
						tokens[i] = varMap.get(tokens[i]).toString();
					} 
					// rebuild string with hashed token values
					str = str + tokens[i];
				}
				varMap.put(tokens[0], Evaluator.evalExpr(str));
			} else if (tokens.length == 1 && (varMap.containsKey(str))){
				System.out.println(varMap.get(tokens[0]));
			} else if (tokens[0].equals("print")){
				System.out.println(Evaluator.evalExpr(str.substring(5,str.length())));
			} 
		} else if (SimpleParser.isExprHelper(str)) {
			System.out.println(Evaluator.evalExpr(str));
		} 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print(">>> ");
			String in = sc.nextLine();
			
			if (in.equals("quit")) {
				System.exit(0);
			} else 
				interpret(in);

		}

	}

}
