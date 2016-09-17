package experiment_1;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class experiment_1 {
	
	//letter numbser新表达式需要初始化
	public static void  main (String[] args){
		//variable
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		String expression=null;
		String end_expression=null;
		String[] xiang;
		boolean judge=false;
		int choice=0;
		ArrayList<String>  number = new ArrayList<String> ();//记录赋值函数的值
		ArrayList<String>  letter = new ArrayList<String> ();//赋值函数的类型
		ArrayList<String>  fuhao = new ArrayList<String> ();
		char VarDerivative = 0;//保证求导变量的类型
		int j=0;
		int x=0;
		int y=0;
		while(true){
			choice=0;
			expression=in.nextLine();
			expression=change(expression);
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
					System.out.println("Error There is no expression!");//在没有表达式的情况下无法赋值
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
					System.out.println("Error There is no expression!");//在没有表达式的情况下无法求导
				}
				else{//有了表达式
					//首先要将求导的变量求出来 变量只能是在 !d/d()括号里面的值 "()"是不能存在的 且变量只能有字母组成
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
					xiang=splitby_jia(end_expression);
					//System.out.println(1);
					for (int i=0;i<end_expression.length();i++){
						if(end_expression.charAt(i)=='+'){
							fuhao.add("+");
						}
						else if(end_expression.charAt(i)=='-'){
							fuhao.add("-");
						}
					}
					
					derivative(xiang,VarDerivative,end_expression,fuhao);
				}
				break;
			default:
				break;
			}
			
			 
		}
	}
	public static boolean expression(String str,int len){//输入表达式
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
	public static void simplify(String expression,ArrayList<String> letter,ArrayList<String> number){//初始化赋值
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
	public static String derivative(String[] itemCount,char var,String expression,ArrayList<String> fuhao){//求导初始化
		int varNum[];//变量的个数
		int xishu[];//项的系数
		ArrayList<String>  newitemCount = new ArrayList<String> ();
		ArrayList<String>  newfuhao = new ArrayList<String> ();
		String end="";
		int o=0;
		varNum = new int [itemCount.length];
		//xishu = new int [itemCount.length];
		if(expression.indexOf(var)==-1){
			System.out.println("Error, no variable");
			return null;
		}
		else{
			for (int i=0;i<itemCount.length;i++){
				for (int j=0;j<itemCount[i].length();j++){//计算变量的个数
					if(var==itemCount[i].charAt(j)){
						varNum[i]++;
					}
				}
				/*for (int j=0;j<itemCount[i].length();j++){//计算系数
					if(itemCount[i].charAt(j)>='0'&&itemCount[i].charAt(j)<='9'){
						xishu[i]=xishu[i]*10+(itemCount[i].charAt(j)-48);
					}
					else{
						xishu[i]=1;
					}
				}*/
				System.out.println("测试");
				System.out.print(varNum[i]+" ");//测试
				//System.out.println(xishu[i]);

			}
			/*for (int i=0;i<itemCount.length;i++){
				System.out.println("测试");
				System.out.print(itemCount[i]);//测试
			}*/


			for (int i=0;i<itemCount.length;i++){//合成求导后的项
				if(varNum[i]!=0){
					if(i<itemCount.length-1){
						System.out.println(fuhao);
						System.out.println(fuhao.get(i));
						newfuhao.add(fuhao.get(i));
					}
					//System.out.println(Integer.toString(xishu[i])+"*"+itemCount[i].replaceFirst(String.valueOf(var), String.valueOf(varNum[i])));
					newitemCount.add(itemCount[i].replaceFirst(String.valueOf(var), String.valueOf(varNum[i])));
					//System.out.println(newitemCount);
				}
			}
			for (int i=0;i<newitemCount.size();i++){//将合成的项连起来
				if(i==0){
					end+=newitemCount.get(i);
				}
				else {
					end+=newfuhao.get(o++)+newitemCount.get(i);
				}
			}
		}
		System.out.println(end);
		return end;
	}
	public static String[] splitby_jia(String str){//按加法分割
		return str.split("\\+|\\-");
	}
	public static String[] splitby_cheng(String str){//按乘法分割
		return str.split("\\*");
	}
	public static String change(String expression){//将输入的表达式进行修改
		String change_expression=null;
		String end_expression="";
		String[] str;
		String[] xiang;
		String newstr;
		String newxiang;
		int xishu=0;
		change_expression=expression.replace("\t", "");//将tab删掉
		change_expression=change_expression.replace(" ", "");//将空格删掉
		str=change_expression.split("//+|//-");
		for (int i=0;i<str.length;i++){
			newstr=str[i];
			for(int j=0;j<str[i].length();j++){
				if(j<str[i].length()-1){
					if(str[i].charAt(j)>='0'&&str[i].charAt(j)<='9'){
						if((str[i].charAt(j+1)>='a'&&str[i].charAt(j+1)<='z')||(str[i].charAt(j+1)>='A'&&str[i].charAt(j+1)<='Z')){
							newstr=newstr.substring(0,j+1)+"*"+newstr.substring(j+1);
						}
					}
				}
			}
			str[i]=newstr;
			end_expression+=str[i];
			System.out.print(end_expression+" ");
		}
		str=end_expression.split("//+|//-");
		for (int i=0;i<str.length;i++){
			newstr=str[i];
			xiang=str[i].split("//*");
			for(int j=0;j<xiang.length;j++){
				newxiang=xiang[j];
				for(int k=0;k<xiang[i].length();k++){
					if(xiang[j].charAt(k)=='^'){
						for (int l=k+1;l<xiang[j].length();l++){
							if(xiang[j].charAt(l)<'0'||xiang[j].charAt(l)>'9'){//出现错误，直接返回，在judge中会判断为错误表达式
								return end_expression;
							}
							else{
								xishu=xishu*10+(xiang[j].charAt(l)-48);
							}
						}
					}
					newxiang=xiang[j].substring(0, k);
					for (int m=0;m<xishu;m++){
						newxiang+="*"+xiang[j-1];
					}
					newxiang+=xiang[j].substring(k+1);
					xiang[j]=newxiang;
				}
			}
		}
		return end_expression;
	}

    public static ArrayList<String> getItems(String expression)
    /**
     * 对输入的expression，按加号拆分为多个多想式，保存并返回
     */
    {

        return null;
    }
    public static boolean isInteger(String fact)
    /**
     *
     * @param fact为多项式中的因子
     * @return 是数字返回1，否则返回0。
     */
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(fact).matches();
    }
    public static String makeSimple(String expression,ArrayList<String> items)
    /**
     * 对输入的expression进行简化，输出简化结果
     * 简化包括
     */
    {
        String[] factors = null;
        int[] newXishu  = new int[items.size()];
        for (int i = 0; i < newXishu.length; i++)
        {
            newXishu[i]=1;
        }
        for (int i=0;i<items.size();i++)//对没个多项式内部的数字相乘化简
        {
            factors = items.get(i).split("//*");//用*分离出多项式的每个因子
            for (int j = 0; j < factors.length; j++)//计算系数
            {
                if (isInteger(factors[j]))//如果是数字
                {
                    newXishu[i] *= Integer.parseInt(factors[j]);
                }
            }

        }
        return null;
    }
}


