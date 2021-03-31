package com.example.salesforcespringbootv2.model.account;

import com.example.salesforcespringbootv2.model.contact.Contact;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountResponse {
    private int totalSize;
    private boolean done;
    private List<Account> records;

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

    public List<Account> getRecords() {
        return records;
    }

    public void setRecords(List<Account> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "totalSize=" + totalSize +
                ", done=" + done +
                ", records=" + records +
                '}';
    }
}
