/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.repository;

import com.mint.cardscheme.entity.CardScheme;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author austine.okoroafor
 */
@Repository
public interface CardSchemeRepository extends JpaRepository<CardScheme, Long> {
    
    CardScheme findByCardnum(String cardnum);
    
    public List<CardScheme> findByCardnum(String cardnum, Pageable pageable);

    
}
