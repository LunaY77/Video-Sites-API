package com.iflove.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Data
@AllArgsConstructor
public class CountParams {
    private Long id;
    private Date date;
    private Integer count;
    private Timestamp update;
}
