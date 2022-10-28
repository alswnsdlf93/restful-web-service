package com.example.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import io.swagger.models.Model;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Resource
@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/1 or /users/10 -> String
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));
        return model;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

}

//public class UserController {
//    private UserDaoService service;
//
//
//    public UserController(UserDaoService service) {
//        this.service = service;
//    }
//
//    @GetMapping("/users")
//    public List<User> retrieveAllUsers() {
//        return service.findAll(); }
//
//    // GET /users/1 자번 사용자 or / users/ 10 번 사용
//    @GetMapping("/users/{id}")
//    public EntityModel<User> retrieveUser(@PathVariable int id) {
//        User user = service.findOne(id);
//
//        if(user == null) {
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//        // Hateoas
//        // EntityModel<User> model = EntityModel.of(user);
//
////        Resource<User>  resource = new Resource<>(user);
////        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
////        resource.add(linkTo.withRel("all-users"));
//
//
//        EntityModel<User> model = EntityModel.of(user);
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        model.add(linkTo.withRel("all-users"));
//        return model;
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
//        User savedUser = service.save(user);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedUser.getId())
//                .toUri();
//        return ResponseEntity.created(location).build();
//
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable int id) {
//        User user = service.deleteById(id);
//
//        if (user == null){
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//    }
//}