package com.iche.sco.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageUtils<T> implements Serializable {
    private Integer pageNo;
    private Integer pageSize;
    private long totalElement;
    private Integer totalPage;
    List<T> content;
    private Boolean isLast;
}
