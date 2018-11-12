package com.nobbyknox.absa.queues;

public enum Queues {
    PAYMENT("payment.new"),
    STATUS("payment.status"),
    ENGINE("payment.engine"),
    ACK("payment.ack");

    @Override
    public String toString() {
        return queueName;
    }

    Queues(String name) {
        this.queueName = name;
    }

    private final String queueName;
}
