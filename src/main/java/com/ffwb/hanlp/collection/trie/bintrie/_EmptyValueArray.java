/*
 * <summary></summary>
 * <author>ffwb</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/5/15 10:24</create-date>
 *
 * <copyright file="EmptyValueArray.java">
 * Copyright (c) 2003-2015, ffwb. All Right Reserved, http://www.ffwb.com/
 * </copyright>
 */
package com.ffwb.hanlp.collection.trie.bintrie;

/**
 * @author ffwb
 */
public class _EmptyValueArray<V> extends _ValueArray<V>
{
    public _EmptyValueArray()
    {
    }

    @Override
    public V nextValue()
    {
        return null;
    }
}
