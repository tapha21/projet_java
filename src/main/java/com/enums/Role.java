package com.enums;

public enum Role {
    ADMIN , CLIENT;

    public  static Role getValue(String value){
        for (Role f: Role.values()) {
             if (f.name().compareToIgnoreCase(value)==0) {
                   return f; 
             }
        }
        return null;
     }
}
