package cn.jd.interfacedemo.autofun;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String name = "呵呵\\...";
        System.out.println(name.replaceAll("\\.","/"));
        
		StringBuilder sb = new StringBuilder();
		sb.append("[");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","test");
        map.put("3", "num");
        map.put("false", "boolean");
        Set<String> key = map.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (s != "") {
				sb.append(s).append("=").append(map.get(s));
				sb.append(",");
				// System.out.println(map.get(s));
			}
		}
        sb.append("]");
		// System.out.println(sb.toString());
        System.out.println(sb.toString());
    }
}
