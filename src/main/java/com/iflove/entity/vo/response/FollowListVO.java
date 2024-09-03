package com.iflove.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Data
@AllArgsConstructor
public class FollowListVO {
    private List<FollowInfoVO> items;
    private Long total;
}
