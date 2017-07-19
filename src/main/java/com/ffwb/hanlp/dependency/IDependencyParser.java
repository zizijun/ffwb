/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/11/10 20:13</create-date>
 *
 * <copyright file="IDependencyParser.java" company="码农场">
 * Copyright (c) 2008-2015, 码农场. All Right Reserved, http://www.ffwb.com/
 * This source is subject to ffwb. Please contact ffwb to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.dependency;

import com.ffwb.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.ffwb.hanlp.seg.Segment;
import com.ffwb.hanlp.seg.common.Term;

import java.util.List;
import java.util.Map;

/**
 * 依存句法分析器接口
 *
 * @author ffwb
 */
public interface IDependencyParser
{
    /**
     * 分析句子的依存句法
     *
     * @param termList 句子，可以是任何具有词性标注功能的分词器的分词结果
     * @return CoNLL格式的依存句法树
     */
    CoNLLSentence parse(List<Term> termList);

    /**
     * 分析句子的依存句法
     *
     * @param sentence 句子
     * @return CoNLL格式的依存句法树
     */
    CoNLLSentence parse(String sentence);

    /**
     * 获取Parser使用的分词器
     *
     * @return
     */
    Segment getSegment();

    /**
     * 设置Parser使用的分词器
     *
     * @param segment
     */
    IDependencyParser setSegment(Segment segment);

    /**
     * 获取依存关系映射表
     *
     * @return
     */
    Map<String, String> getDeprelTranslator();

    /**
     * 设置依存关系映射表
     *
     * @param deprelTranslator
     */
    IDependencyParser setDeprelTranslator(Map<String, String> deprelTranslator);

    /**
     * 依存关系自动转换开关
     * @param enable
     */
    IDependencyParser enableDeprelTranslator(boolean enable);
}
