package com.wso2.finance.open.banking.conformance.test.core.context;

import java.util.ArrayList;

public class Context {

    private ArrayList<FeatureAttributeSet> attributes = new ArrayList<FeatureAttributeSet>();

    public void addFeatureAttribSet(FeatureAttributeSet attribSet){

        attributes.add(attribSet);
    }

    public FeatureAttributeSet getAttributeSet(String featureName){
        int len=attributes.size();
        for(int i=0; i<len; i++) {
            if (attributes.get(i).getName().equals(featureName)){
                return attributes.get(i);
            }
        }
        return null;
    }

}
