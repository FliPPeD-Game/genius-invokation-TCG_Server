package com.card.game.cardstun.flyweight;

import com.card.game.cardstun.websocket.Connection;

// 抽象享元角色
public interface IFlyweight {
    /**
     * 创建
     *
     * @paramconnection 链接
     * @return 标识
     */
    void create(Connection connection);

    /**
     * 删除
     *
     * @param connection 链接
     * @return 是否删除成功
     */
    boolean delete(Connection connection);

    /**
     * 查询
     *
     * @return 房间人数
     */
    Integer query();
}
