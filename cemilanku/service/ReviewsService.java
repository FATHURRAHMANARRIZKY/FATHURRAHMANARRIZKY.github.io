package com.cemilanku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cemilanku.model.Reviews;
import com.cemilanku.repository.ReviewsRepository;

@Service
public class ReviewsService {
    @Autowired
    private ReviewsRepository reviewsRepository;
    private final int notNull = 0;

    // membuat review
    public Reviews createReviews(Reviews reviews){
        return reviewsRepository.save(reviews);
    }

    // membaca review
    public List<Reviews> getAllReviews(){
        return reviewsRepository.findAll();
    }

    // memperbarui review dengan id
    public Reviews updateReviews(String id, Reviews reviews){
        Reviews UpdateReviews = reviewsRepository.findById(id).orElseThrow(() -> new RuntimeException("Review tidak diketemui!"));
        if(reviews.getNama() != null){
            UpdateReviews.setNama(reviews.getNama());
        }
        else if(reviews.getKomentar() != null){
            UpdateReviews.setKomentar(reviews.getKomentar());
        }
        else if(reviews.getNilai() != notNull){
            UpdateReviews.setNilai(reviews.getNilai());
        }
        return reviewsRepository.save(UpdateReviews);
    }

    // menghapus review dengan id
    public void deleteReviews(String id){
        if(reviewsRepository.existsById(id)){
            reviewsRepository.deleteById(id);
        }else{
            throw new RuntimeException("review id tidak diketemui!");
        }
    }
}
