/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>ffwb.cn@gmail.com</email>
 * <create-date>2014/11/19 18:53</create-date>
 *
 * <copyright file="CoNLLLoader.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.corpus.dependency.CoNll;

import com.ffwb.hanlp.corpus.io.IOUtil;

import java.util.LinkedList;

/**
 * CoNLL格式依存语料加载
 * @author ffwb
 */
public class CoNLLLoader
{
    public static LinkedList<CoNLLSentence> loadSentenceList(String path)
    {
        LinkedList<CoNLLSentence> result = new LinkedList<CoNLLSentence>();
        LinkedList<CoNllLine> lineList = new LinkedList<CoNllLine>();
        for (String line : IOUtil.readLineListWithLessMemory(path))
        {
            if (line.trim().length() == 0)
            {
                result.add(new CoNLLSentence(lineList));
                lineList = new LinkedList<CoNllLine>();
                continue;
            }
            lineList.add(new CoNllLine(line.split("\t")));
        }

        return result;
    }
//    public static void main(String[] args)
//    {
//        LinkedList<CoNLLSentence> coNLLSentences = CoNLLLoader.loadSentenceList("D:\\Doc\\语料库\\依存分析训练数据\\THU\\dev.conll.fixed.txt");
//        System.out.println(coNLLSentences.getFirst());
//    }
}
