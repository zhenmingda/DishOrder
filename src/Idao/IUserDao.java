package Idao;

import dto.UserTo;

import java.util.List;

/**
 * Created by dashu on 2016/5/26.
 * version 1.0
 * The interface provides a read method fucntion, a create function and a delete function
 */
public interface IUserDao {
    int create( UserTo to);
    List<UserTo> read(int tableID);
    void delete( UserTo to);
}
