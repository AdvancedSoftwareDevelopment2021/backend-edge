package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public class Visitor {

    private int interval;

    public DriverDao driverDao;

    /**
     * 保存节点id：节点
     */
    private Map<String, AbstractFlowNodeProxy> nodes = new ConcurrentHashMap<>();

    /**
     * 保存节点id：节点剩余物料
     */
    private Map<String, AtomicInteger> numbers = new ConcurrentHashMap<>();


    public Visitor(int interval){
        this.interval = interval;
    }

    public void addNode(AbstractFlowNodeProxy node) {
        node.driverDao = driverDao;
        nodes.putIfAbsent(node.getId(), node);
        log.info(String.format("加入node, id=%s, 当前node总数%d", node.getId(), nodes.size()));
    }

    public void addNumber(String nodeId) {
        if (numbers.containsKey(nodeId)) {
            numbers.get(nodeId).getAndIncrement();
        } else {
            numbers.put(nodeId, new AtomicInteger(1));
        }
        log.info(String.format("加入number, id=%s, 当前number数%d", nodeId, numbers.get(nodeId).get()));

        // 触发下一个节点工作
        AbstractFlowNodeProxy node = nodes.get(nodeId);
        if (!node.running) { return; }
        Thread thread = new MyThread(this, node);
        thread.start();
    }

    public void subNumber(String nodeId) {
        if (numbers.containsKey(nodeId)) {
            numbers.get(nodeId).getAndDecrement();
        } else {
            log.info("减少number, nodeId对应的值不存在，nodeID={}", nodeId);
        }
        log.info(String.format("减少number, id=%s, 当前number数%d", nodeId, numbers.get(nodeId).get()));
    }

    public boolean canWork(String nodeId, int thr) {
        return numbers.get(nodeId).get() >= thr;
    }

    private static class MyThread extends Thread {

        private final Visitor visitor;

        private final AbstractFlowNodeProxy node;

        public MyThread(Visitor visitor, AbstractFlowNodeProxy node) {
            this.visitor = visitor;
            this.node = node;
        }

        @Override
        public void run() {
            node.setRunning(false);
            while (visitor.canWork(node.getId(), 1)) {
                node.startWithKafkaMode(visitor.getInterval());
                visitor.subNumber(node.getId());
                List<AbstractFlowNodeProxy> followingNodes = node.getFollowingFlowNodes();
                for (AbstractFlowNodeProxy n : followingNodes) {
                    visitor.addNumber(n.getId());
                }
            }
            node.setRunning(true);
        }
    }
}
