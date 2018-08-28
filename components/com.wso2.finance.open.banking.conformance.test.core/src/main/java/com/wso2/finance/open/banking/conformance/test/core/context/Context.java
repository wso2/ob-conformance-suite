package com.wso2.finance.open.banking.conformance.test.core.context;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/*
 * ClassName : Context
 *
 * Contains a list of FeatureAttributeSet objects.
 * Returns a FeatureAttributeSet when the feature name is given.
 *
 */

public class Context {

    private static final Context contextInstance = new Context();
    private ArrayList<FeatureAttributeSet> attributes = new ArrayList<FeatureAttributeSet>();

    private Context(){}

    public static Context getContext(){
        return contextInstance;
    }

    public void addFeatureAttribSet(FeatureAttributeSet attribSet){

        attributes.add(attribSet);
    }

    public FeatureAttributeSet getAttributeSet(String featureName) throws NoSuchElementException {
        int len=attributes.size();
        for(int i=0; i<len; i++) {
            if (attributes.get(i).getName().equals(featureName)){
                return attributes.get(i);
            }
        }
        throw new NoSuchElementException("Feature with given name not found.");
    }

}
