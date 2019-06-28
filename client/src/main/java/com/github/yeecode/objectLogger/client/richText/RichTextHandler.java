package com.github.yeecode.objectLogger.client.richText;

import difflib.Delta;
import difflib.DiffRow;
import difflib.DiffRowGenerator;
import difflib.DiffUtils;
import difflib.Patch;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RichTextHandler {
    private RichTextHandler() {
    }

    /**
     * 对比两个 text对象，或者html片段的不同，生成类似于gitDiff的html片段
     *
     * @param oldText 旧值
     * @param newText 新值
     * @return 类似于gitDiff的html片段
     */
    public static String diffText(String oldText, String newText) {
        StringBuilder outputSb = new StringBuilder();

        List<String> oldStringList = CollectionUtils.arrayToList(Html2Text.simpleHtml(oldText).split("\n"));
        List<String> newStringList = CollectionUtils.arrayToList(Html2Text.simpleHtml(newText).split("\n"));

        // 获得文件的不同之处
        Patch patch = DiffUtils.diff(oldStringList, newStringList);
        DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
        builder.showInlineDiffs(false).columnWidth(Integer.MAX_VALUE);
        DiffRowGenerator generator = builder.build();

        Map<Integer, List<DiffRow>> diffRowMap = new LinkedHashMap<>();

        // 获得变化的数据
        for (Object delta : patch.getDeltas()) {
            List<String> originalLines = (List<String>) ((Delta) delta).getOriginal().getLines();
            List<String> revisedLines = (List<String>) ((Delta) delta).getRevised().getLines();
            List<DiffRow> generateDiffRows = generator.generateDiffRows(originalLines, revisedLines);
            // 变化的位置
            int leftPos = ((Delta) delta).getOriginal().getPosition();
            int rightPos = ((Delta) delta).getRevised().getPosition();
            // 根据改变的位置做分类
            for (DiffRow row : generateDiffRows) {
                List<DiffRow> diffRowList = diffRowMap.get(leftPos);
                if (diffRowList == null) {
                    diffRowList = new ArrayList<DiffRow>();
                }
                diffRowList.add(row);
                diffRowMap.put(leftPos, diffRowList);
            }
        }

        // 遍历map
        Set<Map.Entry<Integer, List<DiffRow>>> entrySet = diffRowMap.entrySet();
        for (Map.Entry<Integer, List<DiffRow>> entry : entrySet) {
            Integer pos = entry.getKey();
            List<DiffRow> diffRowList = entry.getValue();
            outputSb.append("Line ").append(pos + 1).append("<br/>");
            for (DiffRow row : diffRowList) {
                DiffRow.Tag tag = row.getTag();
                if (tag == DiffRow.Tag.INSERT) {
                    outputSb.append("&nbsp;&nbsp; +： <u> ").append(replaceImgTag(row.getNewLine())).append(" </u> <br/>");
                } else if (tag == DiffRow.Tag.CHANGE) {
                    if (!row.getOldLine().trim().isEmpty()) {
                        outputSb.append("&nbsp;&nbsp;&nbsp; -： <del> ");
                        outputSb.append(replaceImgTag(row.getOldLine()));
                        outputSb.append(" </del> <br/>");
                    }
                    if (!row.getNewLine().trim().isEmpty()) {
                        outputSb.append("&nbsp;&nbsp; +： <u> ");
                        outputSb.append(replaceImgTag(row.getNewLine()));
                        outputSb.append(" </u> <br/>");
                    }
                } else if (tag == DiffRow.Tag.DELETE) {
                    outputSb.append("&nbsp;&nbsp;&nbsp; -： <del> ").append(replaceImgTag(row.getOldLine())).append(" </del> <br/>");
                }
            }
        }
        return outputSb.toString();
    }

    private static String replaceImgTag(String s) {
        return s.replaceAll("【【-START_IMG-】】", "<img ").replaceAll("【【-END_IMG-】】", " >");
    }
}
