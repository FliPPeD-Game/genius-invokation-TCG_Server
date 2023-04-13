import { NacosNamingClient } from 'nacos'
import express from 'express'

const logger = console
const app = express()

const port = 3000// 服务的端口

const client = new NacosNamingClient({
  logger,
  serverList: 'localhost:8848', // nacos服务端的地址
  namespace: 'dev', // nacos命名空间
})
client.ready().then(() => {
  const serviceName = 'nodejs.test.domain'// 服务名
  // 开始注册
  client.registerInstance(serviceName, {
    ip: 'localhost',
    port,
  })
})
// 测试接口
app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
