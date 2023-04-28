//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.nacos.naming.controllers;

import com.alibaba.nacos.auth.annotation.Secured;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.alibaba.nacos.core.utils.WebUtils;
import com.alibaba.nacos.naming.cluster.ServerStatusManager;
import com.alibaba.nacos.naming.core.DistroMapper;
import com.alibaba.nacos.naming.core.v2.client.Client;
import com.alibaba.nacos.naming.core.v2.client.manager.ClientManager;
import com.alibaba.nacos.naming.misc.Loggers;
import com.alibaba.nacos.naming.misc.SwitchDomain;
import com.alibaba.nacos.naming.misc.SwitchManager;
import com.alibaba.nacos.naming.monitor.MetricsMonitor;
import com.alibaba.nacos.plugin.auth.constant.ActionTypes;
import com.alibaba.nacos.sys.env.EnvUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/v1/ns/operator", "/v1/ns/ops"})
public class OperatorController {
    private final SwitchManager switchManager;
    private final ServerStatusManager serverStatusManager;
    private final SwitchDomain switchDomain;
    private final DistroMapper distroMapper;
    private final ClientManager clientManager;

    public OperatorController(SwitchManager switchManager, ServerStatusManager serverStatusManager, SwitchDomain switchDomain, DistroMapper distroMapper, ClientManager clientManager) {
        this.switchManager = switchManager;
        this.serverStatusManager = serverStatusManager;
        this.switchDomain = switchDomain;
        this.distroMapper = distroMapper;
        this.clientManager = clientManager;
    }

    @RequestMapping({"/push/state"})
    public ObjectNode pushState(@RequestParam(required = false) boolean detail, @RequestParam(required = false) boolean reset) {
        ObjectNode result = JacksonUtils.createEmptyJsonNode();
        int failedPushCount = MetricsMonitor.getFailedPushMonitor().get();
        int totalPushCount = MetricsMonitor.getTotalPushMonitor().get();
        result.put("succeed", totalPushCount - failedPushCount);
        result.put("total", totalPushCount);
        if (totalPushCount > 0) {
            result.put("ratio", ((float)totalPushCount - (float)failedPushCount) / (float)totalPushCount);
        } else {
            result.put("ratio", 0);
        }

        if (detail) {
            ObjectNode detailNode = JacksonUtils.createEmptyJsonNode();
            detailNode.put("avgPushCost", MetricsMonitor.getAvgPushCostMonitor().get());
            detailNode.put("maxPushCost", MetricsMonitor.getMaxPushCostMonitor().get());
            result.replace("detail", detailNode);
        }

        if (reset) {
            MetricsMonitor.resetPush();
        }

        result.put("reset", reset);
        return result;
    }

    @GetMapping({"/switches"})
    public SwitchDomain switches(HttpServletRequest request) {
        return this.switchDomain;
    }

    @Secured(
            resource = "naming/switches",
            action = ActionTypes.WRITE
    )
    @PutMapping({"/switches"})
    public String updateSwitch(@RequestParam(required = false) boolean debug, @RequestParam String entry, @RequestParam String value) throws Exception {
        this.switchManager.update(entry, value, debug);
        return "ok";
    }

    @GetMapping({"/metrics"})
    public ObjectNode metrics(HttpServletRequest request) {
        boolean onlyStatus = Boolean.parseBoolean(WebUtils.optional(request, "onlyStatus", "true"));
        ObjectNode result = JacksonUtils.createEmptyJsonNode();
        result.put("status", this.serverStatusManager.getServerStatus().name());
        if (onlyStatus) {
            return result;
        } else {
            Collection<String> allClientId = this.clientManager.allClientId();
            int connectionBasedClient = 0;
            int ephemeralIpPortClient = 0;
            int persistentIpPortClient = 0;
            int responsibleClientCount = 0;
            int responsibleIpCount = 0;
            Iterator var10 = allClientId.iterator();

            while(var10.hasNext()) {
                String clientId = (String)var10.next();
                if (clientId.contains("#")) {
                    if (clientId.endsWith("false")) {
                        ++persistentIpPortClient;
                    } else {
                        ++ephemeralIpPortClient;
                    }
                } else {
                    ++connectionBasedClient;
                }

                Client client = this.clientManager.getClient(clientId);
                if (this.clientManager.isResponsibleClient(client)) {
                    ++responsibleClientCount;
                    responsibleIpCount += client.getAllPublishedService().size();
                }
            }

            result.put("serviceCount", MetricsMonitor.getDomCountMonitor().get());
            result.put("instanceCount", MetricsMonitor.getIpCountMonitor().get());
            result.put("subscribeCount", MetricsMonitor.getSubscriberCount().get());
            result.put("responsibleInstanceCount", responsibleIpCount);
            result.put("clientCount", allClientId.size());
            result.put("connectionBasedClientCount", connectionBasedClient);
            result.put("ephemeralIpPortClientCount", ephemeralIpPortClient);
            result.put("persistentIpPortClientCount", persistentIpPortClient);
            result.put("responsibleClientCount", responsibleClientCount);
            result.put("cpu", EnvUtil.getCpu());
            result.put("load", EnvUtil.getLoad());
            result.put("mem", EnvUtil.getMem());
            return result;
        }
    }

    @GetMapping({"/distro/client"})
    public ObjectNode getResponsibleServer4Client(@RequestParam String ip, @RequestParam String port) {
        ObjectNode result = JacksonUtils.createEmptyJsonNode();
        String tag = ip + ":" + port;
        result.put("responsibleServer", this.distroMapper.mapSrv(tag));
        return result;
    }

    @PutMapping({"/log"})
    public String setLogLevel(@RequestParam String logName, @RequestParam String logLevel) {
        Loggers.setLogLevel(logName, logLevel);
        return "ok";
    }
}
