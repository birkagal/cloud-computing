package com.birkagal.wishlist.controller;

import com.birkagal.wishlist.model.SortBoundary;
import com.birkagal.wishlist.model.UserBoundary;
import com.birkagal.wishlist.model.WishListBoundary;
import com.birkagal.wishlist.model.WishListProductBoundary;
import com.birkagal.wishlist.service.WishlistServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class WishListController {

    private final WishlistServiceImplementation wishList;

    @Autowired
    public WishListController(WishlistServiceImplementation wishList) {
        super();
        this.wishList = wishList;
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --debug --request --data "{\"name\":\"Gal's wishlist\", \"userEmail\":\"gal@email.com\"}" --route wishList-create-req-resp tcp://localhost:7000
    @MessageMapping("wishList-create-req-resp")
    public Mono<WishListBoundary> createRequestResponse(WishListBoundary input) {
        return this.wishList.store(input);
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --debug --fnf --data "{\"wishListId\":\"61b4834f58e87861702568ae\", \"productId\":\"p42\"}" --route addProd-fire-and-forget tcp://localhost:7000
    @MessageMapping("addProd-fire-and-forget")
    public Mono<Void> AddProdFireAndForget(WishListProductBoundary input) {
        return this.wishList.addProduct(input);
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --debug --stream --data "{\"sortBy\":[\"WishListId\"], \"order\":\"ASC\"}" --route getLists-stream tcp://localhost:7000
    @MessageMapping("getLists-stream")
    public Flux<WishListBoundary> getAllWishListsStream(SortBoundary sort) {
        return this.wishList.getAllWishLists(sort);
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --debug --stream --data "{\"wishListId\":\"61b4d56335fc00301bd9b605\"}" --route getProductsByList-stream tcp://localhost:7000
    @MessageMapping("getProductsByList-stream")
    public Flux<WishListProductBoundary> getAllProductsByWishListStream(WishListBoundary wishlist) {
        return this.wishList.getAllProductsByWishList(wishlist);
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --debug --fnf --route cleanup-fire-and-forget tcp://localhost:7000
    @MessageMapping("cleanup-fire-and-forget")
    public Mono<Void> cleanupFNF() {
        return this.wishList.deleteAll();
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --stream --data "{\"userEmail\":\"gal@email.com\"}" --route getLists-byEmail-stream tcp://localhost:7000
    @MessageMapping("getLists-byEmail-stream")
    public Flux<WishListBoundary> getAllWishListsByEmailStream(UserBoundary user) {
        return this.wishList.getAllWishListsByEmail(user);
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --stream --data "{\"sortBy\":[\"wishListId\"], \"order\":\"ASC\"}" --route getProducts-stream tcp://localhost:7000
    @MessageMapping("getProducts-stream")
    public Flux<WishListProductBoundary> getAllProductsStream(SortBoundary sort) {
        return this.wishList.getAllProducts(sort);
    }

    // CLI INVOCATION SAMPLE:
    // java -jar rsc-0.9.1.jar --channel --data=- --route getProductsByLists-channel tcp://localhost:7000
    @MessageMapping("getProductsByLists-channel")
    public Flux<WishListProductBoundary> getAllProductsByWishListChannel(Flux<WishListBoundary> wishlist) {
        return this.wishList.getAllProductsByWishListChannel(wishlist);
    }
}
