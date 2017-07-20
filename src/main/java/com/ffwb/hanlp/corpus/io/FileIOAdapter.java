/*
 * <summary></summary>
 * <author>ffwb</author>
 * <email>me@ffwb.com</email>
 * <create-date>2016-09-07 PM4:42</create-date>
 *
 * <copyright file="FileIOAdapter.java" company="码农场">
 * Copyright (c) 2008-2016, 码农场. All Right Reserved, http://www.ffwb.com/
 * This source is subject to ffwb. Please contact ffwb to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.corpus.io;

import java.io.*;

/**
 * 基于普通文件系统的IO适配器
 *
 * @author ffwb
 */
public class FileIOAdapter implements IIOAdapter
{
    @Override
    public InputStream open(String path) throws FileNotFoundException
    {
        return new FileInputStream(path);
    }

    @Override
    public OutputStream create(String path) throws FileNotFoundException
    {
        return new FileOutputStream(path);
    }
}
