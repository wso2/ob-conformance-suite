package com.wso2.finance.open.banking.conformance.test.core.context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * ClassName : FeatureAttributeSet
 *
 * Maintains the attributes of the feature in the given
 * JSON object.
 *
 * A scenario object (JSONObject) can be requested by giving the respective scenario ID.
 *
 */
public class FeatureAttributeSet {

    private String featureName;
    private String featureURL;
    private JSONObject attribContainer;
    private JSONArray scenarioArray;

    public FeatureAttributeSet(JSONObject object){
        this.attribContainer = object;
        this.featureName = attribContainer.get("name").toString();
        this.featureURL = attribContainer.get("url").toString();
        this.scenarioArray = (JSONArray)attribContainer.get("scenarios");
    }

    public String getName(){
        return this.featureName;
    }

    public String getURL(){
        return this.featureURL;
    }

    public JSONObject getScenarioObject(String scenarioID){
        int size = scenarioArray.size();
        JSONObject currentObject;

        for (int i = 0; i < size; i++) {
            currentObject = (JSONObject) scenarioArray.get(i);

            if(currentObject.get("id").toString().equals(scenarioID)){
                return currentObject;
            }
        }
        return null;
    }
}
