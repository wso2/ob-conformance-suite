package org.wso2.finance.open.banking.conformance.mgt.models;

import java.util.List;

public class FeatureElementItem {

    private int line;
    private String name;
    private String description;
    private String id;
    private String type;
    private String keyword;
    private List<Step> steps;
    private List<Tag> tags;

    public FeatureElementItem(int line, String name, String description, String id, String type,
                  String keyword, List<Step> steps, List<Tag> tags) {

        this.line = line;
        this.name = name;
        this.description = description;
        this.id = id;
        this.type = type;
        this.keyword = keyword;
        this.steps = steps;
        this.tags = tags;
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
    public String getDescription() {

        return description;
    }

    /**
     * @return
     */
    public String getId() {

        return id;
    }

    /**
     * @return
     */
    public String getType() {

        return type;
    }

    /**
     * @return
     */
    public String getKeyword() {

        return keyword;
    }

    /**
     * @return
     */
    public List<Step> getSteps(){
        return steps;
    }

    /**
     * @return
     */
    public  List<Tag> getTags() {
        return tags;
    }

    public class Tag{
        private String name;

        public Tag(String name){
            this.name = name;
        }

        /**
         * @return
         */
        public  String getTagName() {
            return name;
        }

        @Override
        public String toString() {
            return "Tag [name=" + name + "]";
        }
    }

    @Override
    public String toString() {
        return "FeatureElementItem [line=" + line + ", name=" + name + ", description=" + description + ", id=" + id +
               ", type =" + type + ", keywords=" + keyword + "steps = " + steps + "tags = " + tags + "]";
    }
}
