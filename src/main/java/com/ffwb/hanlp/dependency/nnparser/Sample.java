/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/11/1 21:27</create-date>
 *
 * <copyright file="Sample.java" company="��ũ��">
 * Copyright (c) 2008-2015, ��ũ��. All Right Reserved, http://www.ffwb.com/
 * This source is subject to ffwb. Please contact ffwb to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.dependency.nnparser;

import java.util.List;

/**
 * @author ffwb
 */
public class Sample
{
    List<Integer> attributes;  //! sparse vector of attributes
    List<Double> classes;  //! dense vector of classes

    public Sample()
    {
    }

    public Sample(List<Integer> attributes, List<Double> classes)
    {
        this.attributes = attributes;
        this.classes = classes;
    }
}
