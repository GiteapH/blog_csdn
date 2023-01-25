package cn.springboot.blog.util;

import java.math.BigInteger;
import java.security.MessageDigest;


public class SystemUtil {

    private SystemUtil() {
    }


    /**
     * 登录或注册成功后,生成保持用户登录状态会话token值
     *
     * @param src:为用户最新一次登录时的now()+user.id+random(4)
     * @return
     */
    public static String genToken(String src) {
        if (null == src || "".equals(src)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() == 31) {
                result = result + "-";
            }
            System.out.println(result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }


//    点赞收藏需要的字段
    public static String getParamName(int i){
        String[] names = new String[]{"like","dislike","collected"};
        return names[i];
    }

//    用户个人中心模块
    public static String getModuleName(int i){
        String[] names = new String[]{"收藏列表","关注列表","发布列表","发布数量打卡"};
        return names[i];
    }
    //    用户个人中心模块
    public static String[] getModuleNames(){
        return new String[]{"收藏列表","关注列表","发布列表","发布数量打卡"};

    }

//    获取评论排序字段
    public static String getOrderName(int i){
        String[] names = new String[]{"like","watched","comments","collected"};
        return names[i];
    }
}
