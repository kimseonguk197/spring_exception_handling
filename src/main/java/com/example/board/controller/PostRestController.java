package com.example.board.controller;

import com.example.board.Exception.NoListException;
import com.example.board.domain.Post;
import com.example.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RestController
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

//      에러처리에 대한 기본
//    @GetMapping("api/posts/findById/{id}")
//    public Post findById(@PathVariable("id") Long id) throws Exception {
////        Exception처리를 해주지 않고, null로만 return하면 기본 httperror메세지도 나가지 않는다.
////        return postService.findById(id).orElse(null);
////        기본 Exception처리만 하게 되면, 어떤 에러인지 알기가 어렵다. 또한 메서드 단위로  throws Exception  해주어야함.
////        return postService.findById(id).orElseThrow(Exception::new);
////        아래코드는 가독성은 향상할 수 있으나, postman 테스트시에 500에러를 리턴함을 알수가 있다.
////        return postService.findById(id).orElseThrow(EntityNotFoundException::new);
//            return postService.findById(id).orElseThrow(()->new EntityNotFoundException());
//    }


    @GetMapping("api/posts")
    public List<Post> postList(){
//        wrapping된 error
        try{
            List<Post> posts=  postService.findAll();
            return posts;
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("postList EntityNotFoundException " + e.getMessage());
        }
//        wrapping없이 return
//        return postService.findAll();
    }


//    @GetMapping("api/posts/acompany")
//    public Post acompanyApi() throws NoListException {
//           return postService.test();
//    }

    @GetMapping("api/posts/acompany")
    public Post acompanyApi() throws Exception {
        return postService.test();
    }

}
