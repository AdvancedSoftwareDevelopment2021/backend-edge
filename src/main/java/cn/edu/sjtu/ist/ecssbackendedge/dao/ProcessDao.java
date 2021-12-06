package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;

import java.util.List;

public interface ProcessDao {

    Process createProcess(Process process);

    void removeProcess(String id);

    void modifyProcess(Process process);

    Process findProcessById(String id);

    List<Process> findProcessByName(String name);

    List<Process> findAllProcesss();
}
