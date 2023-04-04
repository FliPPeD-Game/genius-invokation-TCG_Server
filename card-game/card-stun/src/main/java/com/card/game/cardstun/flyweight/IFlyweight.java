package com.card.game.cardstun.flyweight;

import com.card.game.cardstun.model.ConnectionEntity;

// 抽象享元角色
public interface IFlyweight {

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
}
