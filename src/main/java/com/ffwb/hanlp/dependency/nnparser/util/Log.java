/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/11/1 20:22</create-date>
 *
 * <copyright file="Log.java" company="��ũ��">
 * Copyright (c) 2008-2015, ��ũ��. All Right Reserved, http://www.ffwb.com/
 * This source is subject to ffwb. Please contact ffwb to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.dependency.nnparser.util;

/**
 * @author ffwb
 */
public class Log
{
    public static void ERROR_LOG(String format, Object ... args)
    {
        System.err.printf(format, args);
    }

    public static void INFO_LOG(String format, Object ... args)
    {
        System.err.printf(format, args);
    }
}
