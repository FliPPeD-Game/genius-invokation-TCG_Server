import { NacosNamingClient } from 'nacos'
import { PeerServer } from 'peer'

const logger = console

const ip = '127.0.0.1'
const port = 9000

const client = new NacosNamingClient({
  logger,
  serverList: '127.0.0.1:8848',
  namespace: 'd9134ab5-743d-48a3-98f0-0db5ab132f80',
})

await client.ready()

const serviceName = 'peer-service'

client.registerInstance(serviceName, {
  ip,
  port,
})

PeerServer({
  path: '/peerServer',
  port,
})
