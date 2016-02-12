package com.ecip.msdp.ecitest1;

/**
 * Created by msdg on 12-02-2016.
 */
public class ElectoralDistrictClass {
    String id,dist,pc,show;

    public ElectoralDistrictClass() {
    }

    public ElectoralDistrictClass(String id, String dist, String pc, String show) {
        this.id = id;
        this.dist = dist;
        this.pc = pc;
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "ElectoralDistrictClass{" +
                "id='" + id + '\'' +
                ", dist='" + dist + '\'' +
                ", pc='" + pc + '\'' +
                ", show='" + show + '\'' +
                '}';
    }
}
