package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> show(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<Post> result = posts.stream().limit(limit).toList();
        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> showById(@PathVariable("id") String id) {
        Optional<Post> post = posts
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post requestBody) {
        posts.add(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestBody);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> update(@PathVariable("id") String id, @RequestBody Post requestBody) {
        Optional<Post> foundPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        ResponseEntity<Post> result;
        if (foundPost.isPresent()) {
            Post post = foundPost.get();
            post.setId(requestBody.getId());
            post.setBody(requestBody.getBody());
            post.setTitle(requestBody.getTitle());
            result = ResponseEntity.ok(post);
        } else {
            result = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return result;
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
