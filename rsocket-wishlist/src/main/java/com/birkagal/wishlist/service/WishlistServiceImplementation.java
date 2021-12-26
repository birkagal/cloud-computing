package com.birkagal.wishlist.service;

import com.birkagal.wishlist.model.SortBoundary;
import com.birkagal.wishlist.model.UserBoundary;
import com.birkagal.wishlist.model.WishListBoundary;
import com.birkagal.wishlist.model.WishListProductBoundary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class WishlistServiceImplementation implements WishListService {

    private final WishListDAO wishListDAO;
    private final WishListProductDAO wishListProductDAO;
    private final ServiceUtil util;
    private final Log logger;

    @PostConstruct
    public void init() {
        Hooks.onErrorDropped(e -> {
        });
    }

    @Autowired
    public WishlistServiceImplementation(WishListDAO wishListDAO, WishListProductDAO wishListProductDAO) {
        super();
        this.wishListDAO = wishListDAO;
        this.wishListProductDAO = wishListProductDAO;
        this.util = new ServiceUtil();
        this.logger = LogFactory.getLog(WishlistServiceImplementation.class);
    }

    @Override
    public Mono<WishListBoundary> store(WishListBoundary wishList) {
        this.logger.debug("Received wishList-create-req-resp request with: " + wishList);

        // Validate user provided name and userEmail fields
        if (wishList.getName() == null || wishList.getName().isEmpty()
                || wishList.getUserEmail() == null || wishList.getUserEmail().isEmpty())
            throw new RuntimeException("Please provide Name and userEmail.");

        if (!Pattern.compile("[A-Z0-9_.]+@([A-Z0-9]+\\.)+[A-Z0-9]{2,6}$",
                Pattern.CASE_INSENSITIVE).matcher(wishList.getUserEmail()).find()) {
            throw new RuntimeException("Please provide a valid email address");
        }

        return Mono.just(wishList)
                .map(boundary -> {
                    boundary.setWishListId(null);
                    boundary.setCreatedTimestamp(new Date());
                    return boundary;
                })
                .map(this.util::wishlistToEntity)
                .flatMap(this.wishListDAO::save)
                .map(this.util::wishlistToBoundary)
                .log();
    }

    @Override
    public Mono<Void> addProduct(WishListProductBoundary wishListProduct) {
        this.logger.debug("Received addProd-fire-and-forget request with: " + wishListProduct);

        // Validate user provided productId field
        if (wishListProduct.getWishListId() == null || wishListProduct.getProductId() == null
                || wishListProduct.getProductId().isEmpty())
            return Mono.empty();

        return this.wishListDAO
                .findById(wishListProduct.getWishListId())
                .flatMap(entity -> Mono.just(wishListProduct))
                .map(this.util::wishlistProductToEntity)
                .flatMap(this.wishListProductDAO::save)
                .log()
                .then();
    }

    @Override
    public Flux<WishListBoundary> getAllWishLists(SortBoundary sort) {
        this.logger.debug("Received getLists-stream request with: " + sort);

        // Validate user provided correct sort field. If not, use defaults
        SortBoundary validatedSort = this.util.validateSort(sort);
        this.logger.debug("Using sort: " + validatedSort);

        return this.wishListDAO
                .findAll(Sort.by(Sort.Direction.fromString(validatedSort.getOrder()), validatedSort.getSortBy().toArray(new String[0])))
                .map(this.util::wishlistToBoundary)
                .log();
    }

    @Override
    public Flux<WishListProductBoundary> getAllProductsByWishList(WishListBoundary wishList) {
        this.logger.debug("Received getProductsByList-stream request with: " + wishList);

        // Validate user provided wishListId
        if (wishList.getWishListId() == null)
            return Flux.empty();

        return this.wishListProductDAO
                .findAllByWishListId(wishList.getWishListId(), Sort.by(Sort.Direction.ASC, "productId"))
                .map(this.util::wishlistProductToBoundary)
                .log();
    }

    @Override
    public Mono<Void> deleteAll() {
        this.logger.debug("Received cleanup-fire-and-forget request.");

        return this.wishListDAO
                .deleteAll()
                .then(this.wishListProductDAO.deleteAll());
    }

    @Override
    public Flux<WishListBoundary> getAllWishListsByEmail(UserBoundary user) {
        this.logger.debug("Received getLists-byEmail-stream request with: " + user);

        // Validate user provided userEmail field
        if (user.getUserEmail() == null)
            throw new RuntimeException("Please provide userEmail");

        return this.wishListDAO
                .findByUserEmail(user.getUserEmail(), Sort.by(Sort.Direction.ASC, "createdTimestamp"))
                .map(this.util::wishlistToBoundary)
                .log();
    }

    @Override
    public Flux<WishListProductBoundary> getAllProducts(SortBoundary sort) {
        this.logger.debug("Received getProducts-stream request with: " + sort);

        // Validate user provided correct sort field. If not, use defaults
        SortBoundary validatedSort = this.util.validateSort(sort);
        this.logger.debug("Using sort: " + validatedSort);

        return this.wishListProductDAO
                .findAll(Sort.by(Sort.Direction.fromString(validatedSort.getOrder()), validatedSort.getSortBy().toArray(new String[0])))
                .map(this.util::wishlistProductToBoundary)
                .log();
    }

    @Override
    public Flux<WishListProductBoundary> getAllProductsByWishListChannel(Flux<WishListBoundary> wishList) {
        this.logger.debug("Received getProductsByLists-channel request.");
        return wishList
                .switchMap(this::getAllProductsByWishList)
                .log();
    }
}
