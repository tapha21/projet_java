package com.core.config.repoImpl;
import java.util.List;

import com.core.config.Repository;

import java.util.ArrayList;

public abstract class RepositoryImpl <T> implements Repository <T> {
protected List<T> list=new ArrayList<>();

@Override
public void insert(T objet){
    list.add(objet);
}

@Override
public List<T> selectAll(){
    return list;
}
}

   

