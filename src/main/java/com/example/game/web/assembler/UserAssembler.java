package com.example.game.web.assembler;

import java.util.ArrayList;
import java.util.List;
import com.example.game.model.entities.User;
import com.example.game.web.resources.UserCreateResource;
import com.example.game.web.resources.UserResource;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

  public User assembleUser(UserCreateResource userCreateResource) {
    User user = new User();
    user.setUsername(userCreateResource.getUsername());
    user.setPassword(userCreateResource.getPassword());
    return user;
  }

  public UserResource assembleUserResource(User user) {
    UserResource userResource = new UserResource();
    userResource.setUsername(user.getUsername());
    userResource.setPassword(user.getPassword());
    return userResource;
  }

  public List<UserResource> assembleProductsResource(List<User> users) {
    List<UserResource> userResources = new ArrayList<>();
    for (User user : users) {
      userResources.add(assembleUserResource(user));
    }
    return userResources;
  }
}
