package cn.springboot.blog.api.blog.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodPointsParam implements Serializable {
    @ApiModelProperty("用户id")
    private Integer uid;

    @ApiModelProperty("评论id")
    private Integer cid;

    @ApiModelProperty("文章id")
    private Integer aid;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("更新类型")
    private Integer ntype;
}
