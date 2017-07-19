/*
 * <summary></summary>
 * <author>ffwb</author>
 * <email>me@ffwb.com</email>
 * <create-date>2015/5/7 11:04</create-date>
 *
 * <copyright file="HMMSegment.java">
 * Copyright (c) 2003-2015, ffwb. All Right Reserved, http://www.ffwb.com/
 * </copyright>
 */
package com.ffwb.hanlp.seg.HMM;

import com.ffwb.hanlp.HanLP;
import com.ffwb.hanlp.corpus.io.ByteArray;
import com.ffwb.hanlp.model.trigram.CharacterBasedGenerativeModel;
import com.ffwb.hanlp.seg.CharacterBasedGenerativeModelSegment;
import com.ffwb.hanlp.seg.common.Term;
import com.ffwb.hanlp.utility.GlobalObjectPool;
import com.ffwb.hanlp.utility.TextUtility;

import java.util.LinkedList;
import java.util.List;

import static com.ffwb.hanlp.utility.Predefine.logger;

/**
 * 基于2阶HMM（A Second-Order Hidden Markov Model, TriGram3阶文法模型）+ BMES序列标注的分词器
 *
 * @author ffwb
 */
public class HMMSegment extends CharacterBasedGenerativeModelSegment
{
    CharacterBasedGenerativeModel model;

    public HMMSegment()
    {
        this(HanLP.Config.HMMSegmentModelPath);
    }

    public HMMSegment(String modelPath)
    {
        model = GlobalObjectPool.get(modelPath);
        if (model != null) return;
        model = new CharacterBasedGenerativeModel();
        long start = System.currentTimeMillis();
        logger.info("开始从[ " + modelPath + " ]加载2阶HMM模型");
        try
        {
            ByteArray byteArray = ByteArray.createByteArray(modelPath);
            if (byteArray == null)
            {
                logger.severe("HMM分词模型[ " + modelPath + " ]不存在" );
                System.exit(-1);
            }
            model.load(byteArray);
        }
        catch (Exception e)
        {
            logger.severe("发生了异常：" + TextUtility.exceptionToString(e));
            System.exit(-1);
        }
        logger.info("加载成功，耗时：" + (System.currentTimeMillis() - start) + " ms");
        GlobalObjectPool.put(modelPath, model);
    }

    @Override
    protected List<Term> segSentence(char[] sentence)
    {
        char[] tag = model.tag(sentence);
        List<Term> termList = new LinkedList<Term>();
        int offset = 0;
        for (int i = 0; i < tag.length; offset += 1, ++i)
        {
            switch (tag[i])
            {
                case 'b':
                {
                    int begin = offset;
                    while (tag[i] != 'e')
                    {
                        offset += 1;
                        ++i;
                        if (i == tag.length)
                        {
                            break;
                        }
                    }
                    if (i == tag.length)
                    {
                        termList.add(new Term(new String(sentence, begin, offset - begin), null));
                    }
                    else
                        termList.add(new Term(new String(sentence, begin, offset - begin + 1), null));
                }
                break;
                default:
                {
                    termList.add(new Term(new String(sentence, offset, 1), null));
                }
                break;
            }
        }

        return termList;
    }
}
