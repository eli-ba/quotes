package com.eliba.quotes.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.eliba.quotes.entities.Quote;

public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {
  Quote findTopByOrderByCreatedAtDesc();
  
  @Query("select q from Quote q order by (upvotes - downvotes) desc")
  List<Quote> top(Pageable pageable);
  
  @Query("select q from Quote q order by (downvotes - upvotes) desc")
  List<Quote> flop(Pageable pageable);
  
  @Transactional
  @Modifying
  @Query("update Quote set upvotes = upvotes + 1 where id = :id")
  void upvote(@Param("id") Long id);
  
  @Transactional
  @Modifying
  @Query("update Quote set downvotes = downvotes + 1 where id = :id")
  void downvote(@Param("id") Long id);
}