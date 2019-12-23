package com.github.yeecode.objectlogger.client.richText;

import java.util.List;

class Fragment {
    private Integer lineNumber;
    private List<Part> partList;

    Fragment(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    Integer getLineNumber() {
        return lineNumber;
    }

    void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    List<Part> getPartList() {
        return partList;
    }

    void setPartList(List<Part> partList) {
        this.partList = partList;
    }
}
