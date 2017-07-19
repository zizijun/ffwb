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
 * 香港繁体转繁体
 * @author ffwb
 */
public class HongKongToTraditionalChineseDictionary extends BaseChineseDictionary
{
    static AhoCorasickDoubleArrayTrie<String> trie = new AhoCorasickDoubleArrayTrie<String>();
    static
    {
        long start = System.currentTimeMillis();
        String datPath = HanLP.Config.tcDictionaryRoot + "hk2t";
        if (!load(datPath, trie))
        {
            TreeMap<String, String> hk2t = new TreeMap<String, String>();
            if (!load(hk2t, true, HanLP.Config.tcDictionaryRoot + "t2hk.txt"))
            {
                throw new IllegalArgumentException("香港繁体转繁体加载失败");
            }
            trie.build(hk2t);
            saveDat(datPath, trie, hk2t.entrySet());
        }
        logger.info("香港繁体转繁体加载成功，耗时" + (System.currentTimeMillis() - start) + "ms");
    }

    public static String convertToTraditionalChinese(String traditionalHongKongChineseString)
    {
        return segLongest(traditionalHongKongChineseString.toCharArray(), trie);
    }

    public static String convertToTraditionalChinese(char[] traditionalHongKongChineseString)
    {
        return segLongest(traditionalHongKongChineseString, trie);
    }
}
