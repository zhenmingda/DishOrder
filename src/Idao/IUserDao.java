package Idao;

import dto.UserTo;

import java.util.List;

/**
 * Created by dashu on 2016/5/26.
 */
public interface IUserDao {
    int create( UserTo to);
    List<UserTo> read(int tableID);
    void delete( UserTo to);
}
