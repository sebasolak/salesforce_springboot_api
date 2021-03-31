package com.example.salesforcespringbootv2.model.contact;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactResponse {
    private int totalSize;
    private boolean done;
    private List<Contact> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Contact> getRecords() {
        return records;
    }

    public void setRecords(List<Contact> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "ContactResponse{" +
                "totalSize=" + totalSize +
                ", done=" + done +
                ", records=" + records +
                '}';
    }
}
