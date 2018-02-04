package com.shirey.cafe.entity;

import java.io.Serializable;

/**
 * The {@code Entity} class
 * is a superclass for all entities in this project.
 * <p>
 * Implements Cloneable, Serializable, so every entity implements this interfaces too.
 *
 * @author Alex Shirey
 */

public abstract class Entity implements Cloneable, Serializable {

    /**
     * Overrides original method clone() and makes access public.
     *
     * @return a clone of Entity instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
