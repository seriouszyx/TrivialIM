package io.github.seriouszyx.push;

public class UserService implements IUserService {

    @Override
    public String search(int hashcode) {
        return "User:" + hashcode;
    }

}
