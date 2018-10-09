package com.code.resumemanagement.domain;

import java.io.Serializable;

public class Permission implements Serializable {

    private static final long serialVersionUID = -8025597823572680802L;

    private String id;

    private String token;//token

    private String url;

    private String description;//权限说明

    private String roleId;//角色id

    public Permission() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
