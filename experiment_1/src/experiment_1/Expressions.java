package experiment_1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Expressions {
	//属性
	private String expression = "";//保存原表达式串

	/**
	 * 构造函数
	 */
	public Expressions(){}

	/**
	 * 根据输入字符串，创建
	 * @param inputstr
	 */
	public void setExpressions(String inputstr)
	{
		if (checkExpression(inputstr)) {
			expression = inputstr;
		}
		else{
			System.out.println("The expression is wrong!Stupid!");
		}
	}


	/**
	 * 判断表达式合法性
	 * @param expression 输入的表达式
	 * @return 是否合法
	 */
	private boolean checkExpression(String expression){
		char a=0,b=0,c=0;//b is behind a,c is in front of a
		int x=0;//judge the number is the first one or not
		for (int i=0;i<expression.length();i++){;
			a=expression.charAt(i);
			if (i<expression.length()-1){
				b=expression.charAt(i+1);
			}
			if (i>='1'){
				c=expression.charAt(i-1);
			}
			if(a=='+' || a=='-' || a=='*'){//symbol
				if ((b=='+'||b=='-'||b=='*')&& i<expression.length()-1){//symbol a is connect with symbol b
					return false;
				}
				else if (i==expression.length()-1){//symbol a is the last one in expression
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
					if(i<expression.length()-1 && b!='+'&&b!='-'&&b!='*'){
						return false;// not ...+0+...
					}
				}
			}
			else if ((a>='a'&&a<='z')||(a>='A'&&a<='Z')){//letter
				if (i<expression.length()-1 && i>=1 && b!='+'&&b!='-'&&b!='*' &&c!='+'&&c!='-'&&c!='*'){
					return false; // not ...+a+...
				}
				if (i==0 && b!='+'&&b!='-'&&b!='*' && i<expression.length()-1){
					return false;// not a+...
				}
				x=0;//number clear
			}
		}
		return true;
	}

	/**
	 * 表达式赋值
	 * @param letter 要赋值的变量
	 * @param number 变量值
	 * @return 赋值化简后的表达式
	 */
	public String simplify(ArrayList<String> letter,ArrayList<String> number)
	{
		String end=null;
		end =expression;
			/*for (int i=0;i<letter.size();i++){
				System.out.println(letter.get(i)+' '+number.get(i));
			}*/
		for (int i=0;i<letter.size();i++){
			end=end.replace(letter.get(i), number.get(i));
			//System.out.println(end);
		}
		//将化简结果合并简化 by妥
		ArrayList<String> newItem = setItems(end);//获取多项式
		int[] newSign = setSign(end, newItem);//获取多项式符号数组
		String newExpression = makeSimple(end, newItem, newSign);
		return newExpression;
	}
	/**
	 *
	 * @param itemCount
	 * @param var
	 * @param expression
	 * @param fuhao
	 * @return
	 */
	public String derivative(String[] itemCount,char var,String expression,ArrayList<String> fuhao){//求导初始化
		int varNum[];//变量的个数
		ArrayList<String>  newitemCount = new ArrayList<String> ();
		ArrayList<String>  newfuhao = new ArrayList<String> ();
		String end="";
		int o=0;
		varNum = new int [itemCount.length];
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
			}

			for (int i=0;i<itemCount.length;i++){//合成求导后的项
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

		//将化简结果合并简化 by妥
		ArrayList<String> newItem = setItems(end);//获取多项式
		int[] newSign = setSign(end, newItem);//获取多项式符号数组
		String newExpression = makeSimple(end, newItem, newSign);
		return newExpression;
	}
	/**
	 * 获取表达式的多项式
	 * @param expression 表达式
	 * @return 多项式
	 */
	private static ArrayList<String> setItems(String expression)
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
	 * @param expression 表达式
	 * @param items 多项式
	 * @return 符号数组
	 */
	private static int[] setSign(String expression, ArrayList<String> items)
	{
		int[] sign = new int[items.size()];
		int j=0;
		if(expression.charAt(0) == '-'){//处理第一个多项式
			sign[j++] = -1;
		}
		else{
			sign[j++] = 1;
		}

		for(int i = 1;i<expression.length();i++)//处理后续多项式
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
	 *
	 * @param expression 表达式
	 * @param items 多项式
	 * @param signArr 符号数组
	 * @return 简化后的表达式
	 */
	private static String makeSimple(String expression,ArrayList<String> items ,int[] signArr){
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
		for (int i=0;i<newItems.size();i++)//对每个多项式内部的数字相乘化简
		{
			factors = newItems.get(i).split("\\*");//用*分离出多项式的每个因子
			for (int j = 0; j < factors.length; j++)//计算系数
			{
				if (isInteger(factors[j]))//如果是数字
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
		for (int i=0;i<newItems.size();i++)//合并同类项
		{
			for(int j=i+1;j<newItems.size();j++)
			{
				if(isSameItem(newItems.get(i),newItems.get(j)))//如果是同类项
				{
					tmpXishu=signArr[i]*newXishu[i]+signArr[j]*newXishu[j];//计算新的系数
					tmpItem=Integer.toString(tmpXishu);

					factors = newItems.get(i).split("\\*");//用*分离出多项式的每个因子
					for (int k = 0; k < factors.length; k++)//重新对多项式组合
					{
						if (!isInteger(factors[k]))//如果不是数字
						{
							tmpItem+=("*"+factors[k]);
						}
					}
					newItems.set(i, tmpItem);//更新i位置项
					newItems.remove(j);//删除j位置项

					newXishu[i]=tmpXishu;//更新i位置系数

					for(int l = j; l<newItems.size();l++){
						signArr[l]=signArr[l+1];//更新符号数组，j位置前移
						newXishu[l]=newXishu[l+1];//更新系数数组，j位置前移
					}

					j-=1;//删除后，新的项占了原来位置，需要减一，否则判断同类项会露项。

				}
			}
		}
		if(newItems.size()==0){
			return "";
		}
		else{
			newExpression=newItems.get(0);
			for (int i = 1; i < newItems.size(); i++) {//合成最终化简结果
				newExpression += "+" + newItems.get(i);
			}
		}
		return newExpression;
	}

	/**
	 * 判断string1和string2是否为同类项，并返回结果
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static boolean isSameItem(String string1, String string2){
		String[] factor1,factor2;
		ArrayList<String> newFactors1 = new ArrayList<String>();
		ArrayList<String> newFactors2 = new ArrayList<String>();
		factor1=string1.split("\\*");//按*号或者数字拆分
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
	 * @param fact为多项式中的因子
	 * @return 是数字返回1，否则返回0。
	 */
	public static boolean isInteger(String fact)
	{
		Pattern pattern = Pattern.compile("[0-9]*|-[0-9]*");
		return pattern.matcher(fact).matches();
	}
	/**
	 * 获取表达式串
	 * @return
	 */
	public String getExpression()
	{
		return expression;
	}
}
