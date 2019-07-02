package Responses;

import Models.UserProfile;

public class UserProfileResponse {

String status;
UserProfile data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserProfile getData() {
        return data;
    }

    public void setData(UserProfile data) {
        this.data = data;
    }
}
