package org.wso2.finance.open.banking.conformance.mgt.models;

import java.util.Map;

public class Step {

    private Map<String, String> result;
    private int line;
    private String name;
    private Map<String, String> match;
    private String keyword;

    public Step(Map<String, String> result, int line, String name, Map<String, String> match, String keyword){
        this.result = result;
        this.line = line;
        this.name = name;
        this.match = match;
        this.keyword = keyword;
    }

    /**
     * @return
     */
    public Map<String, String> getresults() {
        return result;
    }

    /**
     * @return
     */
    public int getLine() {

        return line;
    }

    /**
     * @return
     */
    public String getName() {

        return name;
    }

    /**
     * @return
     */
    public Map<String, String> getMatch() {

        return match;
    }

    /**
     * @return
     */
    public String getKeyword() {

        return keyword;
    }

    @Override
    public String toString() {
        return "Step [results =" + result + "line =" + line + "name =" + name + "match =" + match + "keyword =" + keyword +"]";
    }

}