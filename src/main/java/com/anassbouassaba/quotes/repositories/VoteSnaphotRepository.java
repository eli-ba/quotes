package com.anassbouassaba.quotes.repositories;

import java.util.List;

import com.anassbouassaba.quotes.entities.VoteSnapshot;
import com.anassbouassaba.quotes.mappers.VoteHistoryItem;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface VoteSnaphotRepository extends PagingAndSortingRepository<VoteSnapshot, Long> {
  @Query("select v from VoteSnapshot v where v.quoteId = :quoteId order by createdAt desc")
  List<VoteSnapshot> findByQuoteId(@Param("quoteId") Long quoteId, Pageable pageable);
  
  @Query("select v from VoteSnapshot v where v.quoteId = :quoteId order by createdAt desc")
  List<VoteSnapshot> findByQuoteId(@Param("quoteId") Long quoteId);

  @Query(value = "SELECT date_trunc('second', v.created_at) createdAt, max(v.upvotes) - max(v.downvotes) delta, max(v.upvotes) upvotes, max(v.downvotes) downvotes FROM vote_snapshot v WHERE quote_id = :quoteId GROUP BY 1 ORDER BY createdAt ASC;", nativeQuery = true)
  List<VoteHistoryItem> voteHistory(@Param("quoteId") Long quoteId);
}