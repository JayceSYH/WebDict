package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
/*
 * Return value is the state, state code and user session
 * format:
 * ok;0;1cb3ef78965478c32a456987456d2d
 * err;1
 *
 * Note:state code could be the following num
 * 0:state ok
 * -1:unknown error
 * 1:user not exist
 * 2:password error
 * 3:password format error
 * 4:username format error
 * 5:username already exist
 *
 */
public interface LoginService{
    String login(String username, String password);
    String register(String username, String password);
}
