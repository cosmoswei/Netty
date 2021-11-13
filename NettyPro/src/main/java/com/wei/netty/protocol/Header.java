package com.wei.netty.protocol;

import java.util.HashMap;
import java.util.Map;

public class Header {
    //    private int crcCode = 0xabcef0101;
    private int crcCode = 1;
    private int length;
    private long sessionID;
    private byte type;
    private byte priority;
    private Map<String, Object> attachment = new HashMap<String, Object>();

    public Header() {
    }

    public Header(int crcCode, int length, long sessionID, byte type, byte priority, Map<String, Object> attachment) {
        this.crcCode = crcCode;
        this.length = length;
        this.sessionID = sessionID;
        this.type = type;
        this.priority = priority;
        this.attachment = attachment;
    }

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

}
