package com.sensing.core.test;

import java.util.HashMap;
import java.util.Map;

public class test {

	public static String charReverse(String s) {
		String reverse = "";
		for (int i = 0; i < s.length(); i++) {
			reverse = s.charAt(i) + reverse;
		}
		return reverse;
	}

	public static String reverseCharArray(String s) {
		char[] charArray = s.toCharArray();
		String reverse = "";
		for (int i = charArray.length - 1; i >= 0; i--) {
			reverse += charArray[i];
		}
		return reverse;
	}

	public static String reverseStringbuilder(String s) {
		StringBuilder sb = new StringBuilder(s);
		String reverse = sb.reverse().toString();
		return reverse;
	}

//	public int hashCode() {
//		int h = hash;
//		if(h == 0 && value.length >0) {
//			char val[] = value;
//			
//			for(int i = 0; i < value.length;i++) {
//				h = 31*h+val[i];
//			}
//			hash = h;
//		}
//		
//		return h;

//	}

	public static void main(String[] args) {

//		byte b1 = 1, b2 = 2, b3, b6, b8;
//
//		final byte b4 = 4, b5 = 6, b7;
//		
		byte b = 4 + 5;
//		
//		
//		b3 = (b1 + b2);
//		b6 = (b4 + b5);
//		b8 = (b1 + b4);
//		b7 = (b2 + b5);
//
//		System.out.println(b3);
		System.out.println(b);
//		System.out.println(b8);
//		System.out.println(b7);

		String string = reverseStringbuilder("asdfgtrewq");
//		String string = reverseCharArray("asdfgtrewq");
//		String string = charReverse("asdfgtrewq");
		System.out.println(string);

//		 long r = Math.round(-1.5) ;
//		 System.out.println(r);

//		Stu s1 = new Stu("xiaoming", 11);
//
////		Stu s2 = s1;
//		Stu s2 = new Stu("xiaoming", 11);
//
//		System.out.println(s1);
//		System.out.println(s2);
////
//		if (s1 == s2) {
//			System.out.println("s1==s2");
//		}
//
//		if (s1.equals(s2)) {
//			System.out.println("s1.equals(s2)");
//		}

//		String a = new String("abcd");
//		String b = new String("abcd");
//		String c = "abcd";
//		String d = "abcd";
//
//		if (a == b) {
//			System.out.println("a==b");
//		}
//		if (a.equals(b)) {
//			System.out.println("a.equals(b)");
//		}
//		if (c == d) {
//			System.out.println("c==d");
//		}
//		if (c.equals(d)) {
//			System.out.println("c.equals(d)");
//		}

//		Stu s = new Stu();
//		
//		System.out.println(s);

//		Stu s1 = new Stu("19",18);
//		Stu s2 = new Stu("19",18);
////		Stu s2 = new Stu("18",19);
//		System.out.println("s1的hashcode:"+s1.hashCode());
//		System.out.println("s2的hashcode:"+s2.hashCode());
//		System.out.println("equals:"+s1.equals(s2));
//		
//		Map<Stu,Integer> map = new HashMap<Stu,Integer>(); 
//		
//		map.put(new Stu("xiamoming",11),11);
//		Integer val = map.get(new Stu("xiamoming",11));
//		System.out.println(val);

//		String a = "JAVA虚拟机(JVM)中存在着一个字符串池，其中保存着很多String对象; 符串池由String类维护，我们可以调用intern()方法来访问字符串池。并且可以被共享使用，因此它提高了效率JAVA虚拟机(JVM)中存在着一个字符串池，其中保存着很多String对象; 符串池由String类维护，我们可以调用intern()方法来访问字符串池。并且可以被共享使用，因此它提高了效率  ";
//		System.out.println(a.hashCode());

//        /** 
//         * 情景一：字符串池 
//         * JAVA虚拟机(JVM)中存在着一个字符串池，其中保存着很多String对象; 
//         * 并且可以被共享使用，因此它提高了效率。 
//         * 由于String类是final的，它的值一经创建就不可改变。 
//         * 字符串池由String类维护，我们可以调用intern()方法来访问字符串池。  
//         */  
//        String s1 = "abc";     
//        //↑ 在字符串池创建了一个对象  
//        String s2 = "abc";     
//        //↑ 字符串pool已经存在对象“abc”(共享),所以创建0个对象，累计创建一个对象  
//        System.out.println("s1 == s2 : "+(s1==s2));    
//        //↑ true 指向同一个对象，  
//        System.out.println("s1.equals(s2) : " + (s1.equals(s2)));    
//        //↑ true  值相等  
//        //↑------------------------------------------------------over  
//        /** 
//         * 情景二：关于new String("") 
//         *  
//         */  
//        String s3 = new String("abc");  
//        //↑ 创建了两个对象，一个存放在字符串池中，一个存在与堆区中；  
//        //↑ 还有一个对象引用s3存放在栈中  
//        String s4 = new String("abc");  
//        //↑ 字符串池中已经存在“abc”对象，所以只在堆中创建了一个对象  
//        System.out.println("s3 == s4 : "+(s3==s4));  
//        //↑false   s3和s4栈区的地址不同，指向堆区的不同地址；  
//        System.out.println("s3.equals(s4) : "+(s3.equals(s4)));  
//        //↑true  s3和s4的值相同  
//        System.out.println("s1 == s3 : "+(s1==s3));  
//        //↑false 存放的地区多不同，一个栈区，一个堆区  
//        System.out.println("s1.equals(s3) : "+(s1.equals(s3)));  
//        //↑true  值相同  
//        //↑------------------------------------------------------over  
//        /** 
//         * 情景三：  
//         * 由于常量的值在编译的时候就被确定(优化)了。 
//         * 在这里，"ab"和"cd"都是常量，因此变量str3的值在编译时就可以确定。 
//         * 这行代码编译后的效果等同于： String str3 = "abcd"; 
//         */  
//        String str1 = "ab" + "cd";  //1个对象  
//        String str11 = "abcd";   
//        System.out.println("str1 = str11 : "+ (str1 == str11));  
//        //↑------------------------------------------------------over  
//        /** 
//         * 情景四：  
//         * 局部变量str2,str3存储的是存储两个拘留字符串对象(intern字符串对象)的地址。 
//         *  
//         * 第三行代码原理(str2+str3)： 
//         * 运行期JVM首先会在堆中创建一个StringBuilder类， 
//         * 同时用str2指向的拘留字符串对象完成初始化， 
//         * 然后调用append方法完成对str3所指向的拘留字符串的合并， 
//         * 接着调用StringBuilder的toString()方法在堆中创建一个String对象， 
//         * 最后将刚生成的String对象的堆地址存放在局部变量str3中。 
//         *  
//         * 而str5存储的是字符串池中"abcd"所对应的拘留字符串对象的地址。 
//         * str4与str5地址当然不一样了。 
//         *  
//         * 内存中实际上有五个字符串对象： 
//         *       三个拘留字符串对象、一个String对象和一个StringBuilder对象。 
//         */  
//        String str2 = "ab";  //1个对象  
//        String str3 = "cd";  //1个对象                                         
//        String str4 = str2+str3;                                        
//        String str5 = "abcd";    
//        System.out.println("str4 = str5 : " + (str4==str5)); // false  
//        //↑------------------------------------------------------over  
//        /** 
//         * 情景五： 
//         *  JAVA编译器对string + 基本类型/常量 是当成常量表达式直接求值来优化的。 
//         *  运行期的两个string相加，会产生新的对象的，存储在堆(heap)中 
//         */  
//        String str6 = "b";  
//        String str7 = "a" + str6;  
//        String str67 = "ab";  
//        System.out.println("str7 = str67 : "+ (str7 == str67));  
//        //↑str6为变量，在运行期才会被解析。  
//        final String str8 = "b";  
//        String str9 = "a" + str8;  
//        String str89 = "ab";  
//        System.out.println("str9 = str89 : "+ (str9 == str89));  
//        //↑str8为常量变量，编译期会被优化  
//        //↑------------------------------------------------------over  
	}

	public void insertSort(int[] array) {
		int len = array.length;
		int numtoinsert;
		int j;
		for (int i = 1; i < len; i++) {
			numtoinsert = array[i];
			// 把i之前大于array[i]的数往后移一位
			for (j = i - 1; j >= 0 && array[j] > numtoinsert; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = numtoinsert;
		}
	}

//	 public boolean equals(Object obj)
//	    {
//	        if(this == obj)
//	            return true;
//	        if(obj instanceof String)
//	        {
//	            String s = (String)obj;
//	            int i = value.length;
//	            if(i == s.value.length)
//	            {
//	                char ac[] = value;
//	                char ac1[] = s.value;
//	                for(int j = 0; i-- != 0; j++)
//	                    if(ac[j] != ac1[j])
//	                        return false;
//
//	                return true;
//	            }
//	        }
//	        return false;
//	    }
//	 
//	 public int hashCode()
//	    {
//	        int i = hash;
//	        if(i == 0 && value.length > 0)
//	        {
//	            char ac[] = value;
//	            for(int j = 0; j < value.length; j++)
//	                i = 31 * i + ac[j];
//
//	            hash = i;
//	        }
//	        return i;
//	    }
}
