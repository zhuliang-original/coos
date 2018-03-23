package top.coos.tool.string;

import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class StringExecute {

    public static Object invokeMethod(String jexlExp, Map<String, Object> map) {
        if (!StringHelper.isEmpty(jexlExp)) {
            Boolean isNumber = jexlExp.matches("^[-\\+]?[\\d]*[\\.]?[\\d]*$");
            if (isNumber) {
                return jexlExp;
            }
        }
        JexlEngine jexl = new JexlEngine();
        Expression e = jexl.createExpression(jexlExp);
        JexlContext jc = new MapContext();
        for (String key : map.keySet()) {
            jc.set(key, map.get(key));
        }
        Object value = e.evaluate(jc);
        return value;
    }
}
