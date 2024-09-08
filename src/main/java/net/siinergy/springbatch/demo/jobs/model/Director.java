package net.siinergy.springbatch.demo.jobs.model;


public class Director {
    private Long id;

    private String fullName;

    public Long getId() {
        return id;
    }

    public Director setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Director setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
