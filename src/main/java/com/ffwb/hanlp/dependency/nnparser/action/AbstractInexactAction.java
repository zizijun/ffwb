/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/10/31 20:30</create-date>
 *
 * <copyright file="AbstractInexactAction.java" company="��ũ��">
 * Copyright (c) 2008-2015, ��ũ��. All Right Reserved, http://www.ffwb.com/
 * This source is subject to ffwb. Please contact ffwb to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.dependency.nnparser.action;

/**
 * @author ffwb
 */
public class AbstractInexactAction implements Comparable<AbstractInexactAction>
{
    int seed;

    public AbstractInexactAction()
    {
    }

    /**
     * Constructor for inexact action. Empirically, the number of name
     * is less than 32. So such inexact action type compile the action
     * name and action type into a single integer.
     *
     * @param name The name for the action.
     * @param rel  The dependency relation.
     */
    AbstractInexactAction(int name, int rel)
    {
        seed = rel << 6 | name;
    }

    public int compareTo(AbstractInexactAction o)
    {
        return new Integer(seed).compareTo(o.seed);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AbstractInexactAction)) return false;
        AbstractInexactAction o = (AbstractInexactAction) obj;
        return seed == o.seed;
    }

    public int name()
    {
        return (seed & 0x3f);
    }

    public int rel()
    {
        return (seed >> 6);
    }
}
