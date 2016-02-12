package com.ecip.msdp.ecitest1;

/**
 * Created by msdg on 12-02-2016.
 */
public class ElectoralStateClass {
    String id,name;

    public ElectoralStateClass() {
        id="";
        name="";
    }

    public ElectoralStateClass(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ElectoralStateClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
