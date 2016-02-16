package cn.nekocode.baseframework.data.exception

/**
 * Created by fostion on 1/13/16.
 */
class ResponseException(var status: String, var code: Int, var msg: String) : Exception()