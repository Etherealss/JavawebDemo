package pers.etherealss.demo.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.etherealss.demo.core.repository.annotation.DbField;
import pers.etherealss.demo.core.repository.annotation.DbFieldId;

import java.util.Date;

/**
 * @author wtk
 * @description
 * User类上的3个注解是lombok的
 * 类字段上的注解就是自己写的，用来表示User类对应的数据库表的字段名
 * @date 2021-09-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @DbFieldId
    @DbField(value = "id",update = false)
    private Long id;
    @DbField("email")
    private String email;
    @DbField("password")
    private String password;
    @DbField("nickname")
    private String nickname;
    @DbField("birthday")
    private Date birthday;
    @DbField("sex")
    private Boolean sex;
}
