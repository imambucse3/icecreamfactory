package com.example.icecreamfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.icecreamfactory.entity.Production;

@Repository
public interface ProductionRepository  extends JpaRepository<Production, Long> {

}
