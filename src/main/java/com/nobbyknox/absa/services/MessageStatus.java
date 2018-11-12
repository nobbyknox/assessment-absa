package com.nobbyknox.absa.services;

public enum MessageStatus {
    APPROVED("approved"),
    REJECTED("rejected"),
    UNKNOWN("unknown");

    @Override
    public String toString() {
        return statusName;
    }

    public static MessageStatus getStatus(String value) {
        for (MessageStatus status : values()) {
            if (status.toString().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException();
    }

    MessageStatus(String name) {
        this.statusName = name;
    }

    private final String statusName;
}
