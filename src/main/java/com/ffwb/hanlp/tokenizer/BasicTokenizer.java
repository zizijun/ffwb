/*
 * <summary></summary>
 * <author>ffwb</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/5/4 23:36</create-date>
 *
 * <copyright file="BasicTokenizer.java">
 * Copyright (c) 2003-2015, ffwb. All Right Reserved, http://www.ffwb.com/
 * </copyright>
 */
package com.ffwb.hanlp.tokenizer;

import com.ffwb.hanlp.HanLP;
import com.ffwb.hanlp.seg.Segment;
import com.ffwb.hanlp.seg.common.Term;

import java.util.List;

/**
 * 基础分词器，只做基本NGram分词，不识别命名实体，不使用用户词典
 * @author ffwb
 */
public class BasicTokenizer
{
    /**
     * 预置分词器
     */
    public static final Segment SEGMENT = HanLP.newSegment().enableAllNamedEntityRecognize(false).enableCustomDictionary(false);

    /**
     * 分词
     * @param text 文本
     * @return 分词结果
     */
    public static List<Term> segment(String text)
    {
        return SEGMENT.seg(text.toCharArray());
    }

    /**
     * 分词
     * @param text 文本
     * @return 分词结果
     */
    public static List<Term> segment(char[] text)
    {
        return SEGMENT.seg(text);
    }

    /**
     * 切分为句子形式
     * @param text 文本
     * @return 句子列表
     */
    public static List<List<Term>> seg2sentence(String text)
    {
        return SEGMENT.seg2sentence(text);
    }
}
