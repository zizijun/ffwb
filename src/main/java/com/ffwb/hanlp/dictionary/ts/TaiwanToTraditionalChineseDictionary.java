/*
 * <summary></summary>
 * <author>ffwb</author>
 * <email>me@ffwb.com</email>
 * <create-date>2016-08-30 AM10:29</create-date>
 *
 * <copyright file="SimplifiedToHongKongChineseDictionary.java" company="码农场">
 * Copyright (c) 2008-2016, 码农场. All Right Reserved, http://www.ffwb.com/
 * This source is subject to ffwb. Please contact ffwb to get more information.
 * </copyright>
 */
package com.ffwb.hanlp.dictionary.ts;

import com.ffwb.hanlp.HanLP;
import com.ffwb.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;

import java.util.TreeMap;

import static com.ffwb.hanlp.utility.Predefine.logger;

/**
 * 台湾繁体转繁体
 * @author ffwb
 */
public class TaiwanToTraditionalChineseDictionary extends BaseChineseDictionary
{
    static AhoCorasickDoubleArrayTrie<String> trie = new AhoCorasickDoubleArrayTrie<String>();
    static
    {
        long start = System.currentTimeMillis();
        String datPath = HanLP.Config.tcDictionaryRoot + "tw2t";
        if (!loadDat(datPath, trie))
        {
            TreeMap<String, String> tw2t = new TreeMap<String, String>();
            if (!load(tw2t, true, HanLP.Config.tcDictionaryRoot + "t2tw.txt"))
            {
                throw new IllegalArgumentException("台湾繁体转繁体加载失败");
            }
            trie.build(tw2t);
            saveDat(datPath, trie, tw2t.entrySet());
        }
        logger.info("台湾繁体转繁体加载成功，耗时" + (System.currentTimeMillis() - start) + "ms");
    }

    public static String convertToTraditionalChinese(String traditionalTaiwanChineseString)
    {
        return segLongest(traditionalTaiwanChineseString.toCharArray(), trie);
    }

    public static String convertToTraditionalChinese(char[] traditionalTaiwanChineseString)
    {
        return segLongest(traditionalTaiwanChineseString, trie);
    }
}
