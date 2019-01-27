package com.winterchen.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weweb.api.user.UserService;
import com.weweb.user.entity.User;
import com.winterchen.dao.PostRepository;
import com.winterchen.model.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.*;


/**
 *  * 
 *  * @author KL 
 *  * @date  2018年1月24日
 *  * @description 
 *  *
 *  
 */
@Component
public class PostHandler {
    private final PostRepository posts;

    public PostHandler(PostRepository posts) {
        this.posts = posts;
    }

    /**
     *      * 
     *      * @author KL
     *      * @date 2018年1月24日
     *      * @description: 获取全部实例
     *      * @return Mono<ServerResponse>
     *      
     */

    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(posts.findAll(), Post.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        System.out.println("create");
        return req.body(BodyExtractors.toMono(Post.class)).flatMap(post -> this.posts.createOrUpdate(post)).flatMap(p -> created(URI.create("/posts/" + p.getId())).build());
    }

    public Mono<ServerResponse> list(ServerRequest req) {
        System.out.println("==============================");
        long begiTime = System.currentTimeMillis();
        Long id = Long.valueOf(req.pathVariable("id"));
        Mono<ServerResponse> mono1 = this.posts.findById(id++).flatMap(post -> ok().syncBody(post)).switchIfEmpty(notFound().build());
        Mono<ServerResponse> mono2 = this.posts.findById(id++).flatMap(post -> ok().syncBody(post)).switchIfEmpty(notFound().build());
        Mono<ServerResponse> mono3 = this.posts.findById(id++).flatMap(post -> ok().syncBody(post)).switchIfEmpty(notFound().build());
        Mono<ServerResponse> mono4 = this.posts.findById(id++).flatMap(post -> ok().syncBody(post)).switchIfEmpty(notFound().build());
        Mono<ServerResponse> mono5 = this.posts.findById(id++).flatMap(post -> ok().syncBody(post)).switchIfEmpty(notFound().build());
        System.out.println("spend time:" + (System.currentTimeMillis() - begiTime));
        Flux<ServerResponse> flux = Flux.just(mono1.block(), mono2.block(), mono3.block(), mono4.block(), mono5.block());
        return get(req);
    }
    @Reference(timeout=6000)
    private UserService userService;

    public Mono findAllUsers(ServerRequest serverRequest) {
        List<User> users=userService.findAllUsers();
       return ok().body(Flux.fromIterable(users),User.class);

    }

    public Mono<ServerResponse> get(ServerRequest req) {
        System.out.println("==============================");
        Long id = Long.valueOf(req.pathVariable("id"));
        return this.posts.findById(id).flatMap(post -> ok().syncBody(post)).switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        /**
                 * Aggregate given monos into a new Mono that will be fulfilled 
                 * when all of the given Monos have been fulfilled, 
                 * aggregating their values according to the provided combinator function. 
                 * An error will cause pending results to be cancelled 
                 * and immediate error emission to the returned Mono. 
                 * 根据提供的组合功能聚合它们的值
                 */
        return Mono.zip((data) -> {
                    Post p = (Post) data[0];    //原始数据
                    Post p2 = (Post) data[1];    //修改的数据
                    p.setTitle(p2.getTitle());
                    p.setContent(p2.getContent());
                    return p;
                },
                this.posts.findById(Long.valueOf(req.pathVariable("id"))), req.bodyToMono(Post.class)).cast(Post.class)
                //Cast the current Mono produced type into a target produced type.
                .flatMap(post -> this.posts.createOrUpdate(post))
                .flatMap(post -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        /**
                 * 服务器成功处理了请求，但没返回任何内容。
                 */
        return ServerResponse.noContent().build(this.posts.delete(Long.valueOf(req.pathVariable("id"))));
    }
}