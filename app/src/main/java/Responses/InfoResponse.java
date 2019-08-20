package Responses;

import Models.Info;

public class InfoResponse {
    String status;
    Info data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Info getData() {
        return data;
    }

    public void setData(Info data) {
        this.data = data;
    }
}
