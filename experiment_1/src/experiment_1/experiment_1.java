package experiment_1;

import java.util.ArrayList;
import java.util.Scanner;

public class experiment_1 {
	
	//letter numbser�±��ʽ��Ҫ��ʼ��
	public static void  main (String[] args){
		//variable
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		String expression=null;
		String end_expression=null;
		String[] xiang;
		boolean judge=false;
		int choice=0;
		ArrayList<String>  number = new ArrayList<String> ();//��¼��ֵ������ֵ
		ArrayList<String>  letter = new ArrayList<String> ();//��ֵ����������
		ArrayList<String>  itemCount = new ArrayList<String> ();
		char VarDerivative = 0;//��֤�󵼱���������
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
					System.out.println("Error There is no expression!");//��û�б��ʽ��������޷���ֵ
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
					System.out.println("Error There is no expression!");//��û�б��ʽ��������޷���
				}
				else{//���˱��ʽ
					//����Ҫ���󵼵ı�������� ����ֻ������ !d/d()���������ֵ "()"�ǲ��ܴ��ڵ� �ұ���ֻ������ĸ���
					VarDerivative=expression.charAt(4);
					j=0;
					/*System.out.println(VarDerivative);
					for (int i=0;i<expression.length();i++){
						//System.out.println("1");
						if(expression.charAt(i)=='+'){
							System.out.println("1");
							System.out.println(end_expression.substring(j,i));
							itemCount.add(end_expression.substring(j, i));
							j=i+1;
						}
						else if(i==end_expression.length()-1){
							System.out.println(end_expression.substring(j));
							itemCount.add(end_expression.substring(j));
						}
					}*/
					xiang=end_expression.split("\\+");
					/*for (int i=0;i<xiang.length;i++){
						System.out.println(xiang[i]);
					}*/
					
					derivative(xiang,VarDerivative,end_expression);
				}
				break;
			default:
				break;
			}
			
			 
		}
	}
	public static boolean expression(String str,int len){//������ʽ
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
	public static void simplify(String expression,ArrayList<String> letter,ArrayList<String> number){//��ʼ����ֵ
		String end=null;
		end =expression;
			/*for (int i=0;i<letter.size();i++){
				System.out.println(letter.get(i)+' '+number.get(i));
			}*/
		for (int i=0;i<letter.size();i++){
			end=end.replace(letter.get(i), number.get(i));
			//System.out.println(end);
		}
		System.out.println(end);
		//return end;
	}
	@SuppressWarnings("null")
	public static String derivative(String[] itemCount,char var,String expression){//�󵼳�ʼ��
		int varNum[];//�����ĸ���
		int xishu[];//���ϵ��
		ArrayList<String>  newitemCount = new ArrayList<String> ();
		String end="";
		varNum = new int [itemCount.length];
		xishu = new int [itemCount.length];
		if(expression.indexOf(var)==-1){
			System.out.println("Error, no variable");
			return null;
		}
		else{
			for (int i=0;i<itemCount.length;i++){
				for (int j=0;j<itemCount[i].length();j++){//��������ĸ���
					if(var==itemCount[i].charAt(j)){
						varNum[i]++;
					}
				}
				for (int j=0;j<itemCount[i].length();j++){//����ϵ��
					if(itemCount[i].charAt(j)>='0'&&itemCount[i].charAt(j)<='9'){
						xishu[i]=xishu[i]*10+(itemCount[i].charAt(j)-48);
					}
					else{
						xishu[i]=1;
					}
				}
				System.out.println("����");
				System.out.print(varNum[i]);//����
				System.out.println(xishu[i]);
				
			}
			/*for (int i=0;i<itemCount.length;i++){
				System.out.println("����");
				System.out.print(itemCount[i]);//����
			}*/
			
			
			for (int i=0;i<itemCount.length;i++){//�ϳ��󵼺����
				if(varNum[i]!=0){
					//System.out.println(Integer.toString(xishu[i])+"*"+itemCount[i].replaceFirst(String.valueOf(var), String.valueOf(varNum[i])));
					newitemCount.add(Integer.toString(xishu[i])+"*"+itemCount[i].replaceFirst(String.valueOf(var), String.valueOf(varNum[i])));
					//System.out.println(newitemCount);
				}
			}
			for (int i=0;i<newitemCount.size();i++){//���ϳɵ���������
				if(i==0){
					end+=newitemCount.get(i);
				}
				else {
					end+="+"+newitemCount.get(i);
				}
			}
		}
		
		System.out.println(end);
		return end;
	}
	
}
