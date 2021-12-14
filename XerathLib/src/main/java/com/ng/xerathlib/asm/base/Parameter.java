package com.ng.xerathlib.asm.base;

public class Parameter {

    public final String name;
    public final String desc;
    public final int index;

    public Parameter(String name, String desc, int index){
        this.name = name;
        this.desc = desc;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", index=" + index +
                '}';
    }
}
