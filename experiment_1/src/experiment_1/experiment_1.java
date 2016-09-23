package experiment_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class experiment_1 {

	//letter numbser新表达式�?要初始化
	public static void  main (String[] args){
		//variable
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		String expression=null;//输入的表达式或�?�是！命令行
		String end_expression=null;//这里存放的是表达式，当expression输入的是正确的表达式时，将end_expression更新
		String[] xiang;//这里存放的是将表达式分解为项的String数组
		boolean judge=false;//判断表达式是否正�?
		int choice=0;//选择
		ArrayList<String>  number = new ArrayList<String> ();//记录赋�?�函数的�?
		ArrayList<String>  letter = new ArrayList<String> ();//赋�?�函数的类型
		ArrayList<String>  fuhao = new ArrayList<String> ();//存放表达式中�?+号或者是-号的位置顺序
		char VarDerivative = 0;//保证求导变量的类�?
		int j=0;
		int x=0;
		int y=0;

		while(true){
			choice=0;
			expression=in.nextLine();//输入
			fuhao=myfuhao(expression);//获得输入的符号，不管是不是正确的表达式或者是赋�?�求导指�?
			expression=change(expression,fuhao);//将表达式转变为标准的表达式（无论表达式是否正确或者是输入的赋值求导指�?

			judge=expression(expression, expression.length());//判断表达式是否正�?
			if (expression.length()>=9 && expression.substring(0, 9).equals("!simplify")){//如果是赋值�?�择2
				choice=2;
			}
			else if(expression.length()>=4 && expression.substring(0, 4).equals("!d/d")){//如果是求导�?�择3
				choice=3;
			}
			else{
				choice=1;//其他情况选择1
			}
			switch(choice)
			{
			case 1:
				if (judge){//如果表达式正确，则输出表达式，并更新end_expression
					System.out.println("改变后的表达�?:"+expression);
					end_expression=expression;
				}
				else{//不正确则输出错误信息
					System.out.println("The expression is wrong!Stupid!");
					//System.out.println(end_expression);
				}
				break;
			case 2:
				if (end_expression==null){
					System.out.println("Error There is no expression!");//在没有表达式的情况下无法赋�??
				}
				else{
					for (int i=expression.length()-1;i>=0;i--){//获得要赋值的变量
						if(expression.charAt(i)=='='){
							j=i;
							number.add(expression.substring(i+1,j+2));//获得要赋值变量的�?
						}
						else if(expression.charAt(i)==' '){
							letter.add(expression.substring(i+1, j));//获得要赋值变量的名称
						}
					}
					simplify(end_expression,letter,number);//计算赋�?�后的结果，在函数中输出
				}
				break;
			case 3:
				if(end_expression==null){
					System.out.println("Error There is no expression!");//在没有表达式的情况下无法求导
				}
				else{//有了表达�?
					//首先要将求导的变量求出来 变量只能是在 !d/d()括号里面的�?? "()"是不能存在的 且变量只能有字母组成
					VarDerivative=expression.charAt(4);//获得要求导的变量 这里变量只能是单个字�?
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
					fuhao=myfuhao(end_expression);
					derivative(xiang,VarDerivative,end_expression,fuhao);//输出求导后的结果，在函数中输�?
				}
				break;
			default:
				break;
			}
			
			 
		}
	}
	public static boolean expression(String str,int len){//输入表达�?
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
	public static void simplify(String expression,ArrayList<String> letter,ArrayList<String> number){//初始化赋�?
		String end=null;
		end =expression;
			/*for (int i=0;i<letter.size();i++){
				System.out.println(letter.get(i)+' '+number.get(i));
			}*/
		for (int i=0;i<letter.size();i++){
			end=end.replace(letter.get(i), number.get(i));
			//System.out.println(end);
		}
		System.out.println("Before make simple");
		System.out.println(end);
		//将化�?结果合并�?�? by�?
		ArrayList<String> newItem = getItems(end);//获取多项�?
		int[] newSign = getSign(end, newItem);//获取多项式符号数�?
		String newExpression = makeSimple(end, newItem, newSign);
		System.out.println("After make simple");
		System.out.println(newExpression);
		//return end;
	}
	@SuppressWarnings("null")
	public static String derivative(String[] itemCount,char var,String expression,ArrayList<String> fuhao){//求导初始�?
		int varNum[];//变量的个�?
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
				for (int j=0;j<itemCount[i].length();j++){//计算变量的个�?
					if(var==itemCount[i].charAt(j)){
						varNum[i]++;
					}
				}
			}

			for (int i=0;i<itemCount.length;i++){//合成求导后的�?
				if(varNum[i]!=0){
					if(i<itemCount.length-1){
						//System.out.println(fuhao);
						//System.out.println(fuhao.get(i));
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
		
		//将化�?结果合并�?�? by�?
		System.out.println("Before make simple");
		System.out.println(end);
		ArrayList<String> newItem = getItems(end);//获取多项�?
		int[] newSign = getSign(end, newItem);//获取多项式符号数�?
		String newExpression = makeSimple(end, newItem, newSign);
		System.out.println("After make simple");
		System.out.println(newExpression);
		return newExpression;
	}
	public static String[] splitby_jia(String str){//按加法分�?
		return str.split("\\+|\\-");
	}
	public static String[] splitby_cheng(String str){//按乘法分�?
		return str.split("\\*");
	}
	public static ArrayList<String> myfuhao(String end_expression){
		ArrayList<String> fuhao=new ArrayList<String>();
		for (int i=0;i<end_expression.length();i++){
			if(end_expression.charAt(i)=='+'){
				fuhao.add("+");
			}
			else if(end_expression.charAt(i)=='-'){
				fuhao.add("-");
			}
		}
		return fuhao;
	}
	public static String change(String expression,ArrayList<String> fuhao){//将输入的表达式进行修�?
		String change_expression=null;
		String end_expression="";
		String[] str;
		String[] xiang;
		String newxiang = null;
		int xishu=0;
		int fuhao1=0;//标记到第几个符号�?
		change_expression=expression.replace("\t", "");//将tab删掉
		change_expression=change_expression.replace(" ", "");//将空格删�?

		//System.out.println(str+" ");
		for (int i=0;i<change_expression.length();i++){
			if(i<change_expression.length()-1){
				if(change_expression.charAt(i)>='0'&&change_expression.charAt(i)<='9'){
					if((change_expression.charAt(i+1)>='a'&&change_expression.charAt(i+1)<='z')||(change_expression.charAt(i+1)>='A'&&change_expression.charAt(i+1)<='Z')){
						//System.out.print(change_expression+" ");
						change_expression=change_expression.substring(0,i+1)+"*"+change_expression.substring(i+1);
						//System.out.println(change_expression+" ");
					}
				}
			}
		}
		end_expression=change_expression;
		str=end_expression.split("\\+|\\-");
		System.out.println("输入的表达式:"+end_expression);
		for (int i=0;i<str.length;i++){//依次访问每个�?
			xiang=str[i].split("\\*");
			for(int j=0;j<xiang.length;j++){//对每个项访问
				newxiang=xiang[j];
//				System.out.println("newxiang1"+"   "+newxiang);
				for(int k=0;k<xiang[j].length();k++){
					if(xiang[j].charAt(k)=='^'){
						for (int l=k+1;l<xiang[j].length();l++){//计算^后面的系数是多少
							if(xiang[j].charAt(l)<'0'||xiang[j].charAt(l)>'9'){//出现错误，直接返回，在judge中会判断为错误表达式
								return end_expression;
							}
							else{
								xishu=xishu*10+(xiang[j].charAt(l)-48);
							}
						}
						newxiang=newxiang.substring(0, newxiang.indexOf("^"));
//						System.out.println("newxiang2"+"   "+newxiang);
//						System.out.println("newxiang3"+"   "+newxiang);
						for (int m=0;m<xishu-1;m++){
							newxiang+="*"+xiang[j].substring(0, xiang[j].indexOf("^"));
						}
						xiang[j]=newxiang;
//						System.out.println("newxiang3"+"   "+newxiang);
					}
				}
			}
//			System.out.println("1=============");
//			for (int n=0;n<xiang.length;n++){
//				System.out.println(xiang[n]);
//			}
//			System.out.println("=============");
			newxiang=xiang[0];
			for (int n=1;n<xiang.length;n++){
				newxiang+="*"+xiang[n];
			}
			str[i]=newxiang;
//			System.out.println("*****"+str[i]);
		}
//		System.out.println("fuhao"+"   "+fuhao);
//		System.out.println("str"+"   "+str.length);
		//每个项都处理完了，该合并项了
		end_expression="";
//		for (int i=0;i<str.length;i++){
//			System.out.println("str"+"   "+str[i]);
//		}
		for (int i=0;i<str.length;i++){
			end_expression+=str[i];
			if(i<str.length-1){
				end_expression+=fuhao.get(fuhao1);
				fuhao1++;
			}
//			System.out.println("end_expression"+"   "+end_expression);
		}
		return end_expression;
	}
	
    /**
     * 对输入的expression，按加号拆分为多个多想式，保存并返回
     * 保存为ArrayList类型的优势在于在化简时，会有减少，便于处理�??
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
    * @return 是数字返�?1，否则返�?0�?
    */
    public static boolean isInteger(String fact)
    {
        Pattern pattern = Pattern.compile("[0-9]*|-[0-9]*");
        return pattern.matcher(fact).matches();
    }
    
    /**
     * 
     * @param expression:表达�?
     * @param items:多项�? 
     * @return 符号数组，仅含�?1
     */
    public static int[] getSign(String expression,ArrayList<String> items)
    {
    	int[] sign = new int[items.size()];
    	int j=0;
    	if(expression.charAt(0) == '-'){//处理第一个多项式
    		sign[j++] = -1;
    	}
    	else{
    		sign[j++] = 1;
    	}
    
    	for(int i = 1;i<expression.length();i++)//处理后续多项�?
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
     * @param string1 同类�?1
     * @param string2 同类�?2
     * @return 是否为同类项
     * 算法思想：将两个多项式分别按*分开后，将变量保存在两个数组里，对数组进行字典排序后比较即可。相同为同类项，不同则非同类项�??
     */
    public static boolean isSameItem(String string1, String string2)
    {
		String[] factor1,factor2;
		ArrayList<String> newFactors1 = new ArrayList<String>();
		ArrayList<String> newFactors2 = new ArrayList<String>();
		factor1=string1.split("\\*");//�?*号或者数字拆�?
		factor2=string2.split("\\*");
		
		Arrays.sort(factor1);
		Arrays.sort(factor2);
		for (int i = 0; i < factor1.length; i++) {
			if(!isInteger(factor1[i])){
				newFactors1.add(factor1[i]);
			}
		}
		for (int i = 0; i < factor2.length; i++) {
			if(!isInteger(factor2[i])){
				newFactors2.add(factor2[i]);
			}
		}
		
		if(newFactors1.size() != newFactors2.size()){
			return false;
		}
		for (int i = 0; i < newFactors2.size(); i++) {
			if(!newFactors1.get(i).equals(newFactors2.get(i))){
				return false;
			}
		}
    	return true;
	}
    
    /**
     * * 对输入的expression进行�?化，输出�?化结�?
     * items为已分开的多项式，signArr为符号数组�??
     * �?化包括每�? 同类项系数相乘，同类项合并�??
     * @param expression
     * @param items
     * @param signArr
     * @return 化简后的表达�?
     */
    public static String makeSimple(String expression,ArrayList<String> items ,int[] signArr)
    {
        String[] factors = null;//保存多项式的因子
        String newExpression = null;//保存�?终化�?结果
        int[] newXishu  = new int[items.size()];//化简后的系数数组
        ArrayList<String> newItems = items;//化简后的多项�?
        int tmpXishu = 0;//临时系数
        String tmpItem = null;//临时多项�?
        
        for (int i = 0; i < newXishu.length; i++)
        {
            newXishu[i]=1;
        }
        for (int i=0;i<newItems.size();i++)//对每个多项式内部的数字相乘化�?
        {
            factors = newItems.get(i).split("\\*");//�?*分离出多项式的每个因�?
            for (int j = 0; j < factors.length; j++)//计算系数
            {
                if (isInteger(factors[j]))//如果是数�?
                {
                    newXishu[i] *= Integer.parseInt(factors[j]);
                }
            }
            
            tmpItem = Integer.toString(newXishu[i]);
            for (int k = 0; k < factors.length; k++)//重新对多项式组合
            {
                if (!isInteger(factors[k]))//如果不是数字
                {
                    tmpItem+=("*"+factors[k]);
                }
            }
            newItems.set(i, tmpItem);
        }
        for (int i=0;i<newItems.size();i++)//合并同类�?
        {
        	for(int j=i+1;j<newItems.size();j++)
        	{
        		if(isSameItem(newItems.get(i),newItems.get(j)))//如果是同类项
        		{
        			tmpXishu=signArr[i]*newXishu[i]+signArr[j]*newXishu[j];//计算新的系数
        			tmpItem=Integer.toString(tmpXishu);
        			
        			factors = newItems.get(i).split("\\*");//�?*分离出多项式的每个因�?
                    for (int k = 0; k < factors.length; k++)//重新对多项式组合
                    {
                        if (!isInteger(factors[k]))//如果不是数字
                        {
                            tmpItem+=("*"+factors[k]);
                        }
                    }
                    newItems.set(i, tmpItem);//更新i位置�?
                    newItems.remove(j);//删除j位置�?
                    
                    newXishu[i]=tmpXishu;//更新i位置系数
                    
                    for(int l = j; l<newItems.size();l++){
                    	signArr[l]=signArr[l+1];//更新符号数组，j位置前移
                    	newXishu[l]=newXishu[l+1];//更新系数数组，j位置前移
                    }                  
                    
                    j-=1;//删除后，新的项占了原来位置，�?要减�?，否则判断同类项会露项�??
                    
        		}
        	}
        }
        if(newItems.size()==0){
        	return "";
        }
        else{
        	newExpression=newItems.get(0);
	        for (int i = 1; i < newItems.size(); i++) {//合成�?终化�?结果
	        	newExpression += "+" + newItems.get(i);
			}
        }
        return newExpression;
    }
	
}
