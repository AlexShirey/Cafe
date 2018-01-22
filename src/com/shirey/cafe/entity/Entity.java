package com.shirey.cafe.entity;

import java.io.Serializable;

public abstract class Entity implements Cloneable, Serializable {

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
