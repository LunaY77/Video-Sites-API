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
public class FollowingListVO {
    private List<FollowingInfoVO> items;
    private Long total;
}
