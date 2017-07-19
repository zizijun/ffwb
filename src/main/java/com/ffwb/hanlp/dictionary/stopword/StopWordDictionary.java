/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>ffwb.cn@gmail.com</email>
 * <create-date>2014/9/15 19:38</create-date>
 *
 * <copyright file="StopwordDictionary.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.dictionary.stopword;

import com.ffwb.hanlp.collection.MDAG.MDAGSet;
import com.ffwb.hanlp.seg.common.Term;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author ffwb
 */
public class StopWordDictionary extends MDAGSet implements Filter
{
    public StopWordDictionary(File file) throws IOException
    {
        super(file);
    }

    public StopWordDictionary(Collection<String> strCollection)
    {
        super(strCollection);
    }

    public StopWordDictionary()
    {
    }

    @Override
    public boolean shouldInclude(Term term)
    {
        return contains(term.word);
    }
}
