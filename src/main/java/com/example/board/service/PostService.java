package com.example.board.service;

import com.example.board.Exception.NoListException;
import com.example.board.domain.Post;
import com.example.board.repository.PostRepository;
import jdk.jshell.spi.ExecutionControl;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PostService {

//    외부 접근 불가능
    private final PostRepository repository;

//    생성자
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> findAll(){
        List<Post> result = repository.findAll();
        if(result.isEmpty()){
            throw new EntityNotFoundException("findAll EntityNotFoundException");
        }
        return result;
    }

    public Optional<Post> findById(Long memberId){
        return Optional.ofNullable(repository.findById(memberId).orElseThrow(()-> new EntityNotFoundException("service no")));
    }

    public Post test() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Post> response = restTemplate.exchange(
                    "http://localhost:8082/acompany",
                    HttpMethod.GET,
                    null,
                    Post.class
            );
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println("e : "+e);
            int status = Integer.parseInt(message.substring(0,3));
            JSONObject jo = new JSONObject(message.substring(7));
            String errorMessage = (String) jo.get("errors");
            if(status == 400){
                throw new NoListException(errorMessage);
            }else if(status == 404){
                throw new EntityNotFoundException(errorMessage);
            }else{
                throw new Exception();
            }
        }
    }



}
