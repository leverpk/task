package com.example.task;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GithubRepo {

    private String name;
    @JsonProperty("fork")
    private boolean forked;
    private GithubUser owner;
    private List<Branch> branches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isForked() {
        return forked;
    }

    public void setForked(boolean forked) {
        this.forked = forked;
    }

    public GithubUser getOwner() {
        return owner;
    }

    public void setOwner(GithubUser owner) {
        this.owner = owner;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
