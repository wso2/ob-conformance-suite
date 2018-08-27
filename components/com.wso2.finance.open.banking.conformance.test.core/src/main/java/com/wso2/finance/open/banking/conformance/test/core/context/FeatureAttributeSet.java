package com.wso2.finance.open.banking.conformance.test.core.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONObject;

public class FeatureAttributeSet {

    private String featureName;
    private String featureURL;
    private JSONObject attribContainer = new JSONObject();
    private Map<String, String> reqGeneterationAttribMap = new HashMap<String, String>();
    private Map<String, String> respValidationAttribMap = new HashMap<String, String>();

    public FeatureAttributeSet(JSONObject jo){
        this.attribContainer = jo;
        this.featureName = jo.get("name").toString();
        this.featureURL = jo.get("url").toString();

    }

    public String getName(){
        return this.featureName;
    }

    public String getURL(){
        return this.featureURL;
    }

    public String getAttribute(String type, String key){
        Map curMap = ((Map)attribContainer.get(type)); //type can be either 'request' or 'response'.

        Iterator<Map.Entry> itr = curMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            if(pair.getKey().equals(key)){
                return pair.getValue().toString();
            }

        }
        return null;
    }



}
