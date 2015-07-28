package cn.iyowei.stockai.config;

public class ACK {
    /**
     * 消息（1字头）
     */
    // 客户端应当继续发送请求
    public static final int CONTINUE = 100;

    // 代表处理将被继续执行。
    public static final int PROCESSING = 102;

    /**
     * 成功（2字头）
     */
    // 请求已成功，请求所希望的响应头或数据体将随此响应返回。
    public static final int SUCCESS = 200;

    //RestFul  CREATED – [POST/PUT/PATCH]：用户新建或修改数据成功。
    //请求已经被实现，而且有一个新的资源已经依据请求的需要而建立，且其 URI 已经随Location 头信息返回。假如需要的资源无法及时建立的话，应当返回 '202 Accepted'。
    public static final int CREATED = 201;

    // 服务器已接受请求，但尚未处理。正如它可能被拒绝一样，最终该请求可能会也可能不会被执行。在异步操作的场合下，没有比发送这个状态码更方便的做法了。
    public static final int ACCEPTED = 202;

    //服务器成功处理了请求，但不需要返回任何实体内容，并且希望返回更新了的元信息
    //Restful NO CONTENT – [DELETE]：用户删除数据成功。
    public static final int NOCONTENT = 204;

    /**
     * 重定向（3字头）
     */

    //如果客户端发送了一个带条件的 GET 请求且该请求已被允许，而文档的内容（自上次访问以来或者根据请求的条件）并没有改变，则服务器应当返回这个状态码
    public static final int NotMODIFIED = 304;

    /**
     * 请求错误（4字头）
     */

    // 1)语义有误，当前请求无法被服务器理解; 2)请求参数有误;
    public static final int BADREQUEST = 400;

    //当前请求需要用户验证。
    public static final int UNAUTHORIZED = 401;

    //该状态码是为了将来可能的需求而预留的。
    public static final int PAYMENTREQUIRED = 402;

    //服务器已经理解请求，但是拒绝执行它。
    public static final int FORBIDDEN = 403;

    //请求失败，请求所希望得到的资源未被在服务器上发现。
    public static final int NOTFOUND = 404;

    //请求行中指定的请求方法不能被用于请求相应的资源。
    public static final int METHODNOTALLOWED = 405;

    //请求的资源的内容特性无法满足请求头中的条件，因而无法生成响应实体。
    public static final int NOTACCEPTABLE = 406;

    //请求超时。客户端没有在服务器预备等待的时间内完成一个请求的发送。
    public static final int REQUESTTIMEOUT = 408;

    //由于和被请求的资源的当前状态之间存在冲突，请求无法完成。
    public static final int CONFLICT = 409;

    //被请求的资源在服务器上已经不再可用，而且没有任何已知的转发地址
    public static final int GONE = 410;


    /**
     * 服务器错误（5字头）
     */

    //服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。一般来说，这个问题都会在服务器端的源代码出现错误时出现。
    public static final int INTERNALSERVERERROR = 500;
    //服务器不支持当前请求所需要的某个功能。当服务器无法识别请求的方法，并且无法支持其对任何资源的请求。
    public static final int NOTIMPLEMENTED = 501;


    /**
     * 业务错误(7字头)
     */
    /**
     * 账号已过期
     */
    public static final int ACCOUNTEXPIRED = 701;
    /**
     * 认证已经过期
     */
    public static final int CREDENTIALSEXPIRED = 702;
    /**
     * 用户已经存在
     */
    public static final int USEREXIST = 703;
    /**
     * 未绑定手机
     */
    public static final int NOBINDPHONE = 704;
    /**
     * 未绑定邮箱
     */
    public static final int NOBINDEMAIL = 705;
    /**
     * 角色不存在
     */
    public static final int ROLENOTEXIST = 706;
    /**
     * 密码与确认密码不一致
     */
    public static final int PWDDIFF = 707;
    /**
     * 重置密码，验证码超时
     */
    public static final int RESETPWDTIMEOUT = 708;
    /**
     * 重置密码，验证码不一致
     */
    public static final int RESETPWDDIFF = 709;
    /**
     * 短信发送验证码失败
     */
    public static final int MESSAGESENDERROR = 710;

    /**
     * 存在关联数据删除失败
     */
    public static final int DELETEFAILURE = 711;

}
