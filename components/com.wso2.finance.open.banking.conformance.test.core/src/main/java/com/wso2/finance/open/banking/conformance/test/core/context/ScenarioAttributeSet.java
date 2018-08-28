package com.wso2.finance.open.banking.conformance.test.core.context;

import org.json.simple.JSONObject;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/*
 * ClassName : ScenarioAttributeSet
 *
 * Maintains the attributes of a scenario, when a
 * scenario object (JSONObject) is given.
 *
 * Attributes can be requested by giving type, container, and key variables.
 *
 */
public class ScenarioAttributeSet {

    private String scenarioName;
    private JSONObject scenarioObject;
    private JSONObject requestObject;
    private JSONObject responseObject;
    private Map<String, String> reqHeaderAttribMap = new HashMap<String, String>();
    private Map<String, String> reqBodyAttribMap = new HashMap<String, String>();
    private Map<String, String> respHeaderAttribMap = new HashMap<String, String>();
    private Map<String, String> respBodyAttribMap = new HashMap<String, String>();

    public ScenarioAttributeSet(JSONObject object){
        this.scenarioObject = object;
        this.scenarioName = scenarioObject.get("name").toString();
        this.requestObject = (JSONObject)scenarioObject.get("request");
        this.responseObject = (JSONObject)scenarioObject.get("response");
        this.reqHeaderAttribMap = ((Map)requestObject.get("header"));
        this.reqBodyAttribMap = ((Map)requestObject.get("body"));
        this.respHeaderAttribMap = ((Map)responseObject.get("header"));
        this.respBodyAttribMap = ((Map)responseObject.get("body"));
    }

    public String getName(){
        return this.scenarioName;
    }

    /*
     * Returns the requested attribute.
     * type can be either 'request' or 'response'.
     * container can be either 'header' or 'body'.
     */
    public String getAttribute(String type, String container, String key) throws InvalidParameterException, NoSuchElementException {
        Map curMap;

        if(type.equals("request") && container.equals("header")){
            curMap = reqHeaderAttribMap;
        }
        else if(type.equals("request") && container.equals("body")){
            curMap = reqBodyAttribMap;
        }
        else if(type.equals("response") && container.equals("header")){
            curMap = respHeaderAttribMap;
        }
        else if(type.equals("response") && container.equals("body")){
            curMap = respBodyAttribMap;
        }
        else{
            throw new InvalidParameterException("Call with correct 'type' and 'container' parameters");
        }

        Iterator<Map.Entry> itr = curMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            if(pair.getKey().equals(key)){
                return pair.getValue().toString();
            }
        }

        throw new NoSuchElementException("Key not found");
    }
}
