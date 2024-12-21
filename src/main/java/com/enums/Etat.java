package com.enums;

public enum Etat {
    Activer,
    Desactiver;

    public  static Etat getValue(String value){
        for (Etat f: Etat.values()) {
             if (f.name().compareToIgnoreCase(value)==0) {
                   return f; 
             }
        }
        return null;
     }
}
