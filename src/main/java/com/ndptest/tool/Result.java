package com.ndptest.tool;

import lombok.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@Table(name="result")
@AllArgsConstructor
@NoArgsConstructor

public class Result{
//    @Override
//    public boolean isNew(){
//        return true;
//    }
    @Id
    private int number;

    @Column(length = 200)
    private String username;
    private String responseTime;
}
