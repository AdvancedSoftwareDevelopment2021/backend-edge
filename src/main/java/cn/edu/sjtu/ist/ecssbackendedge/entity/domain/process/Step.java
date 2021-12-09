package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process;

/**
 * @brief 流程构建步骤
 * @author rsp
 * @version 0.1
 * @date 2021/12/6
 */
public enum Step {

    /**
     * 第一步，绘制BPMN
     */
    BPMN,
    /**
     * 第二步，绑定设备
     */
    DEVICE,
    /**
     * 第三步，流程定制完成
     */
    FINISHED,
}
