package com.tascape.qa.th;

/**
 *
 * @author linsong wang
 */
public class ExecutionResult {
    public static final ExecutionResult NA = new ExecutionResult("NA");

    public static final ExecutionResult QUEUED = new ExecutionResult("QUEUED");

    public static final ExecutionResult ACQUIRED = new ExecutionResult("ACQUIRED");

    public static final ExecutionResult RUNNING = new ExecutionResult("RUNNING");

    public static final ExecutionResult PASS = new ExecutionResult("PASS");

    public static final ExecutionResult FAIL = new ExecutionResult("FAIL");

    public static final ExecutionResult CANCEL = new ExecutionResult("CANCEL");

    public static synchronized ExecutionResult createMultiple() {
        return new ExecutionResult("MULTIPLE");
    }

    private String name = "";

    private int pass = 0;

    private int fail = 0;

    public ExecutionResult(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public String result() {
        if (this.pass != 0 || this.fail != 0) {
            return this.pass + "/" + this.fail;
        }
        return this.name();
    }

    public boolean equals(ExecutionResult er) {
        return this.result().equals(er.result());
    }
}
