package experiment_1;

import java.util.ArrayList;
import java.util.Arrays;
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
			case 1://输入
				if (judge){
					System.out.println(expression);
					end_expression=expression;
				}
				else{
					System.out.println("The expression is wrong!Stupid!");
					//System.out.println(end_expression);
				}
				break;
			case 2://赋值
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
			case 3://求导
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
	
    /**
     * 对输入的expression，按加号拆分为多个多想式，保存并返回
     * 保存为ArrayList类型的优势在于在化简时，会有减少，便于处理。
     */
    public static ArrayList<String> getItems(String expression)
    {
    	String[] tmpItems = expression.split("\\+|\\-");
    	ArrayList<String> items =  new ArrayList<String>();
    	for (int i = 0; i < tmpItems.length; i++) {
			items.add(tmpItems[i]);
		}
        return items;
    }
    
    /**
    *
    * @param fact为多项式中的因子
    * @return 是数字返回1，否则返回0。
    */
    public static boolean isInteger(String fact)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(fact).matches();
    }
    
    /**
     * 
     * @param expression:表达式
     * @param items:多项式 
     * @return 符号数组，仅含±1
     */
    public static int[] getSign(String expression,ArrayList<String> items)
    {
    	int[] sign = new int[items.size()];
    	int j=0;
    	for(int i = 0;i<expression.length();i++)
    	{
    		if(expression.charAt(i) == '+')
    		{
    			sign[j++]=1;
    		}
    		else if (expression.charAt(i) == '-')
    		{
    			sign[j++]=-1;
    		}
    	}
    	
    	return sign;
    }
    
    /**
     * 判断是否为同类项
     * @param string1 同类项1
     * @param string2 同类项2
     * @return 是否为同类项
     * 算法思想：将两个多项式分别按*分开后，将变量保存在两个数组里，对数组进行字典排序后比较即可。相同为同类项，不同则非同类项。
     */
    public static boolean isSameItem(String string1, String string2)
    {
		String[] factor1,factor2;
		factor1=string1.split("//*|(/d+)");//按*号或者数字拆分
		factor2=string2.split("//*|(/d+)");
		if(factor1.length != factor2.length){
			return false;
		}
		Arrays.sort(factor1);
		Arrays.sort(factor2);
		for (int i = 0; i < factor2.length; i++) {
			if(factor1[i]!=factor2[i]){
				return false;
			}
		}
    	return true;
	}
    
    /**
     * * 对输入的expression进行简化，输出简化结果
     * items为已分开的多项式，signArr为符号数组。
     * 简化包括每个 同类项系数相乘，同类项合并。
     * @param expression
     * @param items
     * @param signArr
     * @return 化简后的表达式
     */
    public static String makeSimple(String expression,ArrayList<String> items ,int[] signArr)
    {
        String[] factors = null;//保存多项式的因子
        String newExpression = null;//保存最终化简结果
        int[] newXishu  = new int[items.size()];//化简后的系数数组
        ArrayList<String> newItems = items;//化简后的多项式
        int tmpXishu = 0;//临时系数
        String tmpItem = null;//临时多项式
        
        for (int i = 0; i < newXishu.length; i++)
        {
            newXishu[i]=1;
        }
        for (int i=0;i<newItems.size();i++)//对没个多项式内部的数字相乘化简
        {
            factors = newItems.get(i).split("//*");//用*分离出多项式的每个因子
            for (int j = 0; j < factors.length; j++)//计算系数
            {
                if (isInteger(factors[j]))//如果是数字
                {
                    newXishu[i] *= Integer.parseInt(factors[j]);
                }
            }
        }
        for (int i=0;i<newItems.size();i++)//合并同类项
        {
        	for(int j=i+1;j<newItems.size();j++)
        	{
        		if(isSameItem(newItems.get(i),newItems.get(j)))//如果是同类项
        		{
        			tmpXishu=signArr[i]*newXishu[i]+signArr[j]*newXishu[j];//计算新的系数
        			tmpItem=Integer.toString(tmpXishu);
        			
        			factors = newItems.get(i).split("//*");//用*分离出多项式的每个因子
                    for (int k = 0; k < factors.length; k++)//重新对多项式组合
                    {
                        if (!isInteger(factors[j]))//如果不是数字
                        {
                            tmpItem+=("*"+factors[k]);
                        }
                    }
                    newItems.set(i, tmpItem);//更新i位置项
                    newItems.remove(j);//删除j位置项
        		}
        	}
        }
        if(newItems.size()==0){
        	return "";
        }
        else{
        	newExpression=newItems.get(0);
	        for (int i = 1; i < newItems.size(); i++) {//合成最终化简结果
	        	newExpression += "*" + newItems.get(i);
			}
        }
        return newExpression;
    }
	
}


