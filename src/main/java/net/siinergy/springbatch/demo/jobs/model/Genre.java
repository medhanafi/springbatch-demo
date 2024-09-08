package net.siinergy.springbatch.demo.jobs.model;


public class Genre {
    private Long id;

    private String label;

    public Long getId() {
        return id;
    }

    public Genre setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Genre setLabel(String label) {
        this.label = label;
        return this;
    }
}
