package Idao;

import dto.OrderTo;


import java.util.List;

/**
 * Created by dashu on 2016/4/8.
 * version 1.0
 * The interface provides crund methods
 */
public interface IOrderDao {
     void create( OrderTo to);

     List<OrderTo> read(int tableID);

     void update( OrderTo to);

     void delete( OrderTo to);

   

}
