package fullstack.website.blog.utils.api;


public class ConstantsApi {

    public static final String NAME_PROJECT = "/meta-blog";

    public static final String BASE_URL = "/api" + NAME_PROJECT;


    public static final class Category {
        public static final String CATEGORY_PATH = BASE_URL + "/category";
    }
    public static final class Account {
        public static final String ACCOUNT_PATH = BASE_URL + "/account";
    }

    public static final class Authentication {
        public static final String AUTHENTICATION_PATH = BASE_URL + "/authentication";
    }
    public static final class Post {
        public static final String POST_PATH = BASE_URL + "/post";
    }
    public static final class Comment {
        public static final String COMMENT_PATH = BASE_URL + "/comment";
    }

    public static final class Like{
        public static final String LIKE_PATH = BASE_URL + "/like";
    }

}
