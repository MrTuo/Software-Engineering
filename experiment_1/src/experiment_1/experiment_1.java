package experiment_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class experiment_1 {

	//letter numbser�±��ʽ��Ҫ��ʼ��
	public static void  main (String[] args){
		//variable
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		String expression=null;//����ı��ʽ�����ǣ�������
		String end_expression=null;//�����ŵ��Ǳ��ʽ����expression���������ȷ�ı��ʽʱ����end_expression����
		String[] xiang;//�����ŵ��ǽ����ʽ�ֽ�Ϊ���String����
		boolean judge=false;//�жϱ��ʽ�Ƿ���ȷ
		int choice=0;//ѡ��
		ArrayList<String>  number = new ArrayList<String> ();//��¼��ֵ������ֵ
		ArrayList<String>  letter = new ArrayList<String> ();//��ֵ����������
		ArrayList<String>  fuhao = new ArrayList<String> ();//��ű��ʽ�е�+�Ż�����-�ŵ�λ��˳��
		char VarDerivative = 0;//��֤�󵼱���������
		int j=0;
		int x=0;
		int y=0;

		while(true){
			choice=0;
			expression=in.nextLine();//����
			fuhao=myfuhao(expression);//�������ķ��ţ������ǲ�����ȷ�ı��ʽ�����Ǹ�ֵ��ָ��
			//expression=change(expression,fuhao);//�����ʽת��Ϊ��׼�ı��ʽ�����۱��ʽ�Ƿ���ȷ����������ĸ�ֵ��ָ��

			judge=expression(expression, expression.length());//�жϱ��ʽ�Ƿ���ȷ
			if (expression.length()>=9 && expression.substring(0, 9).equals("!simplify")){//����Ǹ�ֵѡ��2
				choice=2;
			}
			else if(expression.length()>=4 && expression.substring(0, 4).equals("!d/d")){//�������ѡ��3
				choice=3;
			}
			else{
				choice=1;//�������ѡ��1
			}
			switch(choice)
			{
			case 1:
				if (judge){//������ʽ��ȷ����������ʽ��������end_expression
					System.out.println(expression);
					end_expression=expression;
				}
				else{//����ȷ�����������Ϣ
					System.out.println("The expression is wrong!Stupid!");
					//System.out.println(end_expression);
				}
				break;
			case 2:
				if (end_expression==null){
					System.out.println("Error There is no expression!");//��û�б��ʽ��������޷���ֵ
				}
				else{
					for (int i=expression.length()-1;i>=0;i--){//���Ҫ��ֵ�ı���
						if(expression.charAt(i)=='='){
							j=i;
							number.add(expression.substring(i+1,j+2));//���Ҫ��ֵ������ֵ
						}
						else if(expression.charAt(i)==' '){
							letter.add(expression.substring(i+1, j));//���Ҫ��ֵ����������
						}
					}
					simplify(end_expression,letter,number);//���㸳ֵ��Ľ�����ں��������
				}
				break;
			case 3:
				if(end_expression==null){
					System.out.println("Error There is no expression!");//��û�б��ʽ��������޷���
				}
				else{//���˱��ʽ
					//����Ҫ���󵼵ı�������� ����ֻ������ !d/d()���������ֵ "()"�ǲ��ܴ��ڵ� �ұ���ֻ������ĸ���
					VarDerivative=expression.charAt(4);//���Ҫ�󵼵ı��� �������ֻ���ǵ����ַ�
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
					derivative(xiang,VarDerivative,end_expression,fuhao);//����󵼺�Ľ�����ں��������
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
	public static String derivative(String[] itemCount,char var,String expression,ArrayList<String> fuhao){//�󵼳�ʼ��
		int varNum[];//�����ĸ���
		int xishu[];//���ϵ��
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
				for (int j=0;j<itemCount[i].length();j++){//��������ĸ���
					if(var==itemCount[i].charAt(j)){
						varNum[i]++;
					}
				}
				/*for (int j=0;j<itemCount[i].length();j++){//����ϵ��
					if(itemCount[i].charAt(j)>='0'&&itemCount[i].charAt(j)<='9'){
						xishu[i]=xishu[i]*10+(itemCount[i].charAt(j)-48);
					}
					else{
						xishu[i]=1;
					}
				}*/
				//System.out.println("����");
				//System.out.print(varNum[i]+" ");//����
				//System.out.println(xishu[i]);

			}
			/*for (int i=0;i<itemCount.length;i++){
				System.out.println("����");
				System.out.print(itemCount[i]);//����
			}*/


			for (int i=0;i<itemCount.length;i++){//�ϳ��󵼺����
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
			for (int i=0;i<newitemCount.size();i++){//���ϳɵ���������
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
	public static String[] splitby_jia(String str){//���ӷ��ָ�
		return str.split("\\+|\\-");
	}
	public static String[] splitby_cheng(String str){//���˷��ָ�
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
	public static String change(String expression,ArrayList<String> fuhao){//������ı��ʽ�����޸�
		String change_expression=null;
		String end_expression="";
		String[] str;
		String[] xiang;
		String newstr = null;
		String newxiang = null;
		int xishu=0;
		int fuhao1=0;//��ǵ��ڼ���������
		change_expression=expression.replace("\t", "");//��tabɾ��
		change_expression=change_expression.replace(" ", "");//���ո�ɾ��

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
		System.out.println(end_expression);
		for (int i=0;i<str.length;i++){//���η���ÿ����
			newstr=str[i];
			xiang=str[i].split("\\*");
			for(int j=0;j<xiang.length;j++){//��ÿ�������
				newxiang=xiang[j];
				System.out.println("newxiang1"+"   "+newxiang);
				for(int k=0;k<xiang[j].length();k++){
					if(xiang[j].charAt(k)=='^'){
						for (int l=k+1;l<xiang[j].length();l++){//����^�����ϵ���Ƕ���
							if(xiang[j].charAt(l)<'0'||xiang[j].charAt(l)>'9'){//���ִ���ֱ�ӷ��أ���judge�л��ж�Ϊ������ʽ
								return end_expression;
							}
							else{
								xishu=xishu*10+(xiang[j].charAt(l)-48);
							}
						}
						newxiang=newxiang.substring(0, newxiang.indexOf("^"));
						for (int n=0;n<k;n++){
							newxiang=xiang[n]+"*"+newxiang;
						}
						System.out.println("newxiang3"+"   "+newxiang);
						for (int m=0;m<xishu;m++){
							newxiang+="*"+xiang[j].substring(0, xiang[j].indexOf("^"));
						}
						System.out.println("newxiang3"+"   "+newxiang);
						for (int n=k+1;n<xiang.length;n++){
							newxiang+="*"+xiang[n];
						}
						xiang[j]=newxiang;
						System.out.println("newxiang3"+"   "+newxiang);
					}
				}
			}
			str[i]=newxiang;
			System.out.println(str[i]);
		}
		System.out.println("fuhao"+"   "+fuhao);
		System.out.println("str"+"   "+str.length);
		//ÿ����������ˣ��úϲ�����
		for (int i=0;i<str.length;i++){
			end_expression+=str[i];
			if(i<expression.length()-1){
				end_expression+=fuhao.get(fuhao1++);
			}
			System.out.println("end_expression"+"   "+end_expression);
		}
		return end_expression;
	}
	
    /**
     * �������expression�����ӺŲ��Ϊ�������ʽ�����沢����
     * ����ΪArrayList���͵����������ڻ���ʱ�����м��٣����ڴ���
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
    * @param factΪ����ʽ�е�����
    * @return �����ַ���1�����򷵻�0��
    */
    public static boolean isInteger(String fact)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(fact).matches();
    }
    
    /**
     * 
     * @param expression:���ʽ
     * @param items:����ʽ 
     * @return �������飬������1
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
     * �ж��Ƿ�Ϊͬ����
     * @param string1 ͬ����1
     * @param string2 ͬ����2
     * @return �Ƿ�Ϊͬ����
     * �㷨˼�룺����������ʽ�ֱ�*�ֿ��󣬽������������������������������ֵ������Ƚϼ��ɡ���ͬΪͬ�����ͬ���ͬ���
     */
    public static boolean isSameItem(String string1, String string2)
    {
		String[] factor1,factor2;
		factor1=string1.split("//*|(/d+)");//��*�Ż������ֲ��
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
     * * �������expression���м򻯣�����򻯽��
     * itemsΪ�ѷֿ��Ķ���ʽ��signArrΪ�������顣
     * �򻯰���ÿ�� ͬ����ϵ����ˣ�ͬ����ϲ���
     * @param expression
     * @param items
     * @param signArr
     * @return �����ı��ʽ
     */
    public static String makeSimple(String expression,ArrayList<String> items ,int[] signArr)
    {
        String[] factors = null;//�������ʽ������
        String newExpression = null;//�������ջ�����
        int[] newXishu  = new int[items.size()];//������ϵ������
        ArrayList<String> newItems = items;//�����Ķ���ʽ
        int tmpXishu = 0;//��ʱϵ��
        String tmpItem = null;//��ʱ����ʽ
        
        for (int i = 0; i < newXishu.length; i++)
        {
            newXishu[i]=1;
        }
        for (int i=0;i<newItems.size();i++)//��û������ʽ�ڲ���������˻���
        {
            factors = newItems.get(i).split("//*");//��*���������ʽ��ÿ������
            for (int j = 0; j < factors.length; j++)//����ϵ��
            {
                if (isInteger(factors[j]))//���������
                {
                    newXishu[i] *= Integer.parseInt(factors[j]);
                }
            }
        }
        for (int i=0;i<newItems.size();i++)//�ϲ�ͬ����
        {
        	for(int j=i+1;j<newItems.size();j++)
        	{
        		if(isSameItem(newItems.get(i),newItems.get(j)))//�����ͬ����
        		{
        			tmpXishu=signArr[i]*newXishu[i]+signArr[j]*newXishu[j];//�����µ�ϵ��
        			tmpItem=Integer.toString(tmpXishu);
        			
        			factors = newItems.get(i).split("//*");//��*���������ʽ��ÿ������
                    for (int k = 0; k < factors.length; k++)//���¶Զ���ʽ���
                    {
                        if (!isInteger(factors[j]))//�����������
                        {
                            tmpItem+=("*"+factors[k]);
                        }
                    }
                    newItems.set(i, tmpItem);//����iλ����
                    newItems.remove(j);//ɾ��jλ����
        		}
        	}
        }
        if(newItems.size()==0){
        	return "";
        }
        else{
        	newExpression=newItems.get(0);
	        for (int i = 1; i < newItems.size(); i++) {//�ϳ����ջ�����
	        	newExpression += "*" + newItems.get(i);
			}
        }
        return newExpression;
    }
	
}


