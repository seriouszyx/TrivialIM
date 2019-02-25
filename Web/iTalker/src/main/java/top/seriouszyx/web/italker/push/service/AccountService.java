package top.seriouszyx.web.italker.push.service;

import top.seriouszyx.web.italker.push.bean.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService {

    @GET
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User get() {
        User user = new User();
        user.setName("张三");
        user.setSex(1);
        return user;
    }

}
