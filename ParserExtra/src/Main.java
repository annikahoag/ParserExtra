import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner scn = new Scanner(System.in);
		Parse parser = new Parse();
		double[] numArr = new double[10];
		char[] oppArr = new char[10];
		String myStr, str="";
		boolean hasEqualEndOper, isNeg;
		int numCounter=0;
		int numArrIndex=0, oppArrIndex=0;
		String sNum="";
		char arithType;
		double dNum = 0;
		char c;
		String s;
		int strLength;
		int oppLength=0, numLength=0;
		double tempEval=0;
		boolean foundMultDiv;
		
		
		//find if string ends in an operator 
		try {
			//input the string
			System.out.println("\nPlease enter an equation. \n"
					+ "Your equation must start with an equal sign and "
					+ "must only contain numbers and arithmetic operators.");
			myStr = scn.nextLine();
		
		
			//delete all white space 
			str = myStr.replaceAll("\\s", "");
			
			if (str.charAt(str.length()-1) == '+' || str.charAt(str.length()-1) == '-' 
					|| str.charAt(str.length()-1) == '*' || str.charAt(str.length()-1) == '/') {
				throw new MyException("Invalid input. Do not end with operator");
			}
			
			//check for equal sign
			hasEqualEndOper = parser.hasEqual(str);
			
		}catch(MyException e) {
				System.out.println(e.getMessage());
				hasEqualEndOper = false;
		
		}
			
		
		
		
		
		if (hasEqualEndOper) {
			//figure out how many numbers there are 
			for (int i=1; i<str.length(); i++) {
				
				//test for subtraction sign
				if (str.charAt(i) == '-') {
					
					//test for double negative
					c = str.charAt(i+1);
					s = Character.toString(c);
					isNeg = parser.negNum(s, 0);
					
					if (isNeg) {
						strLength = str.length()-1;
						if (i == (str.length()-1)) {
							numCounter++; 
							break;
						}else {
							numCounter++;
							i = i + 2;
							
						}
						
					//if not double negative then proceed
					}else{
						if (str.charAt(i-1) == '+' || str.charAt(i-1) == '-' 
							|| str.charAt(i-1) == '*' || str.charAt(i-1) == '/' || str.charAt(i-1) == '=') {
							i = i + 1;
						}
					}
				} 
					//always increase numCounter if charAt is an operator 
					if(str.charAt(i) == '+' || str.charAt(i) == '-'
							|| str.charAt(i) == '*' || str.charAt(i) == '/' || 
							i == (str.length()-1)){
							numCounter++;
						}
				}

				
				
			//instantiate num and operator arrays
			try {
				numArr = new double[numCounter];
				oppArr = new char[numCounter-1];
				
				oppLength = oppArr.length;
				numLength = numArr.length;
			}catch(NegativeArraySizeException e){
				oppArr = new char[0];
			}
			
			
			
			
			//loop through String 
			for (int i=1; i<str.length(); i++) {
				 c = str.charAt(i);
		
				//add negative sign if charAt is '-' and operator was before
				if (str.charAt(i-1) == '+' || str.charAt(i-1) == '-'
						|| str.charAt(i-1) == '*' || str.charAt(i-1) == '/' || str.charAt(i-1)== '=') {
					 	isNeg = parser.negNum(str, i);
						if (isNeg) {
							sNum = sNum + '-';
							continue;
						}
				}
		
				
				
				//checks for arithmetic operator, make first num
				if (str.charAt(i) != '+' && str.charAt(i) != '-'
					&& str.charAt(i) != '*' && str.charAt(i) != '/') {
					
					
					if(str.charAt(i-1) == '-') {
						sNum = sNum + str.charAt(i);
					
					}else if ((str.charAt(i) == '+' ||str.charAt(i) == '-' || str.charAt(i) == '*' 
							|| str.charAt(i) == '/' ) && str.charAt(i-2) == '-') {
							sNum = sNum + str.charAt(i);
					}else {
						sNum = sNum + str.charAt(i);
					}
					
					
					if( i==str.length()-1 ) {
						
						//make sNum a double and catch invalid input
						try {
							dNum = Double.valueOf(sNum);
							sNum ="";
							arithType = str.charAt(i);
							numArr[numArrIndex] = dNum;
							if (numArr[numArrIndex] == '-') {
								System.out.println("hi");
							}else {
								numArrIndex ++;
							}
							
							if (oppArrIndex < oppArr.length) {
								oppArr[oppArrIndex] = arithType;
								oppArrIndex++;
							}
							
						
								 
						}catch(NumberFormatException e) {
							if (numArr[numArrIndex] != '-') {
								System.out.println("Invalid input. Please only enter numbers.");
								numArr = null;
								break;
							}		
							
						}
							
						
					}//end of if 
				
				
					 	
						
				}else {
					
					//make sNum a double and catch invalid input
					try {
						dNum = Double.valueOf(sNum);
						sNum = "";
						arithType = str.charAt(i);
						numArr[numArrIndex] = dNum;
						if (numArr[numArrIndex] == '-') {
							numArr[numArrIndex] = '-';
						}else {
							numArrIndex ++;
						}
						
						if (oppArrIndex < oppArr.length) {
							oppArr[oppArrIndex] = arithType;
							oppArrIndex++;
						}
				
					}catch(NumberFormatException e) {
							System.out.println("Invalid input. Please only enter numbers.");
							numArr = null;
							break;
					}
				}
				
			}//end of for loop
			
			
			

			
			//evaluate expression
			while (oppLength > 0) {
				
				//know if no more multiplication and division
				foundMultDiv = false;
				
				for (int i = 0; i < oppLength; i++) {
					//find * or /
					if (oppArr[i] == '*' || oppArr[i] == '/') {
						foundMultDiv = true;
						tempEval = 0;
					
						try {
							arithType = oppArr[i];
							if (arithType == '*') {
								tempEval = numArr[i] * numArr[i+1];
							}else {
								tempEval = parser.divZero(numArr[i], numArr[i+1]);
								
							}
						//find division by 0
						}catch (ArithmeticException e) {
							System.out.println("Arithmetic error. Can not divide by 0.");
							oppLength = 0;
							numArr = null;
							break;
							
						//invalid input 
						}catch (NumberFormatException e) {
							System.out.println("Invalid input. Please only enter numbers");
						}
						
						
						numArr[i] = tempEval;
						try {
							
							for (int j = i+1; j < (numLength-1); j++) {
								numArr[j] = numArr[j+1];
							}
							numLength--;
							
						}catch(ArrayIndexOutOfBoundsException e){
							
						}
						
						
						
						try {
							for (int k = i; k < (oppLength-1); k++) {
								oppArr[k] = oppArr[k+1];
							}
							oppLength--;
						}catch(ArrayIndexOutOfBoundsException e) {
							
						}

						
					break;
				
				}
				}//end of * or / loop
				
				
				//done with * and /
				if (foundMultDiv == false) {
					for (int i = 0; i < oppLength; i++) {
					//find + or -
						tempEval = 0;
						if (oppArr[i] == '-' || oppArr[i] == '+') {
							try {
								arithType = oppArr[i];
								if (arithType == '+') {
									tempEval = numArr[i] + numArr[i+1];
								}else {
									tempEval = numArr[i] - numArr[i+1];
								}
								
							//invalid input 
							}catch (NumberFormatException e) {
								System.out.println("Invalid input. Please only enter numbers");
								numArr = null;
								break;
							}
							
							
							
							numArr[i] = tempEval;
							try {
								
								for (int j = i+1; j < (numLength-1); j++) {
									numArr[j] = numArr[j+1];
								}
								numLength--;
								
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
							
							
							try {
								for (int k = i; k < (oppLength-1); k++) {
									oppArr[k] = oppArr[k+1];
								}
								oppLength--;
							}catch(ArrayIndexOutOfBoundsException e) {
								
							}
							
						break;
					}		
					}//end of + - for loop 
					
					
				}//end of foundMultDiv if 
			
			
			}//end of while loop
			
			
			
			//print expression and catch null error
			try {
				for (int j=0; j<numLength; j++) {
					System.out.println(numArr[j]);
				}
			}catch (NullPointerException e) {
				System.out.println("");
			}
			
			
		}else {
			//catches equal sign
			try {
				throw new MyException("Error. \n" + str);
			}
			catch (MyException ex) {
				System.out.println(ex.getMessage());
				
			}
		}//end of else 
		
		scn.close();
		
		
		}

}
