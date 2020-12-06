
public class Parse {
	
	//instance variables 
	
	
	//constructor
	public Parse() {
		
	}
	
	
	//check for equal sign 
	public boolean hasEqual(String s) {
		char chr = s.charAt(0);
		
		if ( chr == '='){
			return true;
		}else {
			return false;
		}
	}
	
	
	//check for negative number
		public boolean negNum(String s, int i) {
			char c = s.charAt(i);
			
			if (c == '-') {
				return true;
			}else {
				return false;
			}
		}
		
		
		//checks for division by 0
		public static double divZero (double num1, double num2) throws ArithmeticException {
			if (num2 != 0) {
				return num1 / num2;
			}else {
				throw new ArithmeticException();
			}
		}
}
