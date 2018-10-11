package org.wso2.finance.open.banking.conformance.mgt.models;

import java.util.List;

public class Result {

    private int line;
    private List<FeatureElementItem> elements;
    private String name;
    private String description;
    private String id;
    private String keyword;
    private String uri;
    private List<Tag> tags;

    public Result(int line, List<FeatureElementItem> elements, String name, String description,
                   String id, String keyword, String uri, List<Tag> tags) {

        this.line = line;
        this.elements = elements;
        this.name = name;
        this.description = description;
        this.id = id;
        this.keyword = keyword;
        this.uri = uri;
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
    public List<FeatureElementItem> getElements(){
        return  elements;
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
    public String getKeyword() {

        return keyword;
    }

    /**
     * @return
     */
    public String getUri() {

        return uri;
    }

    /**
     * @return
     */
    public List<Tag> getTags() {
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
        return "Result [line=" + line + ", name=" + name + "elements = " + elements + ", description=" + description
                + ", URI=" + uri +  "tags = " + tags + "]";
    }

}
