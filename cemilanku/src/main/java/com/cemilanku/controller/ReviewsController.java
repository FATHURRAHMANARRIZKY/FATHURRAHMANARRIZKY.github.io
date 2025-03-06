package com.cemilanku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cemilanku.model.Reviews;
import com.cemilanku.service.ReviewsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewsController {
    @Autowired
    private ReviewsService reviewsService;

    // membuat review
    @PostMapping
    public Reviews createReviews(@RequestBody Reviews reviews){
        return reviewsService.createReviews(reviews);
    }

    // membaca review
    @GetMapping
    public List<Reviews> getAllReviews(){
        return reviewsService.getAllReviews();
    }

    // memperbarui review dengan id
    @PutMapping("/{id}")
    public Reviews updateReviews(@PathVariable String id, @Valid @RequestBody Reviews reviews){
        return reviewsService.updateReviews(id, reviews);
    }

    // menghapus review dengan id
    @DeleteMapping("/{id}")
    public void deleteReviews(@PathVariable String id){
        reviewsService.deleteReviews(id);
    }
}
