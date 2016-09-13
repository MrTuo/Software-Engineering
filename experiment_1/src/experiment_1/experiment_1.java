package experiment_1;

import java.util.ArrayList;
import java.util.Scanner;

public class experiment_1 {
	
	//letter numbser新表达式需要初始化
	public static void  main (String[] args){
		//variable
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		String expression=null;
		String end_expression=null;
		String[] xiang=null;
		boolean judge=false;
		int choice=0;
		ArrayList<String>  number = new ArrayList<String> ();
		ArrayList<String>  letter = new ArrayList<String> ();
		ArrayList<String>  itemCount = new ArrayList<String> ();
		int j=0;
		int x=0;
		int y=0;
		while(true){
			choice=0;
			expression=in.nextLine();
			judge=expression(expression, expression.length());
			if (expression.length()>=9 && expression.substring(0, 9).equals("!simplify")){
				choice=2;
			}
			else if(expression.length()>=4 && expression.substring(0, 4).equals("!d/d")){
				choice=3;
			}
			else{
				choice=1;
			}
			switch(choice)
			{
			case 1:
				if (judge){
					System.out.println(expression);
					end_expression=expression;
				}
				else{
					System.out.println("The expression is wrong!Stupid!");
					//System.out.println(end_expression);
				}
				break;
			case 2:
				if (end_expression==null){
					simplify(end_expression,letter,number);
				}
				else{
					for (int i=expression.length()-1;i>=0;i--){
						if(expression.charAt(i)=='='){
							j=i;
							number.add(expression.substring(i+1,j+2));
						}
						else if(expression.charAt(i)==' '){
							letter.add(expression.substring(i+1, j));
						}
					}
					simplify(end_expression,letter,number);
				}
				break;
			case 3:
				if(end_expression==null){
					
				}
				else{
					
				}
				break;
			default:
				break;
			}
			
			 
		}
	}
	public static boolean expression(String str,int len){//input the expression
		char a=0,b=0,c=0;//b is behind a,c is in front of a
		int x=0;//judge the number is the first one or not
		for (int i=0;i<len;i++){;
			a=str.charAt(i);
			if (i<len-1){
				b=str.charAt(i+1);
			}
			if (i>='1'){
				c=str.charAt(i-1);
			}
			if(a=='+' || a=='-' || a=='*'){//symbol
				if ((b=='+'||b=='-'||b=='*')&& i<len-1){//symbol a is connect with symbol b
					return false;
				}
				else if (i==len-1){//symbol a is the last one in expression
					return false;
				}
				else if (i==0){//symbol a is the first one
					return false;
				}
				x=0;//number clear
			}
			else if (a>='0' && a<='9'){//number
				x++;
				if (a=='0'&& x==1 &&c!='+'&&c!='-'&&c!='*'){
					if(i<len-1 && b!='+'&&b!='-'&&b!='*'){
						return false;// not ...+0+...
					}
				}
			}
			else if ((a>='a'&&a<='z')||(a>='A'&&a<='Z')){//letter
				if (i<len-1 && i>=1 && b!='+'&&b!='-'&&b!='*' &&c!='+'&&c!='-'&&c!='*'){
					return false; // not ...+a+...
				}
				if (i==0 && b!='+'&&b!='-'&&b!='*' && i<len-1){
					return false;// not a+...
				}
				x=0;//number clear
			}
		} 
		return true;
	}
	public static String simplify(String expression,ArrayList<String> letter,ArrayList<String> number){//初始化赋值
		String end=null;
		end =expression;
		if(expression.length()==0){
			System.out.println("Error There is no expression!");
		}
		else{
			for (int i=0;i<letter.size();i++){
				System.out.println(letter.get(i)+' '+number.get(i));
			}
			
		}
		for (int i=0;i<letter.size();i++){
			end=end.replace(letter.get(i), number.get(i));
			System.out.println(end);
		}
		return end;
	}
	public static String derivative(ArrayList<String>  itemCount,char var,String expression){//求导初始化
		int varNum[];//变量的个数
		int xishu[];//项的系数
		ArrayList<String>  newitemCount = null;
		String end=null;
		varNum = new int [itemCount.size()];
		xishu = new int [itemCount.size()];
		for (int i=0;i<itemCount.size();i++){
			for (int j=0;j<itemCount.get(i).length();j++){//计算变量的个数
				if(var==itemCount.get(i).charAt(j)){
					varNum[i]++;
				}
			}
			for (int j=0;j<itemCount.get(i).length();j++){//计算系数
				if(itemCount.get(i).charAt(j)>='0'&&itemCount.get(i).charAt(j)<='9'){
					xishu[i]=xishu[i]*10+(int)itemCount.get(i).charAt(j);
				}
				else{
					break;
				}
			}
		}
		for (int i=0;i<itemCount.size();i++){
			if(varNum[i]==0){
				continue;
			}
			else{
				newitemCount.add(Integer.toString(xishu[i])+'*'+itemCount.get(i).replaceFirst(String.valueOf(var), String.valueOf(varNum[i])));
			}
		}
		for (int i=0;i<newitemCount.size();i++){
			if(i==0){
				end.concat(newitemCount.get(i));
			}
			else {
				end.concat("+"+newitemCount.get(i));
			}
		}
		System.out.println(end);
		return end;
	}
	
}
