package com.github.yeecode.objectlogger.client.richText;

class Part {
    private PartType partType;
    private String partContent;

    Part(PartType partType) {
        this.partType = partType;
    }

    Part(PartType partType, String partContent) {
        this.partType = partType;
        this.partContent = partContent;
    }

    PartType getPartType() {
        return partType;
    }

    void setPartType(PartType partType) {
        this.partType = partType;
    }

    String getPartContent() {
        return partContent;
    }

    void setPartContent(String partContent) {
        this.partContent = partContent;
    }
}
