package experiment_1;
import java.util.ArrayList;

public class Expressions {
	 //属性
    String expression = "";//保存原表达式串
    ArrayList<String> items = new ArrayList<String>();//保存所有的多项式
    ArrayList<String> vars = new ArrayList<String>();//保存所有的变量
    ArrayList<String> Cofficients = new ArrayList<String>();//保存每个多项式的系数

    Expressions(String inputstr)
    /**
     * 构造函数
     */
    {
    	expression = inputstr;
    }

    void simplify()
    /**
     * 简化表达式
     */
    {

    }

    int[] varCount(char x)
    /**
     * 接受变量名，计算每个多项式中变量x的个数记录到ArrayList中并返回
     */
    {
		return null;

    }
    
    ArrayList<String> getItems()
    /**
     * 获取多项式
     */
    {
    	return null;
    }

}
