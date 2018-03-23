package top.coos.servlet.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.coos.exception.CoreException;
import top.coos.tool.string.StringHelper;

public class RequestMapping extends Mapping {

    private String path;

    private String rule;

    private List<String> patterns;

    public String getPath() {
        return path;
    }

    public void setPath(String path, List<String> patterns) throws CoreException {
        this.path = path;
        this.patterns = patterns;
        resolve();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    private Map<Integer, String> index_param_map = new HashMap<Integer, String>();

    private List<String> groups;

    private void resolve() throws CoreException {
        index_param_map = new HashMap<Integer, String>();
        groups = new ArrayList<String>();
        rule = "/" + path;
        if (StringHelper.findCount(rule, "{") != StringHelper.findCount(rule, "}")) {
            throw new CoreException(controllerClass + " " + path + " resolve error.");
        }

        if (patterns != null && patterns.size() > 0 && rule.indexOf(".") < 0) {
            String patternRule = "";
            for (String pattern : patterns) {
                if (pattern.indexOf("*.") == 0) {
                    patternRule += "|(" + pattern.replaceFirst("\\*.", "\\\\.") + ")";
                }
            }
            if (patternRule.length() > 0) {
                patternRule = patternRule.substring(1, patternRule.length());
                patternRule = "(" + patternRule + ")";
                rule = rule + patternRule;
            }
        }
        rule = rule.replaceAll("[/]+", "/");
        Pattern pattern = Pattern.compile("[{]" + validString + "+[}]");
        Matcher matcher = pattern.matcher(rule);
        while (matcher.find()) {
            String param = matcher.group();
            String paramName = param.replace("{", "").replace("}", "");
            int index = rule.indexOf(param);
            index_param_map.put(index, paramName);
            rule = rule.replaceFirst("\\{" + paramName + "\\}", "*");

        }
        pattern = Pattern.compile("/[\\*]+/");
        matcher = pattern.matcher(rule);
        while (matcher.find()) {
            String param = matcher.group();
            int count = StringHelper.findCount(param, "*");
            String newString = "/*";
            for (int i = 0; i < count - 1; i++) {
                newString += "/*";
            }
            newString += "/";
            rule = rule.replaceFirst("/[\\*]+/", newString);
        }
        rule = rule.replaceAll("[/]+", "/");

        pattern = Pattern.compile("[*]");
        matcher = pattern.matcher(rule);
        int index = 0;
        String validString_ = validString + "+";
        while (matcher.find()) {
            // String param = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            if (index != start) {
                groups.add(rule.substring(index, start));
            }
            index = end;
            groups.add(rule.substring(start, index));
        }
        if (index < rule.length()) {
            groups.add(rule.substring(index));
        }
        rule = rule.replaceAll("\\*", validString_);

    }

    private String validString = "[a-z|A-Z|0-9_|\\\\-|\\\\.|+|=]";

    private Map<String, String> param_value_map;

    public boolean validata(String pathinfo) {
        param_value_map = new HashMap<String, String>();
        boolean matches = pathinfo.matches(rule);
        if (matches && index_param_map.size() > 0) {
            String pathstr = pathinfo;
            List<String> infos = new ArrayList<String>();
            for (int i = 0; i < groups.size(); i++) {
                String group = groups.get(i);
                int start = 0;
                int end = group.length();
                if (group.indexOf("((") == 0) {
                    end = pathstr.length();
                }
                if (group.equals("*")) {
                    String nextgroup = null;
                    if (i < groups.size() - 1) {
                        nextgroup = groups.get(i + 1);
                    }
                    if (!StringHelper.isEmpty(nextgroup)) {
                        end = pathstr.indexOf(nextgroup);
                    }
                    if (nextgroup.indexOf("((") == 0) {
                        end = pathstr.indexOf(".");
                    }
                } else {

                }
                String thisinfo = pathstr.substring(start, end);
                pathstr = pathstr.replaceFirst(thisinfo, "");
                infos.add(thisinfo);
            }
            for (int index : index_param_map.keySet()) {
                String param = index_param_map.get(index);
                int count = 0;
                for (int groupsize = 0; groupsize < groups.size(); groupsize++) {
                    String group = groups.get(groupsize);
                    if (count == index) {
                        String value = infos.get(groupsize);
                        param_value_map.put(param, value);
                    }
                    count += group.length();
                }
            }
        }
        return matches;
    }

    public Map<String, String> getParam_value_map() {
        return param_value_map;
    }

}
