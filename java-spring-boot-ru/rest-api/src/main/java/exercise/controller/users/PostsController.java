package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private List<Post> posts = new ArrayList<>();

    @GetMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> show(@PathVariable("id") String userId) {
        return posts.stream().filter(post -> post.getUserId() == Integer.parseInt(userId)).toList();
    }

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable("id") String userId, @RequestBody Post requestBody) {
        requestBody.setUserId(Integer.parseInt(userId));
        posts.add(requestBody);
        return requestBody;
    }
}
// END
