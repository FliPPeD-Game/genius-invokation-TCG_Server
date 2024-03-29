package com.card.game.flyweight;

import com.card.game.model.ConnectionEntity;
import java.util.HashSet;
import java.util.Set;

// 抽象享元角色
public interface IFlyweight {
    Set<ConnectionEntity> room=new HashSet<>();
    /**
     * 加入房间
     *
     * @param connection 链接
     */
    void join(ConnectionEntity connection);

    /**
     * 删除
     *
     * @param connection 链接
     * @return 是否删除成功
     */
    boolean delete(ConnectionEntity connection);

    /**
     * 查询
     *
     * @return 房间人数
     */
    Integer query();

    Set<ConnectionEntity> queryAll();
}
