package oncall.domain;

import java.util.Objects;

public class Work {

    private String worker;

    public Work() {
        this.worker = "배정안됨";
    }

    public boolean isAssigned() {
        return worker.equals("베정안됨");
    }

    public void assign(String name) {
        this.worker = name;
    }

    public String getWorker() {
        return worker;
    }
}
