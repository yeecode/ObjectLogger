package com.github.yeecode.objectLogger.client.bean;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Html2Text extends HTMLEditorKit.ParserCallback {

    private String lineBreak = "\n";
    private StringBuilder stringBuilder = new StringBuilder();

    public static String simpleHtml(String html) {
        try {
            if (html.isEmpty()) {
                return "";
            }
            Html2Text parser = new Html2Text();
            parser.parse(html);
            return parser.getText() != null ? parser.getText() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void parse(String html) throws IOException {
        Reader reader = new StringReader(html);
        ParserDelegator parsers = new ParserDelegator();
        parsers.parse(reader, this, Boolean.TRUE);
    }

    @Override
    public void handleText(char[] text, int pos) {
        stringBuilder.append(text);
    }

    @Override
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (stringBuilder.length() != 0
                && t.isBlock()
                && !stringBuilder.toString().endsWith(lineBreak)) {
            stringBuilder.append(lineBreak);
        }
    }

    @Override
    public void handleEndTag(HTML.Tag t, int pos) {
        if (stringBuilder.length() != 0
                && t.isBlock()
                && !stringBuilder.toString().endsWith(lineBreak)) {
            stringBuilder.append(lineBreak);
        }
    }

    @Override
    public void handleEndOfLineString(String eol) {
        if (stringBuilder.length() - lineBreak.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - lineBreak.length(), stringBuilder.length());
        }
    }

    @Override
    public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        // deal with <img>
        if (HTML.Tag.IMG.equals(t)) {
            stringBuilder.append("【【-START_IMG-】】 " + a.toString() + "【【-END_IMG-】】");
        }
    }

    public String getText() {
        return stringBuilder.toString();
    }

}  